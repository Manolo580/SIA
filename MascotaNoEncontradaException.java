//Excepcion personalizada: se lanza cuando se busca una mascota por ID y no existe
public class MascotaNoEncontradaException extends Exception {

    //Constructor
    public MascotaNoEncontradaException(String id){
        super("No se encontró ninguna mascota con ID: " + id);
    }
}
