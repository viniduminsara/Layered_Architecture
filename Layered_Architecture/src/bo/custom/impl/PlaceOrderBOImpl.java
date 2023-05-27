package bo.custom.impl;

import bo.custom.*;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();

            /*if order id already exist*/
            if (orderDAO.exist(orderId)) {
                return false;
            }

            connection.setAutoCommit(false);
            boolean isSaved = orderDAO.save(new OrderDTO(orderId,orderDate,customerId));

            if (!isSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : orderDetails) {

                boolean isDetailsSaved = orderDetailsDAO.save(new OrderDetailDTO(orderId,detail.getItemCode(),detail.getQty(),detail.getUnitPrice()));
                if (!isDetailsSaved) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                ItemDTO item = itemDAO.get(detail.getItemCode());
                if (item != null) {
                    item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
                    boolean isUpdated = itemDAO.update(item);

                    if (!isUpdated) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public CustomerDTO getCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.get(id);
    }

    @Override
    public boolean existsItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public ItemDTO getItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.get(id);
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getLastId();
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getIds();
    }

    @Override
    public ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getIds();
    }
}
