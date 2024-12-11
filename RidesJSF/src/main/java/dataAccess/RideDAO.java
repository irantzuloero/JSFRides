package dataAccess;

import org.hibernate.Session;
import org.hibernate.Query;
import domain.Ride;
import domain.User;
import eredua.HibernateUtil;
import domain.Driver;

import java.util.List;
import java.util.Date;

public class RideDAO {

    private Session session;

    public RideDAO(Session session) {
        this.session = session;
    }

    public void save(Ride ride) {
        session.saveOrUpdate(ride);
    }

    public List<String> getDepartCities() {
        
        Query query = session.createQuery("SELECT DISTINCT r.fromLocation FROM Ride r ORDER BY r.fromLocation");
        return query.list();  
    }

    public List<String> getArrivalCities(String from) {
        
        Query query = session.createQuery("SELECT DISTINCT r.toLocation FROM Ride r WHERE r.fromLocation = :fromLocation ORDER BY r.toLocation");
        query.setParameter("fromLocation", from);  
        return query.list(); 
    }

    public boolean rideExists(Driver driver, String from, String to, Date date) {
        // Usamos 'fromLocation' y 'toLocation' como nombres de las propiedades en la clase Ride
        Query query = session.createQuery("FROM Ride r WHERE r.driver = :driver AND r.fromLocation = :fromLocation AND r.toLocation = :toLocation AND r.date = :date");
        query.setParameter("driver", driver);
        query.setParameter("fromLocation", from); 
        query.setParameter("toLocation", to);    
        query.setParameter("date", date);
        return !query.list().isEmpty(); 
    }

    public List<Ride> getRides(String from, String to, Date date) {
        
        Query query = session.createQuery("FROM Ride r WHERE r.fromLocation = :fromLocation AND r.toLocation = :toLocation AND r.date = :date");
        query.setParameter("fromLocation", from); 
        query.setParameter("toLocation", to);      
        query.setParameter("date", date);
        return query.list();  
    }

    public List<Ride> getRidesByUser(Driver driver) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Ride r WHERE r.driver = :driver";
            Query query = session.createQuery(hql);
            query.setParameter("driver", driver); 
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

}
