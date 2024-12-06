package rides.bean;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.HibernateDataAccess;  // Import HibernateDataAccess

public class FacadeBean {
    private static FacadeBean singleton = new FacadeBean();
    private static BLFacade facadeInterface;

    private FacadeBean() {
        try {
            // Create HibernateDataAccess instance and pass it to BLFacadeImplementation constructor
            facadeInterface = new BLFacadeImplementation(new HibernateDataAccess());  
        } catch (Exception e) {
            System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
        }
    }

    public static BLFacade getBusinessLogic() {
        return facadeInterface;
    }
}
