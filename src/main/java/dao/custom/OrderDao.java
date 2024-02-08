package dao.custom;

import dao.CrudDao;
import dto.PlaceOrderDto;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<PlaceOrderDto> {
    PlaceOrderDto lastOrder() throws SQLException, ClassNotFoundException;
}
