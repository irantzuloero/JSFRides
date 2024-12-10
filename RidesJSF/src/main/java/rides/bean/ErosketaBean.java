package rides.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "erosketaBean")
@SessionScoped
public class ErosketaBean {

    private int selectedSeats;
    private float totalPrice;
    private float price;

    @ManagedProperty(value = "#{BidaiaErosiBean}")
    private BidaiaErosiBean bidaiaErosiBean;

    @PostConstruct
    public void init() {
        updatePriceFromSelectedRide();
    }

    public int getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(int selectedSeats) {
        this.selectedSeats = selectedSeats;
        setTotalPrice(); // Actualizar el precio total al cambiar los asientos
    }

    public float getPrice() {
        return price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public BidaiaErosiBean getBidaiaErosiBean() {
        return bidaiaErosiBean;
    }

    public void setBidaiaErosiBean(BidaiaErosiBean bidaiaErosiBean) {
        this.bidaiaErosiBean = bidaiaErosiBean;
        updatePriceFromSelectedRide(); // Actualizar precio cuando se inyecta BidaiaErosiBean
    }

    private void setPrice(float price) {
        this.price = price;
    }

    private void setTotalPrice() {
        this.totalPrice = this.price * this.selectedSeats;
    }

    public void updatePriceFromSelectedRide() {
        if (bidaiaErosiBean != null && bidaiaErosiBean.getSelectedRide() != null) {
            setPrice(bidaiaErosiBean.getSelectedRide().getPrice());
            setTotalPrice();
        }
    }
}
