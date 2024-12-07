package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String izena;
    private String email;
    private String pasahitza;

    public User() { }

    public User(String izena, String email, String pasahitza) {
        this.izena = izena;
        this.email = email;
        this.pasahitza = pasahitza;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return pasahitza;
    }

    public void setContraseña(String pasahitza) {
        this.pasahitza = pasahitza;
    }
}
