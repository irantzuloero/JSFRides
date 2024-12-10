package dataAccess;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import eredua.HibernateUtil;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;

import java.util.List;
import java.util.Date;

public class HibernateDataAccess {
    private Session session;
    private DriverDAO driverDAO;
    private RideDAO rideDAO;
    private UserDAO userDAO;
    private TravelerDAO travelerDAO;

    public HibernateDataAccess() {
        openSession();
        driverDAO = new DriverDAO(session);
        rideDAO = new RideDAO(session);
        userDAO = new UserDAO(session);
        travelerDAO = new TravelerDAO(session);
    }

    private void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void closeSession() {
        session.close();
    }

    public void initializeDB() {
    	Transaction transaction = session.beginTransaction();
        try {

            User user1 = new User("Aitor Fernandez", "aitor@gmail.com", "123");
            User user2 = new User("Ane Gaztañaga", "ane@gmail.com", "456");

            userDAO.save(user1);
            userDAO.save(user2);

            transaction.commit();
            System.out.println("DB initialized with two users.");
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public List<String> getDepartCities() {
        return rideDAO.getDepartCities();
    }

    public List<String> getArrivalCities(String from) {
        return rideDAO.getArrivalCities(from);
    }

    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) 
            throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
        if (new Date().compareTo(date) > 0) {
            throw new RideMustBeLaterThanTodayException("Data ezin da gaurko data baino zaharragoa izan.");
        }

        Driver driver = driverDAO.getDriver(driverEmail);
        if (driver == null) {
        	System.out.println("sartu da");
            throw new RuntimeException("Gidaria ez da aurkitu.");
        }

        if (rideDAO.rideExists(driver, from, to, date)) {
            throw new RideAlreadyExistException("Bidai hori sortuta dago.");
        }

        Ride ride = new Ride(from, to, date, nPlaces, price, driver);

        Transaction transaction = session.beginTransaction();
        rideDAO.save(ride);
        transaction.commit();

        return ride;
    }

    public List<Ride> getRides(String from, String to, Date date) {
        return rideDAO.getRides(from, to, date);
    }

    public boolean userExists(String email) {
        return userDAO.getUserByEmail(email) != null;
    }
    public void createUser(String name, String email, String password) {
  
        User user = new User(name, email, password);  

        Driver driver = new Driver(email, name, user);
        driver.setUser(user);

        Traveler traveler = new Traveler(user); 

        Transaction transaction = session.beginTransaction();
        try {
            userDAO.save(user);
            driverDAO.save(driver);
            travelerDAO.save(traveler);

            transaction.commit();
            System.out.println("Erabiltzailea sortu da.");
        } catch (Exception e) {
            
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException("Erabiltzailea sortzerakoan errorea egon da", e);
        }
    }


    public void saveUser(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            userDAO.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Errorea erabiltzailea gordetzerako orduan", e);
        }
    }

    public boolean isValidUser(String email, String pasahitza) {
        return userDAO.getUserByEmailAndPassword(email, pasahitza) != null;
    }
}
