package bo.custom;

import bo.SuperBo;
import dto.PlaceOrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
    boolean saveOrder(PlaceOrderDto dto) throws SQLException, ClassNotFoundException;

    String generateId() throws SQLException, ClassNotFoundException;

    List<PlaceOrderDto> allOrders() throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException;
}