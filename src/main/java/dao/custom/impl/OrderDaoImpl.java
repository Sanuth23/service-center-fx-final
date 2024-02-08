package dao.custom.impl;

import dao.custom.OrderDao;
import dao.util.HibernateUtil;
import dto.OrderDetailDto;
import dto.PlaceOrderDto;
import entity.Customer;
import entity.Item;
import entity.PlaceOrder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

//    OrderDetailDao orderDetailDao = new OrderDetailDaoImpl();
    @Override
    public boolean save(PlaceOrderDto dto) throws SQLException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        PlaceOrder order = new PlaceOrder(
                dto.getOrderId(),
                Date.valueOf(dto.getDate()),
                dto.getIssue(),
                dto.getTotal(),
                dto.getStatus()
        );
        order.setCustomer(session.find(Customer.class,dto.getCustId()));
        order.setItem(session.find(Item.class,dto.getItemCode()));
        session.save(order);

        List<OrderDetailDto> list = dto.getList(); //dto type

//        for (OrderDetailDto detailDto:list) {
//            OrderDetail orderDetail = new OrderDetail(
//                    new OrderDetailsKey(detailDto.getOrderId(), detailDto.getItemCode()),
//                    order,
//                    session.find(Item.class, detailDto.getItemCode()),
//                    detailDto.getQty(),
//                    detailDto.getUnitPrice()
//            );
//            session.save(orderDetail);
//        }

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(PlaceOrderDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(PlaceOrder.class,id));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<PlaceOrderDto> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM PlaceOrder ");
        List<PlaceOrder> orderList = query.list();
        List<PlaceOrderDto> list = new ArrayList<>();
        for (PlaceOrder orders:orderList) {
            list.add(new PlaceOrderDto(
                    orders.getOrderId(),
                    orders.getCustomer().getId(),
                    orders.getItem().getCode(),
                    orders.getDate().toString(),
                    orders.getIssue(),
                    orders.getTotal(),
                    orders.getStatus(),
                    null
            ));
        }
        session.close();
        return list;

    }

    @Override
    public PlaceOrderDto lastOrder() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM PlaceOrder ORDER BY id DESC");
        List<PlaceOrder> list = query.list();

        if (!list.isEmpty()){
            return new PlaceOrderDto(
                    list.get(0).getOrderId(),
                    list.get(0).getCustomer().getId(),
                    list.get(0).getItem().getCode(),
                    list.get(0).getDate().toString(),
                    list.get(0).getIssue(),
                    list.get(0).getTotal(),
                    list.get(0).getStatus(),
                    null
            );
        }

        return null;
    }
}
