package dao;

import model.CustomerDTO;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO {
     ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

     boolean saveCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException;

     boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

     boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

     boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

     String getLastId() throws SQLException, ClassNotFoundException;

     CustomerDTO getCustomer(String id) throws SQLException, ClassNotFoundException;

     ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException;
}
