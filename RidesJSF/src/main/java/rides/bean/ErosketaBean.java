package rides.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "erosketaBean")
@SessionScoped
public class ErosketaBean {

	public String atzera() {
		return "BidaiaErosi?faces-redirect=true";
	}
}
