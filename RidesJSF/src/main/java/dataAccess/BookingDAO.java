package dataAccess;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Booking;
import domain.Traveler;
import domain.User;
import eredua.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BookingDAO {

    private Session session;

    public BookingDAO(Session session) {
        this.session = session;
    }

    public Traveler findTravelerByUser(User user) {
        String email = user.getEmail();
        Traveler traveler = (Traveler) session.get(Traveler.class, email);
        if (traveler == null) {
            throw new IllegalStateException("No Traveler found for the given User.");
        }
        return traveler;
    }

    public void saveBooking(Booking booking) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Booking> getBookingsByUser(Traveler traveler) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Booking b WHERE b.traveler = :traveler";
            Query query = session.createQuery(hql);
            query.setParameter("traveler", traveler);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
