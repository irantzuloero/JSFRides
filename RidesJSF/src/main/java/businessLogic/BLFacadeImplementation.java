package businessLogic;

import domain.Ride;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import java.util.Date;
import java.util.List;
import dataAccess.HibernateDataAccess;

public class BLFacadeImplementation implements BLFacade {
    private HibernateDataAccess dataAccess;

    // Constructor now accepts HibernateDataAccess as a parameter
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
}
