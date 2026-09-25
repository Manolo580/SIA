import java.io.Serializable;
import java.util.Map;

public class Datos implements Serializable{
    private static final long serialVersionUID = 1L;

    private Map<String, Client> clientMap;
    private Map<String, Pet> petMap;
    private Map<String, Quote> quoteMap;
    private Map<String, Service> serviceCatalog;

    //Constructor
    public Datos(Map<String, Client> clientMap, Map<String, Pet> petMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog){
        this.clientMap = clientMap;
        this.petMap = petMap;
        this.quoteMap = quoteMap;
        this.serviceCatalog = serviceCatalog;
    }

    //Get
    public Map<String, Client> getClientMap() {
        return clientMap;
    }

    public Map<String, Pet> getPetMap() {
        return petMap;
    }

    public Map<String, Quote> getQuoteMap() {
        return quoteMap;
    }

    public Map<String, Service> getServiceCatalog() {
        return serviceCatalog;
    }
}
