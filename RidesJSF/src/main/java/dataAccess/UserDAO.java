package dataAccess;

import domain.User;
import domain.Driver;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public User getUserByEmailAndPassword(String email, String pasahitza) {
        Query query = session.createQuery("FROM User WHERE email = :email AND pasahitza = :pasahitza");
        query.setParameter("email", email);
        query.setParameter("pasahitza", pasahitza);
        return (User) query.uniqueResult();
    }

    public User getUserByEmail(String email) {
        Query query = session.createQuery("FROM User WHERE email = :email");
        query.setParameter("email", email);
        return (User) query.uniqueResult();
    }

    public void save(User user) {
        session.saveOrUpdate(user);
    }

    // Método para verificar si el email ya existe y registrar al usuario
    public boolean registerUser(User user) {
        // Primero verificamos si el usuario ya existe
        if (getUserByEmail(user.getEmail()) != null) {
            return false;  // Ya existe el usuario
        }

        // Si no existe, se crea el usuario
        session.saveOrUpdate(user);

        // Creamos el Driver asociado a este User
        Driver driver = new Driver(user.getEmail(), user.getIzena(), user);
        session.saveOrUpdate(driver);

        // Si tienes una clase Traveler, aquí también puedes crear un Traveler (si es necesario)

        return true;  // Usuario creado exitosamente
    }
}
