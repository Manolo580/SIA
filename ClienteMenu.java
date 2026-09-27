import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class ClienteMenu {

    //Menu de clientes
    public static void mostrarMenu(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----- MENU CLIENTES -----");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Volver al Menu Principal");
            System.out.println("Seleccione una opcion: ");

            String option = reader.readLine();

            // Prevención de NullPointerException si el flujo se corta
            if (option == null) break; 

            switch (option) {
                case "1":
                    registrarCliente(reader, clientMap, petMap);
                    break;

                case "2":
                    mostrarClientes(clientMap);
                    break;

                case "3":
                    buscarCliente(reader, clientMap);
                    break;

                case "4":
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    //Registrar cliente nuevo
    private static void registrarCliente(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        System.out.println("\n--- Registro de Cliente ---");
        System.out.println("Ingrese su RUT (11222333-4): ");
        String rut = reader.readLine().trim();
        System.out.println("Ingrese su nombre: ");
        String name = reader.readLine().trim();
        System.out.println("Ingrese un telefono (9 9999 9999): ");
        String phone = reader.readLine().trim();

        if (rut.isEmpty()) {
            System.out.println("-> Error: El RUT no puede estar vacío.");
            return;
        }

        if (!clientMap.containsKey(rut)) {
            Client client = new Client(rut, name, phone);
            clientMap.put(rut, client);
            System.out.println("-> Cliente registrado con éxito.");

            //Preguntar si desea agregar mascota de inmediato
            System.out.println("¿Desea registrar una mascota para este cliente ahora? (S/N): ");
            String resp = reader.readLine();
            
            // --- CORRECCIÓN: CONDICIONAL FLEXIBLE ---
            if (resp != null && resp.trim().toUpperCase().startsWith("S")) {
                MascotaMenu.registrarMascota(reader, client, petMap);
            }

        } else {
            System.out.println("-> El RUT ya está registrado.");
        }
    }

    //Mostrar clientes registrados
    private static void mostrarClientes(Map<String, Client> clientMap) {
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

    //Buscar un cliente especifico por RUT
    private static void buscarCliente(BufferedReader reader, Map<String, Client> clientMap) throws IOException {
        System.out.println("\n--- Buscar Cliente ---");
        System.out.println("Ingrese el RUT del cliente: ");
        String rut = reader.readLine().trim();

        try {
            Client client = obtenerClientePorRut(clientMap, rut);
            System.out.println("\nCliente encontrado:");
            System.out.println("Nombre: " + client.getName() + " | RUT: " + client.getRut() + " | Tel: " + client.getPhone());
            System.out.println("Mascotas registradas: " + client.getPets().size());
        } catch (ClienteNoEncontradoException e) {
            System.out.println("-> " + e.getMessage());
        }
    }

    //Obtener un cliente por RUT, lanzando una excepcion si no existe
    //Sin modificador de acceso para poder reutilizarlo desde otros menus (MascotaMenu, CitaMenu)
    static Client obtenerClientePorRut(Map<String, Client> clientMap, String rut) throws ClienteNoEncontradoException {
        Client client = clientMap.get(rut);
        if (client == null) {
            throw new ClienteNoEncontradoException(rut);
        }
        return client;
    }
}