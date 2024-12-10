package rides.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "BidaiaErosiBean")
@SessionScoped
public class BidaiaErosiBean {
	private List<String> departCities;
	private List<String> arrivalCities;
	private Date data;
	private List<Ride> filteredRides;
	private String selectedDepartCity;
	private String selectedArrivalCity;

	private Ride selectedRide;
	private int selectedSeats;
    private float totalPrice;
	
	private BLFacade businessLogic;

	public BidaiaErosiBean() {
		businessLogic = FacadeBean.getBusinessLogic();
		this.departCities = new ArrayList<>();
		this.arrivalCities = new ArrayList<>();
		departCities = businessLogic.getDepartCities();
		filteredRides = new ArrayList<>();

	}

	public List<Ride> getFilteredRides() {
		return filteredRides;
	}

	public void setFilteredRides(List<Ride> filteredRides) {
		this.filteredRides = filteredRides;
	}

	public String getSelectedDepartCity() {
		return selectedDepartCity;
	}

	public void setSelectedDepartCity(String selectedDepartCity) {
		System.out.println(selectedDepartCity + "set select metodoan");
		this.selectedDepartCity = selectedDepartCity;
		updateArrivalCities();
	}

	public void updateArrivalCities() {
		if (selectedDepartCity != null && !selectedDepartCity.isEmpty()) {
			arrivalCities = businessLogic.getDestinationCities(selectedDepartCity);
		} else {
			arrivalCities = null;
		}
	}

	public String getSelectedArrivalCity() {
		return selectedArrivalCity;
	}

	public void setSelectedArrivalCity(String selectedArrivalCity) {
		System.out.println(selectedArrivalCity + "set select metodoan");
		this.selectedArrivalCity = selectedArrivalCity;
	}

	public List<String> getDepartCities() {
		return departCities;
	}

	public void setDepartCities(List<String> departCities) {
		this.departCities = departCities;
	}

	public List<String> getArrivalCities() {
		return arrivalCities;
	}

	public void setArrivalCities(List<String> arrivalCities) {
		this.arrivalCities = arrivalCities;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setSelectedRide(Ride selectedRide) {
		this.selectedRide = selectedRide;
	}

	public Ride getSelectedRide() {
		return selectedRide;
	}

	public int getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(int selectedSeats) {
        this.selectedSeats = selectedSeats;
    }


    public float getTotalPrice() {
        return totalPrice;
    }
    

    public void updateTotalPrice() {
        if (selectedRide != null && selectedSeats > 0) {
            this.totalPrice = selectedRide.getPrice() * this.selectedSeats;
        } else {
            this.totalPrice = 0;
        }
    }

    
	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("" + event.getObject()));
	}

	public void updateRides() {
		if (selectedDepartCity != null && selectedArrivalCity != null && data != null) {
			filteredRides = businessLogic.getRides(selectedDepartCity, selectedArrivalCity, data);
			if (filteredRides == null || filteredRides.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ez dago hautatutako irizpideekin bat datozen bidaiarik", ""));
			}
		} else {
			filteredRides = null;
		}
	}

	public void sartuArrivalCities() {
		System.out.println(this.selectedArrivalCity + "listener");
	}

	public String close() {
		return "Main?faces-redirect=true";
	}

	public String erosi() {
		if (selectedRide == null) {
	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	            "Ez dago bidaiarik hautatuta", ""));
	        return null; 
	    }
		System.out.println(selectedRide.getPrice());
		return "goToErosi";
	}
	
	public List<Integer> getAvailableSeats() {
	    List<Integer> seats = new ArrayList<>();
	    if (selectedRide != null && selectedRide.getnPlaces() > 0) {
	        for (int i = 1; i <= selectedRide.getnPlaces(); i++) {
	            seats.add(i);
	        }
	    }
	    return seats;
	}


}
