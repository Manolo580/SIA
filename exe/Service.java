import java.io.Serializable;

public class Service implements Serializable{
    private static final long serialVersionUID = 1L;

    private String type;
    private String idService;
    private double cash;

    //Constructor
    public Service(String type, String idService, double cash){
        this.type = type;
        this.idService = idService;
        this.cash = cash;
    }

    //Get
    public String getType() {
        return type;
    }

    public String getIdService() {
        return idService;
    }

    public double getCash() {
        return cash;
    }

    //Set
    public void setType(String type) {
        this.type = type;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    //Sobrecarga
    public void setPrice(double amount){
        this.cash = amount;
    }

    //Sobrecarga
    public void setPrice(double amount, double discount){
        this.cash = amount - discount;
    }
}
