package dao.custom;

import dao.CrudDao;
import entity.Customer;

import java.sql.SQLException;

public interface CustomerDao extends CrudDao<Customer> {
    Customer getCustomer(String id) throws SQLException, ClassNotFoundException;

}
