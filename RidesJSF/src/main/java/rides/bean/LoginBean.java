package rides.bean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.HibernateDataAccess;
import domain.User;

@ManagedBean(name = "loginBean")
public class LoginBean {

    private String email;
    private String pasahitza;

    private BLFacade facade;

    public LoginBean() {
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
        	User user = facade.getUserByEmail(email);
        	FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("currentUser", user);
            return "Main?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", "Email-a edo pasahitza okerra"));
            return null; 
        }
    }
    


    public String close() {
        return "Hasiera?faces-redirect=true";
    }
}
