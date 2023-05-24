package dao;

import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean existItem(String code) throws SQLException, ClassNotFoundException;

    String getNewId() throws SQLException, ClassNotFoundException;

    ItemDTO getItem(String newItemCode) throws SQLException, ClassNotFoundException;

    ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException;
}
