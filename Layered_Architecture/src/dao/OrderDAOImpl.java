package dao;

import db.DBConnection;
import model.ItemDTO;
import model.OrderDetailDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl {

    public static String getNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }

    public static boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();

            /*if order id already exist*/
            if (existsOrder(connection,orderId)) {
                return false;
            }

            connection.setAutoCommit(false);
            boolean isSaved = saveOrder(connection,orderId,orderDate,customerId);

            if (!isSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : orderDetails) {

                boolean isDetailsSaved = saveOrderDetails(connection,orderId,detail.getItemCode(),detail.getUnitPrice(),detail.getQty());
                if (!isDetailsSaved) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
//                //Search & Update Item
                ItemDTO item = ItemDAOImpl.getItem(detail.getItemCode());
                if (item != null) {
                    item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
                    boolean isUpdated = ItemDAOImpl.updateItemQty(connection,item);

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

    private static boolean saveOrderDetails(Connection connection, String orderId, String itemCode, BigDecimal unitPrice, int qty) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");
        stm.setString(1, orderId);
        stm.setString(2, itemCode);
        stm.setBigDecimal(3, unitPrice);
        stm.setInt(4, qty);
        return stm.executeUpdate() > 0;
    }

    private static boolean saveOrder(Connection connection, String orderId, LocalDate orderDate, String customerId) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
        stm.setString(1, orderId);
        stm.setDate(2, Date.valueOf(orderDate));
        stm.setString(3, customerId);
        return stm.executeUpdate() > 0;
    }

    private static boolean existsOrder(Connection connection, String orderId) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
        stm.setString(1, orderId);
        return stm.executeQuery().next();
    }
}
