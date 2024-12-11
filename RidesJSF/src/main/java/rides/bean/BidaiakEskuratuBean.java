package rides.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import businessLogic.BLFacade;
import domain.Booking;
import domain.Ride;
import domain.User;

@ManagedBean(name = "BidaiakEskuratuBean")
@SessionScoped
public class BidaiakEskuratuBean {

    private List<Booking> bookedRides;
    private List<Ride> createdRides;
    private BLFacade businessLogic;

    public BidaiakEskuratuBean() {
        businessLogic = FacadeBean.getBusinessLogic();
    }

    @PostConstruct
    public void init() {
        loadUserRides();
    }

    // Método para cargar los viajes del usuario: tanto los reservados como los creados
    public void loadUserRides() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            // Cargar los viajes creados y las reservas del usuario
            bookedRides = businessLogic.getBookingsByUser(currentUser);
            createdRides = businessLogic.getRidesByUser(currentUser);
        }
    }

    // Getters y setters para las listas de reservas y viajes creados
    public List<Booking> getBookedRides() {
        return bookedRides;
    }

    public List<Ride> getCreatedRides() {
        return createdRides;
    }

}
