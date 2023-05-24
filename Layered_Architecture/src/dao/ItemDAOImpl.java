package dao;

import dao.util.SQLUtil;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements CrudDAO<ItemDTO,String>{
    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item";
        ResultSet rst = SQLUtil.execute(sql);
        ArrayList<ItemDTO> items = new ArrayList<>();
        while (rst.next()){
            items.add(new ItemDTO(rst.getString(1),rst.getString(2),rst.getBigDecimal(4),rst.getInt(3)));
        }
        return items;
    }

    @Override
    public boolean save(ItemDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)";
        return SQLUtil.execute(sql,dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        return SQLUtil.execute(sql,dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getCode());
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM Item WHERE code=?";
        ResultSet resultSet =  SQLUtil.execute(sql,s);
        return resultSet.next();
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Item WHERE code=?";
        return SQLUtil.execute(sql,s);
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM Item ORDER BY code DESC LIMIT 1;";
        ResultSet rst = SQLUtil.execute(sql);
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public ItemDTO get(String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE code=?";
        ResultSet rst = SQLUtil.execute(sql,s);
        if(rst.next()) {
            return new ItemDTO(s + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        }
        return null;
    }

    @Override
    public ArrayList<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item";
        ResultSet rst = SQLUtil.execute(sql);
        ArrayList<String> items = new ArrayList<>();

        while (rst.next()){
            items.add(rst.getString("code"));
        }
        return items;
    }
}
