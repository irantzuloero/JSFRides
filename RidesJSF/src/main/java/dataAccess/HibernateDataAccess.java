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
            // Crear dos usuarios
            User user1 = new User("Aitor Fernandez", "aitor@gmail.com", "123");
            User user2 = new User("Ane Gaztañaga", "ane@gmail.com", "456");

            // Guardar los usuarios en la base de datos
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
            throw new RideMustBeLaterThanTodayException("The ride date must be later than today.");
        }

        Driver driver = driverDAO.getDriver(driverEmail);
        if (driver == null) {
            throw new RuntimeException("Driver not found.");
        }

        if (rideDAO.rideExists(driver, from, to, date)) {
            throw new RideAlreadyExistException("The ride already exists.");
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
        // Crear el objeto User
        User user = new User(name, email, password);  

        // Crear el objeto Driver y asociarlo al User
        Driver driver = new Driver(email, name, user);
        driver.setUser(user);

        // Crear el objeto Traveler y asociarlo al User
        Traveler traveler = new Traveler(user); 

        // Comenzar la transacción
        Transaction transaction = session.beginTransaction();
        try {
            // Guardar el User, Driver y Traveler en la base de datos
            userDAO.save(user);
            driverDAO.save(driver);
            travelerDAO.save(traveler);

            // Confirmar la transacción
            transaction.commit();
            System.out.println("User, Driver y Traveler creados con éxito.");
        } catch (Exception e) {
            // Si algo sale mal, revertir la transacción
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException("Error al crear el usuario y asociar Driver y Traveler.", e);
        }
    }


    public void saveUser(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            userDAO.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error al guardar el usuario", e);
        }
    }

    public boolean isValidUser(String email, String pasahitza) {
        return userDAO.getUserByEmailAndPassword(email, pasahitza) != null;
    }
}
