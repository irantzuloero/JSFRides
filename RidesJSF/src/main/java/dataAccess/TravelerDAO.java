package dataAccess;

import domain.Traveler;
import domain.User;
import eredua.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class TravelerDAO {

    private Session session;

    public TravelerDAO(Session session) {
        this.session = session;
    }

    public void save(Traveler traveler) {
        session.save(traveler);
    }

    public Traveler getTravelerByEmail(String email) {
        return (Traveler) session.get(Traveler.class, email);
    }

    public Traveler getTravelerByUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Consulta HQL para encontrar el Traveler que corresponde al email del User
            String hql = "FROM Traveler t WHERE t.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", user.getEmail());
            return (Traveler) query.uniqueResult();  // Retorna el Traveler correspondiente al User
        } catch (Exception e) {
            e.printStackTrace();
            return null;
}
    }
}
