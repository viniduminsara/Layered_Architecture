package dao;

import dao.util.SQLUtil;
import model.OrderDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class OrderDAOImpl implements CrudDAO<OrderDTO,String> {
    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)";
        return SQLUtil.execute(sql,dto.getOrderId(),Date.valueOf(dto.getOrderDate()),dto.getCustomerId());
    }

    @Override
    public boolean update(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT oid FROM `Orders` WHERE oid=?";
        ResultSet resultSet = SQLUtil.execute(sql,s);
        return resultSet.next();
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;";
        ResultSet rst = SQLUtil.execute(sql);

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }

    @Override
    public OrderDTO get(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
