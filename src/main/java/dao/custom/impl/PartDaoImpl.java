package dao.custom.impl;

import dao.custom.ItemDao;
import dao.custom.PartDao;
import dao.util.HibernateUtil;
import db.DBConnection;
import entity.Item;
import entity.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PartDaoImpl implements PartDao {
    @Override
    public Part getPart(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Part WHERE partId=?";
        PreparedStatement pstm = DBConnection.getInstanceOf().getConnection().prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return new Part(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;
    }

    @Override
    public boolean save(Part entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(Part entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Part part = session.find(Part.class, entity.getPartId());
        part.setPartName(entity.getPartName());
        part.setUnitPrice(entity.getUnitPrice());
        session.save(part);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Part.class,value));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Part> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Part");
        List<Part> list = query.list();
        session.close();
        return list;
    }
}
