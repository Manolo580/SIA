import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class ServicioMenu {

    public static void mostrarMenu(BufferedReader reader, Map<String, Service> serviceCatalog) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----- MENU SERVICIOS -----");
            System.out.println("1. Agregar Servicio");
            System.out.println("2. Mostrar Servicios");
            System.out.println("3. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1" -> agregarServicio(reader, serviceCatalog);
                case "2" -> mostrarServicios(serviceCatalog);
                case "3" -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void agregarServicio(BufferedReader reader, Map<String, Service> serviceCatalog) throws IOException {
        // AGREGAR SERVICIO AL CATALOGO
        System.out.println("\n--- Agregar Servicio ---");
        System.out.print("Ingrese el ID del servicio: ");
        String idServicio = reader.readLine();

        if (serviceCatalog.containsKey(idServicio)) {
            System.out.println("-> Error: Ya existe un servicio con ese ID.");
            return;
        }

        System.out.print("Ingrese el tipo de servicio (ej: Baño, Vacuna, Consulta Medica, Urgencia): ");
        String tipo = reader.readLine();

        Service servicio = new Service(tipo, idServicio, 0);
        serviceCatalog.put(idServicio, servicio);
        System.out.println("-> Servicio '" + tipo + "' agregado con éxito.");
    }

    private static void mostrarServicios(Map<String, Service> serviceCatalog) {
        System.out.println("\n--- Servicios Disponibles ---");
        if (serviceCatalog.isEmpty()) {
            System.out.println("No hay servicios registrados.");
        } else {
            for (Service servicio : serviceCatalog.values()) {
                System.out.println("- ID: " + servicio.getIdService() + " | Tipo: " + servicio.getType());
            }
        }
    }
}
