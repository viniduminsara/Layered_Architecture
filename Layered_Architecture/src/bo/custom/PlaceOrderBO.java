package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {

    boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails);

    boolean existsCustomer(String id) throws SQLException, ClassNotFoundException;

    CustomerDTO getCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean existsItem(String id) throws SQLException, ClassNotFoundException;

    ItemDTO getItem(String id) throws SQLException, ClassNotFoundException;

    String generateOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException;
}
