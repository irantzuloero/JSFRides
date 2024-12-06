package rides.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

public class MainBean {

    public String goToQueryRides() {
        return "QueryRides?faces-redirect=true"; //lortu nahi dugun xhtml fitxategiaren izena
    }

    public String goToCreateRide() {
        return "CreateRide?faces-redirect=true";
    }
}