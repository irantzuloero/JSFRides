package dataAccess;

import org.hibernate.Session;

import domain.Driver;

public class DriverDAO {

    private Session session;

    public DriverDAO(Session session) {
        this.session = session;
    }

    public void save(Driver driver) {
        session.saveOrUpdate(driver);
    }

    public Driver getDriver(String email) {
        return (Driver) session.get(Driver.class, email);
    }
}
