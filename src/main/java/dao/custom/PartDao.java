package dao.custom;

import dao.CrudDao;
import entity.Item;
import entity.Part;

import java.sql.SQLException;

public interface PartDao extends CrudDao<Part> {
    Part getPart(String id) throws SQLException, ClassNotFoundException;

}
