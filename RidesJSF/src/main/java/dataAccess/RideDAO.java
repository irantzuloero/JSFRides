package dataAccess;

import org.hibernate.Session;
import org.hibernate.Query;
import domain.Ride;
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
        // Usamos 'fromLocation' como nombre de la propiedad en la clase Ride
        Query query = session.createQuery("SELECT DISTINCT r.fromLocation FROM Ride r ORDER BY r.fromLocation");
        return query.list();  
    }

    public List<String> getArrivalCities(String from) {
        // Usamos 'fromLocation' y 'toLocation' como nombres de las propiedades en la clase Ride
        Query query = session.createQuery("SELECT DISTINCT r.toLocation FROM Ride r WHERE r.fromLocation = :fromLocation ORDER BY r.toLocation");
        query.setParameter("fromLocation", from);  // Usamos 'fromLocation' en lugar de 'from'
        return query.list(); 
    }

    public boolean rideExists(Driver driver, String from, String to, Date date) {
        // Usamos 'fromLocation' y 'toLocation' como nombres de las propiedades en la clase Ride
        Query query = session.createQuery("FROM Ride r WHERE r.driver = :driver AND r.fromLocation = :fromLocation AND r.toLocation = :toLocation AND r.date = :date");
        query.setParameter("driver", driver);
        query.setParameter("fromLocation", from);  // Usamos 'fromLocation' en lugar de 'from_location'
        query.setParameter("toLocation", to);     // Usamos 'toLocation' en lugar de 'to_location'
        query.setParameter("date", date);
        return !query.list().isEmpty(); 
    }

    public List<Ride> getRides(String from, String to, Date date) {
        // Usamos 'fromLocation' y 'toLocation' como nombres de las propiedades en la clase Ride
        Query query = session.createQuery("FROM Ride r WHERE r.fromLocation = :fromLocation AND r.toLocation = :toLocation AND r.date = :date");
        query.setParameter("fromLocation", from);  // Usamos 'fromLocation' en lugar de 'from'
        query.setParameter("toLocation", to);      // Usamos 'toLocation' en lugar de 'to'
        query.setParameter("date", date);
        return query.list();  
    }

}
