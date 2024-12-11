package dataAccess;

import org.hibernate.Query;
import org.hibernate.Session;
import domain.Driver;
import domain.User;
import eredua.HibernateUtil;

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
    public Driver getDriverByUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Consulta HQL para encontrar el Driver que corresponde al email del User
            String hql = "FROM Driver d WHERE d.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", user.getEmail());
            return (Driver) query.uniqueResult();  // Retorna el Driver correspondiente al User
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
}
