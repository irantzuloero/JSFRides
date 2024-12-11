package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.persistence.*;

@Entity
public class Driver {

    @Id
    private String email; 
    private String name;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.PERSIST)
    private List<Ride> rides = new Vector<>();

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;

    public Driver() {}

    public Driver(String email, String name, User user) {
        this.email = email;
        this.name = name;
        this.user = user;  
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
