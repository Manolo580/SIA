import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class ClienteMenu {

    public static void mostrarMenu(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----- MENU CLIENTES -----");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1" -> registrarCliente(reader, clientMap, petMap);
                case "2" -> mostrarClientes(clientMap);
                case "3" -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrarCliente(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        // INSERTAR CLIENTE
        System.out.println("\n--- Registro de Cliente ---");
        System.out.print("Ingrese su RUT (11222333-4): ");
        String rut = reader.readLine();
        System.out.print("Ingrese su nombre: ");
        String name = reader.readLine();
        System.out.print("Ingrese un telefono (9 9999 9999): ");
        String phone = reader.readLine();

        if (!clientMap.containsKey(rut)) {
            Client client = new Client(rut, name, phone);
            clientMap.put(rut, client);
            System.out.println("-> Cliente registrado con éxito.");

            // PREGUNTAR SI DESEA AGREGAR MASCOTA INMEDIATAMENTE
            System.out.print("¿Desea registrar una mascota para este cliente ahora? (S/N): ");
            String resp = reader.readLine().toUpperCase();
            if (resp.equals("S")) {
                MascotaMenu.registrarMascota(reader, client, petMap);
            }

        } else {
            System.out.println("-> El RUT ya está registrado.");
        }
    }

    private static void mostrarClientes(Map<String, Client> clientMap) {
        // MOSTRAR CLIENTES
        System.out.println("\n--- Lista de Clientes ---");
        if (clientMap.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Client client : clientMap.values()) {
                System.out.println("\nCliente: " + client.getName() + " | RUT: " + client.getRut() + " | Tel: " + client.getPhone());
                System.out.println("Mascotas registradas: " + client.getPets().size());
            }
        }
    }
}
