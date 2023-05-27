package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO{
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean save(T dto) throws SQLException, ClassNotFoundException;

    boolean update(T dto) throws SQLException, ClassNotFoundException;

    boolean exist(ID id) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    String getLastId() throws SQLException, ClassNotFoundException;

    T get(ID id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getIds() throws SQLException, ClassNotFoundException;
}
