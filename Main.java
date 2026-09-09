import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException{

        //HASHMAP
        Map<String, Client> clientMap = new HashMap<>();
        Map<String, Pet> petMap = new HashMap<>();
        Map<String, Quote> quoteMap = new HashMap<>();
        Map<String, Service> serviceCatalog = new HashMap<>();


        //Creacion del lector
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Para las opciones
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========== SISTEMA VETERINARIO (SIA) ==========");
            System.out.println("1. Menu Servicios");
            System.out.println("2. Menu Clientes");
            System.out.println("3. Menu Mascotas");
            System.out.println("4. Menu Citas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1" -> ServicioMenu.mostrarMenu(reader, serviceCatalog);
                case "2" -> ClienteMenu.mostrarMenu(reader, clientMap, petMap);
                case "3" -> MascotaMenu.mostrarMenu(reader, clientMap, petMap);
                case "4" -> CitaMenu.mostrarMenu(reader, clientMap, quoteMap, serviceCatalog);

                case "5" -> {
                    exit = true;
                    System.out.println("Saliendo del sistema...");
                }

                default -> System.out.println("Opción no válida.");
            }
        }

    }

}