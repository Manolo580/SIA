
import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Client{
    private String rut;
    private String name;
    private String phone;
    //private List<Pet> pets;

    public Client(String rut, String name, String phone){
        this.rut = rut;
        this.name = name;
        this.phone = phone;
        //this.pets = new ArrayList<>();
    }

    //HashMap

    public String getRut(){
        return this.rut;
    }

    //Sobrecarga (Client)

    /*public void addPet(Pet newPet){
        this.pets.add(newPet);
    }

    public void addPet(String id, String name, String species, String race){
        Pet p = new Pet(id, name, species, race);
        this.pet.add(p);
    }*/
}
/*
class Pet{
    private String idPet;
    private String name;
    private String species;
    private String race;

    public Pet(String idPet, String name, String species, String race){
        this.idPet = idPet;
        this.name = name;
        this.species = species;
        this.race = race;
    }
}

class Service{
    private String type;
    private String idService;
    private double cash;

    public Service(String type, String idService, double cash){
        this.type = type;
        this.idService = idService;
        this.cash = cash;
    }

    //Sobrecarga en Service

    public void setPrice(double amount){
        this.cash = amount;
    }

    public void setPrice(double amount, double discount){
        this.cash = amount - discount;
    }
}


class Quote{
    private String idQuote;
    private String date;
    private String status;
    private Client client;
    private Pet pet;
    private Service service;

    public Quote(String date, String service, String status, Client client, Pets pets){
        this.idQuote = idQuote;
        this.date = date;
        this.service = service;
        this.status = status;
        this.client = client;
        this.pets = pets;
    }
}
*/
public class Main {
    public static void main(String[] args) throws IOException{
        Map<String, Client> mapaClientes = new HashMap<>();

        /*List<Quote> quoteList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();*/

        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        boolean continuar = true;

        System.out.println("--- Sistema de Registro de Clientes ---");

        while (continuar) {
            System.out.print("Ingrese el RUT del cliente (o escriba 'salir' para terminar): ");
            // Para leer, el objeto lector invoca el método readLine()
            String rut = lector.readLine();

            if (rut.equalsIgnoreCase("salir")) {
                continuar = false;
                break;
            }

            System.out.print("Ingrese el nombre del cliente: ");
            String nombre = lector.readLine();

            System.out.print("Ingrese el teléfono del cliente: ");
            String telefono = lector.readLine();

            // Creamos el nuevo cliente con los datos ingresados
            Client nuevoCliente = new Client(rut, nombre, telefono);

            // Lo agregamos al HashMap
            mapaClientes.put(nuevoCliente.getRut(), nuevoCliente);
            System.out.println("¡Cliente agregado con éxito!\n");
        }

        // Mostrar el resultado final
        System.out.println("--- Resumen ---");
        System.out.println("Cantidad total de clientes registrados: " + mapaClientes.size());
        
        // Ejemplo de cómo recorrer el mapa para ver los RUTs registrados
        System.out.println("RUTs registrados en el sistema:");
        for (String rutRegistrado : mapaClientes.keySet()) {
            System.out.println("- " + rutRegistrado);
        }
    }

    

}
