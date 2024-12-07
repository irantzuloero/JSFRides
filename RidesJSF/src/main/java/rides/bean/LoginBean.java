package rides.bean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.HibernateDataAccess;

@ManagedBean(name = "loginBean")
public class LoginBean {

    private String email;
    private String pasahitza;

    private BLFacade facade; // Inyecta o inicializa esto según sea necesario

    public LoginBean() {
        // Esto es solo un ejemplo; probablemente uses un patrón de inyección de dependencias
        HibernateDataAccess dataAccess = new HibernateDataAccess();
        facade = new BLFacadeImplementation(dataAccess);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public String sartu() {
        if (facade.isValidUser(email, pasahitza)) {
            return "Main?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", "Invalid email or password"));
            return null; 
        }
    }

    public String close() {
        return "Hasiera?faces-redirect=true";
    }
}
