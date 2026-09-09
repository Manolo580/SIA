public class Service{
    private String type;
    private String idService;
    private double cash;

    public Service(String type, String idService, double cash){
        this.type = type;
        this.idService = idService;
        this.cash = cash;
    }

    //GET

    public String getType() {
        return type;
    }

    public String getIdService() {
        return idService;
    }

    public double getCash() {
        return cash;
    }

    //SET

    public void setType(String type) {
        this.type = type;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    //Sobrecarga en Service

    public void setPrice(double amount){
        this.cash = amount;
    }

    public void setPrice(double amount, double discount){
        this.cash = amount - discount;
    }
}