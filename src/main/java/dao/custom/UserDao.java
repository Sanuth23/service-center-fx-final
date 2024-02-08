package dao.custom;

import dao.CrudDao;
import entity.Customer;
import entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User> {
    User getUser(String username) throws SQLException, ClassNotFoundException;

}
