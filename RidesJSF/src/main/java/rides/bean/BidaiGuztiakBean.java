package rides.bean;

import java.util.Date;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class BidaiGuztiakBean {
	
	private QueryRidesBean queryRidesBean;
	
	 public BidaiGuztiakBean() {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        queryRidesBean = (QueryRidesBean) facesContext.getApplication()
	                                                     .evaluateExpressionGet(facesContext, "#{QueryRides}", QueryRidesBean.class);
	    }

    public QueryRidesBean getQueryRidesBean() {
        return queryRidesBean;
    }

    public void setQueryRidesBean(QueryRidesBean queryRidesBean) {
        this.queryRidesBean = queryRidesBean;
    }
    
    public String goToBidaiGuztiak2() {
       queryRidesBean.bidaiGuztiak(); 
        return "BidaiGuztiak2?faces-redirect=true";
    }
}
