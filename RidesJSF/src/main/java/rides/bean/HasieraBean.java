package rides.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "Hasiera")
public class HasieraBean {

	public String goToLogin() {
        return "Login?faces-redirect=true"; 
    }

    public String goToRegister() {
        return "Erregistratu?faces-redirect=true";
    }
    
    public String goToQueryRides() {
    	return "QueryRides?faces-redirect=true";
    }
    
    public String saioaItxi() {
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	return "Hasiera?faces-redirect=true";
    }
    
    public String goToBidaiGuztiak() {
    	return "BidaiGuztiak?faces-redirect=true";
    }
}
