package org.example.data;

import org.example.data.entities.Abonent;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class PhoneDB implements AutoCloseable  {
    private SessionFactory sessionFactory = null;
    private Session session = null;
    private List<Abonent> getByHqlParam(String paramName,String param){
        String hql = "FROM Abonent  WHERE LOWER("+paramName+") LIKE :param";
        List<Abonent> abonents = null;
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query<Abonent> query = session.createQuery(hql, Abonent.class);
            query.setParameter("param",  "%" + param.toLowerCase() + "%");
            abonents = query.getResultList();
            transaction.commit();
        }
        catch( HibernateException ex){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Error  !!!\n"+ ex.getMessage());
        }
        return abonents;
    }


    public PhoneDB(){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    public  boolean addAbonent(String name,String email,String phone,String address) throws SQLException {
        Transaction transaction = null;
        Abonent newAbonent = new Abonent();
        newAbonent.setAddress(address);
        newAbonent.setName(name);
        newAbonent.setEmail(email);
        newAbonent.setPhoneNumber(phone);
        try{
             transaction = session.beginTransaction();
             session.save(newAbonent);
             transaction.commit();
        }
        catch( HibernateException ex){
             if(transaction != null)
                 transaction.rollback();
            System.out.println("Error abonent adding !!!\n"+ ex.getMessage());
            return false;
        }
         return true;
    }

    public  boolean deleteAdonent(int id ) throws SQLException {
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Abonent abonent = session.get(Abonent.class, id);
            if (abonent != null) {
                session.delete(abonent);
            }
            transaction.commit();
        }
        catch( HibernateException ex){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Error abonent deleting !!!\n"+ ex.getMessage());
            return false;
        }
        return true;
    }

    public List<Abonent> getAllAbonents( ) throws SQLException{
        String hql = "FROM Abonent";
        List<Abonent> abonents = null ;
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query<Abonent> query = session.createQuery(hql, Abonent.class);
            abonents = query.getResultList();
            transaction.commit();
        }
        catch( HibernateException ex){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Error  !!!\n"+ ex.getMessage());
        }
        return abonents;
    }

    public  List<Abonent> getAbonentsLikeName(String name ) throws SQLException{
        return getByHqlParam("name",name);
    }

    public  List<Abonent> getAbonentByPhone(String phone ) throws SQLException{
         return getByHqlParam("phoneNumber",phone);
    }

    public  Abonent getAbonentById(int id ) throws SQLException{
        String hql = "FROM Abonent WHERE id = :id";
        Abonent abonent = null ;
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query<Abonent> query = session.createQuery(hql, Abonent.class);
            query.setParameter("id",  id);
            abonent = query.getSingleResult();
            transaction.commit();
        }
        catch( HibernateException ex){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Error  !!!\n"+ ex.getMessage());
        }
        return abonent;
    }


    @Override
    public void close() throws Exception {
        if(sessionFactory != null)
            sessionFactory.close();
        if(session != null)
            session.close();
    }
}
