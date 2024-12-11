package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Traveler {

    @Id
    private String email;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "traveler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
    
    public Traveler() {}

    public Traveler(User user) {
        this.user = user;
        this.email = user.getEmail(); 
        this.bookings = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
    	this.bookings=bookings;
    }
    
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}
