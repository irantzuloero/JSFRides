package dataAccess;

import domain.Traveler;
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

}

