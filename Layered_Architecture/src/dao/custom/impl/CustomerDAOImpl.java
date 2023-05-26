package dao.custom.impl;

import dao.custom.CustomerDAO;
import dao.util.SQLUtil;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer";
        ResultSet rst = SQLUtil.execute(sql);
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        while (rst.next()){
            customers.add(new CustomerDTO(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return customers;
    }

    @Override
    public boolean save(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer (id,name, address) VALUES (?,?,?)";
        return SQLUtil.execute(sql,customer.getId(),customer.getName(),customer.getAddress());
    }

    @Override
    public boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET name=?, address=? WHERE id=?";
        return SQLUtil.execute(sql,customerDTO.getName(),customerDTO.getAddress(),customerDTO.getId());
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM Customer WHERE id=?";
        ResultSet resultSet = SQLUtil.execute(sql,s);
        return resultSet.next();
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE id=?";
        return SQLUtil.execute(sql,s);
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM Customer ORDER BY id DESC LIMIT 1;";
        ResultSet rst = SQLUtil.execute(sql);
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public CustomerDTO get(String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE id=?";
        ResultSet rst = SQLUtil.execute(sql,s);
        if(rst.next()) {
            return new CustomerDTO(s + "", rst.getString("name"), rst.getString("address"));
        }
        return null;
    }

    @Override
    public ArrayList<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer";
        ResultSet rst = SQLUtil.execute(sql);
        ArrayList<String> customers = new ArrayList<>();

        while (rst.next()){
            customers.add(rst.getString("id"));
        }
        return customers;
    }
}
