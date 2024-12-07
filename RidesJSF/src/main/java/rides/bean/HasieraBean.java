package rides.bean;

import javax.faces.bean.ManagedBean;

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
    	return "Hasiera?faces-redirect=true";
    }
}
