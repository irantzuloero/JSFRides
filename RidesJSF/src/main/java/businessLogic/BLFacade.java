package businessLogic;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;
import exceptions.RideAlreadyExistException;

 
/**
 * Interface that specifies the business logic.
 */

public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	 public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	 public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	 public List<Ride> getRides(String from, String to, Date date);

	 public void registerUser(String name, String email, String password) throws UserAlreadyExistsException;
	 public boolean isValidUser(String email, String pasahitza);
}
