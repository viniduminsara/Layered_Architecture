package dao;

import java.sql.SQLException;
import java.time.LocalDate;

public interface OrderDAO {
    String getNewId() throws SQLException, ClassNotFoundException;

    boolean saveOrder(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException;

    boolean existsOrder(String orderId) throws SQLException, ClassNotFoundException;
}
