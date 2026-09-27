//Excepcion personalizada: se lanza cuando se busca un cliente por RUT y no existe
public class ClienteNoEncontradoException extends Exception {

    //Constructor
    public ClienteNoEncontradoException(String rut){
        super("No se encontró ningún cliente con RUT: " + rut);
    }
}
