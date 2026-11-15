public class ClienteNoEncontradoException extends Exception {

    //Constructor
    public ClienteNoEncontradoException(String rut){
        super("No se encontró ningún cliente con RUT: " + rut);
    }
}
