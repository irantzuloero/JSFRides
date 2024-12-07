package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Traveler {

    @Id
    private String email;

    @OneToOne
    private User user;

    // Constructor vacío
    public Traveler() {}

    // Constructor con parámetros
    public Traveler(User user) {
        this.user = user;
        this.email = user.getEmail(); // O el email que quieras asignar
    }

    // Getters y setters
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
}
