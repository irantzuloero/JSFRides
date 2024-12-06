package dataAccess;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Driver;
import domain.Ride;
import eredua.HibernateUtil;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import java.util.List;
import java.util.Date;

public class HibernateDataAccess {
    private Session session;
    private DriverDAO driverDAO;
    private RideDAO rideDAO;

    public HibernateDataAccess() {
        openSession();
        driverDAO = new DriverDAO(session);
        rideDAO = new RideDAO(session);
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

            Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez");
            Driver driver2 = new Driver("driver2@gmail.com", "Ane Gaztañaga");
            Driver driver3 = new Driver("driver3@gmail.com", "Test driver");

            driver1.addRide("Donostia", "Bilbo", new Date(), 4, 7);
            driver2.addRide("Donostia", "Bilbo", new Date(), 3, 3);

            driverDAO.save(driver1);
            driverDAO.save(driver2);
            driverDAO.save(driver3);

            transaction.commit();
            System.out.println("DB initialized with Hibernate");
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

    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
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
     //   driver.addRide(from, to, date, nPlaces, price);

        Transaction transaction = session.beginTransaction();
        rideDAO.save(ride);
        transaction.commit();

        return ride;
    }

    public List<Ride> getRides(String from, String to, Date date) {
        return rideDAO.getRides(from, to, date);
    }
}
