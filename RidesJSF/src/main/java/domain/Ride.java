package domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ride {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "from_location") // Cambié el nombre de la columna a 'from_location'
    private String fromLocation;
    
    @Column(name = "to_location") // Cambié el nombre de la columna a 'to_location'
    private String toLocation;
    
    private Date date;
    private int nPlaces;
    private float price;
    
    @ManyToOne(cascade = CascadeType.ALL) // Relación con Driver
    @JoinColumn(name = "driver_email", referencedColumnName = "email")
    private Driver driver;

    public Ride() {
    }

    public Ride(String fromLocation, String toLocation, Date date, int nPlaces, float price, Driver driver) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.date = date;
        this.nPlaces = nPlaces;
        this.price = price;
        this.driver = driver;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getnPlaces() {
        return nPlaces;
    }

    public void setnPlaces(int nPlaces) {
        this.nPlaces = nPlaces;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
