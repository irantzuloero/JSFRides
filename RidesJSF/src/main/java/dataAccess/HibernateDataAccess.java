package dataAccess;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import eredua.HibernateUtil;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.util.Date;

public class HibernateDataAccess {
    private Session session;
    private DriverDAO driverDAO;
    private RideDAO rideDAO;
    private UserDAO userDAO;
    private TravelerDAO travelerDAO;
    private  BookingDAO bookingDAO;

    public HibernateDataAccess() {
        openSession();
        driverDAO = new DriverDAO(session);
        rideDAO = new RideDAO(session);
        userDAO = new UserDAO(session);
        travelerDAO = new TravelerDAO(session);
        bookingDAO = new BookingDAO(session);
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
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public void updateEserlekuKop(Ride ride, int seats) {
        if (session == null || !session.isOpen()) {
            openSession();  // Asegúrate de que la sesión está abierta
        }

        Transaction transaction = session.beginTransaction();  // Iniciar una transacción

        try { 
            // Actualizar el número de asientos
            int remainingSeats = ride.getnPlaces();
            ride.setnPlaces(remainingSeats - seats);
            
            rideDAO.save(ride);  // Guardar el cambio en los asientos
            transaction.commit();  // Confirmar la transacción
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Hacer rollback en caso de error
            }
            throw new RuntimeException("Error al actualizar los asientos", e);
        }
    }

	
    public void createBooking(User user, Ride ride, int seats, float price, Date date) {
 
    	Traveler traveler = bookingDAO.findTravelerByUser(user);
        Booking booking = new Booking(traveler, ride, seats, price, date);
        traveler.addBooking(booking);
        
        int remainingSeats = ride.getnPlaces();
        ride.setnPlaces(remainingSeats - seats);
        
        Transaction transaction = session.beginTransaction();  // Abre la transacción
        try {
            bookingDAO.saveBooking(booking);  // Guardar la reserva
            
            rideDAO.save(ride); 
            transaction.commit(); 
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); 
            }
            throw new RuntimeException("Error al procesar la reserva y actualizar los asientos", e);
        }
    }
    
    public List<Booking> getBookingsByUser(User user) {
        Traveler traveler = travelerDAO.getTravelerByUser(user);
        if (traveler != null) {
            return bookingDAO.getBookingsByUser(traveler);
        }
        return null;
    }

    public List<Ride> getRidesByUser(User user) {
    
        Driver driver = driverDAO.getDriverByUser(user);
        if (driver != null) {
            return rideDAO.getRidesByUser(driver);
        }
        return null;
    }

    public List<Ride> getAllRides(Date date) {
        return rideDAO.getAllRides(date);
    }

}
