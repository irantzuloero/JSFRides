package businessLogic;

import domain.Ride;
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
            throw new UserAlreadyExistsException("El usuario con este correo electrónico ya existe.");
        }
        dataAccess.createUser(name, email, password);  // Solo creamos el User
    }
    public boolean isValidUser(String email, String pasahitza) {
        return dataAccess.isValidUser(email, pasahitza);
    }

}
