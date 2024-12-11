package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @ManyToOne
    @JoinColumn(name = "traveler_email") 
    private Traveler traveler;

    @ManyToOne
    @JoinColumn(name = "ride_id") 
    private Ride ride;
    
    private int seats; 
    private float totalPrice; 
    private Date bookingDate; 
    public Booking() {}
    public Booking( Traveler traveler, Ride ride, int seats, float totalPrice, Date bookingDate) {

        this.traveler = traveler;
        this.ride = ride;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
    }


    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}
