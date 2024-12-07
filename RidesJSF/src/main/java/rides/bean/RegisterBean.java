package rides.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import businessLogic.BLFacadeImplementation;
import dataAccess.HibernateDataAccess;
import exceptions.UserAlreadyExistsException;

@ManagedBean(name = "registerBean")
public class RegisterBean {

    private String izena;
    private String email;
    private String pasahitza;
    private String pasahitza2;

    private BLFacadeImplementation businessLogic; 

    public RegisterBean() {
        this.businessLogic = new BLFacadeImplementation(new HibernateDataAccess());
    }
    
    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
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

    public String getPasahitza2() {
        return pasahitza2;
    }

    public void setPasahitza2(String pasahitza2) {
        this.pasahitza2 = pasahitza2;
    }

    public String sortu() {
        if (pasahitza.equals(pasahitza2)) {
            try {
                // Llamamos al método registerUser que ahora crea tanto User como Driver
                businessLogic.registerUser(izena, email, pasahitza);
                return "Login?faces-redirect=true";  // Redirigir al login después de registrar
            } catch (UserAlreadyExistsException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario con este correo electrónico ya existe.", null));
                return null;  // Mantener en la misma página si el usuario ya existe
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.", null));
            return null;  // Mantener en la misma página si las contraseñas no coinciden
        }
    }
}
