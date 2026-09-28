import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException{

        //Cargar datos
        Datos datos = Persistencia.cargarDatos();
        Map<String, Client> clientMap = datos.getClientMap();
        Map<String, Pet> petMap = datos.getPetMap();
        Map<String, Quote> quoteMap = datos.getQuoteMap();
        Map<String, Service> serviceCatalog = datos.getServiceCatalog();

        //Lector
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Elegir modo de ejecucion
        System.out.println("SISTEMA VETERINARIO\n");
        System.out.println("1. Modo Consola");
        System.out.println("2. Modo Ventana");
        System.out.println("Seleccione una opcion: ");
        String modo = reader.readLine();

        if ("2".equals(modo)) {
            VentanaPrincipal ventana = new VentanaPrincipal(clientMap, petMap, quoteMap, serviceCatalog);
            ventana.setVisible(true);
            return;
        }

        //Menu principal
        boolean exit = false;

        while (!exit) {
            System.out.println("SISTEMA VETERINARIO\n");
            System.out.println("1. Menu Servicios");
            System.out.println("2. Menu Clientes");
            System.out.println("3. Menu Mascotas");
            System.out.println("4. Menu Citas");
            System.out.println("5. Salir");
            System.out.println("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1":
                    ServicioMenu.mostrarMenu(reader, serviceCatalog);
                    break;

                case "2":
                    ClienteMenu.mostrarMenu(reader, clientMap, petMap);
                    break;

                case "3":
                    MascotaMenu.mostrarMenu(reader, clientMap, petMap);
                    break;

                case "4":
                    CitaMenu.mostrarMenu(reader, clientMap, quoteMap, serviceCatalog);
                    break;

                case "5":
                    Persistencia.guardarDatos(new Datos(clientMap, petMap, quoteMap, serviceCatalog));
                    exit = true;
                    System.out.println("Datos guardados. Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

    }

}
