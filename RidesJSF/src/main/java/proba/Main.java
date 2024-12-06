package proba;

import domain.Ride;
import dataAccess.HibernateDataAccess;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import java.util.Date;

import configuration.UtilDate;

public class Main {
    public static void main(String[] args) {
        HibernateDataAccess dataAccess = new HibernateDataAccess();
        
        try {
            String driverEmail = "driver2@gmail.com";

            Ride ride = dataAccess.createRide(
                "Gasteiz",         
                "Donostia",           
                UtilDate.newDate(2024, 12, 30), 
                4,                  
                5,              
                driverEmail         
            );
            
            System.out.println("Viaje creado con éxito: " + ride);
            
        } catch (RideMustBeLaterThanTodayException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RideAlreadyExistException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
