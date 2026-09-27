import java.io.Serializable;
import java.util.HashMap;
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
    //Se retorna una copia de cada mapa para proteger la encapsulación original
    public Map<String, Client> getClientMap() {
        return new HashMap<>(clientMap);
    }

    public Map<String, Pet> getPetMap() {
        return new HashMap<>(petMap);
    }

    public Map<String, Quote> getQuoteMap() {
        return new HashMap<>(quoteMap);
    }

    public Map<String, Service> getServiceCatalog() {
        return new HashMap<>(serviceCatalog);
    }
}
