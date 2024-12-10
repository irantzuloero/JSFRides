package rides.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Driver;
import domain.User;

public class CreateRideBean implements Serializable {
	private String departCity;
	private String arrivalCity;
	private Date rideDate;
	private int numSeats;
	private Driver driver;
	private float price;
	private BLFacade facadeBL;
	private static final long serialVersionUID = 1L;

	public CreateRideBean() {
		this.facadeBL = FacadeBean.getBusinessLogic();
		this.numSeats = 1;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Date getRideDate() {
		return rideDate;
	}

	public void setRideDate(Date rideDate) {
		this.rideDate = rideDate;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public BLFacade getFacadeBL() {
		return facadeBL;
	}

	public void setFacadeBL(BLFacade facadeBL) {
		this.facadeBL = facadeBL;
	}

	public void createRide() {

		try {
	        // Obtener el usuario actual de la sesión
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
	        User currentUser = (User) session.getAttribute("currentUser");

	        if (currentUser != null) {
	            String userEmail = currentUser.getEmail(); // Email del usuario en sesión

	            // Llamar a la lógica de negocio para crear el viaje
	            facadeBL.createRide(departCity, arrivalCity, rideDate, numSeats, price, userEmail);

	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Bidaia sortu da", null));
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erabiltzailea ez da saioa hasi.", null));
	        }
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
	    }

	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("" + event.getObject()));
	}

	public String close() {
		return "Main?faces-redirect=true";
	}
	

}
