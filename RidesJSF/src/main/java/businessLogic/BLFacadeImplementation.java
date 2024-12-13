package businessLogic;

import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Booking;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;
import exceptions.RideAlreadyExistException;

import java.util.Date;
import java.util.List;
import dataAccess.HibernateDataAccess;

public class BLFacadeImplementation implements BLFacade {
    private HibernateDataAccess dataAccess;

    public BLFacadeImplementation(HibernateDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public List<String> getDepartCities() {
        return dataAccess.getDepartCities();
    }

    @Override
    public List<String> getDestinationCities(String from) {
        return dataAccess.getArrivalCities(from);
    }

    @Override
    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
        return dataAccess.createRide(from, to, date, nPlaces, price, driverEmail);
        
    }

    @Override
    public List<Ride> getRides(String from, String to, Date date) {
        return dataAccess.getRides(from, to, date);
    }

    public void registerUser(String name, String email, String password) throws UserAlreadyExistsException {
        if (dataAccess.userExists(email)) {
            throw new UserAlreadyExistsException("Email honekin sortutako kontua existitzen da.");
        }
        dataAccess.createUser(name, email, password); 
    }
    public boolean isValidUser(String email, String pasahitza) {
        return dataAccess.isValidUser(email, pasahitza);
    }
    public User getUserByEmail(String email) {
        return dataAccess.getUserByEmail(email);
    }
    
    public void updateEserlekuKop(Ride ride, int eserlekuak) {
    	 dataAccess.updateEserlekuKop(ride, eserlekuak);
    }
    
    public void createBooking(User user, Ride ride, int seats, float price, Date date) {
    	dataAccess.createBooking(user, ride, seats, price, date);
    }
    
    public List<Booking> getBookingsByUser(User user) {
        return dataAccess.getBookingsByUser(user);  // Método para obtener las reservas del usuario
    }

    public List<Ride> getRidesByUser(User user) {
        return dataAccess.getRidesByUser(user);  // Método para obtener los viajes creados por el usuario
    }
    
    public List<Ride> getAllRides(Date data){
    	return dataAccess.getAllRides(data);
    }

}
