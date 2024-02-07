package dao.custom;

import dao.CrudDao;
import entity.Item;

import java.sql.SQLException;

public interface ItemDao extends CrudDao<Item> {
    Item getItem(String code) throws SQLException, ClassNotFoundException;

}
