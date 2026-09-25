import java.io.Serializable;

public class Quote implements Serializable{
    private static final long serialVersionUID = 1L;

    private String idQuote;
    private String date;
    private String hour;
    private String status;
    private String resultado;
    private Client client;
    private Pet pet;
    private Service service;

    //Constructor
    public Quote(String idQuote, String date, String hour, String status, Client client, Pet pet, Service service){
        this.idQuote = idQuote;
        this.date = date;
        this.hour = hour;
        this.status = status;
        this.client = client;
        this.pet = pet;
        this.service = service;
    }

    //Get
    public String getIdQuote() {
        return idQuote;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Pet getPet() {
        return pet;
    }

    public Service getService() {
        return service;
    }

    public String getResultado() {
        return resultado;
    }

    //Set
    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
