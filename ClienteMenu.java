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
            System.out.println("4. Editar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("6. Volver al Menu Principal");
            System.out.println("Seleccione una opcion: ");

            String option = reader.readLine();

            //NullPointerException
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
                    editarCliente(reader, clientMap);
                    break;

                case "5":
                    eliminarCliente(reader, clientMap, petMap);
                    break;

                case "6":
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    //Registrar cliente
    private static void registrarCliente(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        System.out.println("\n--- Registro de Cliente ---");
        System.out.println("Ingrese su RUT sin guion (112223334): ");
        String rut = reader.readLine().trim();
        System.out.println("Ingrese su nombre: ");
        String name = reader.readLine().trim();
        System.out.println("Ingrese un telefono (9 9999 9999): ");
        String phone = reader.readLine().trim();

        if (rut.isEmpty()) {
            System.out.println("-> Error: El RUT no puede estar vacío.");
            return;
        }

        if (!rut.matches("[0-9]+")) {
            System.out.println("-> Error: El RUT solo puede contener números, sin guion.");
            return;
        }

        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("-> Error: El nombre y el telefono no pueden estar vacíos.");
            return;
        }

        if (!clientMap.containsKey(rut)) {
            Client client = new Client(rut, name, phone);
            clientMap.put(rut, client);
            System.out.println("-> Cliente registrado con éxito.");

            //Preguntar mascota
            System.out.println("¿Desea registrar una mascota para este cliente ahora? (S/N): ");
            String resp = reader.readLine();
            
            //Acepta S o s
            if (resp != null && resp.trim().toUpperCase().startsWith("S")) {
                MascotaMenu.registrarMascota(reader, client, petMap);
            }

        } else {
            System.out.println("-> El RUT ya está registrado.");
        }
    }

    //Mostrar clientes
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

    //Buscar cliente
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

    //Buscar cliente por RUT
    //Sin modificador para reutilizar
    static Client obtenerClientePorRut(Map<String, Client> clientMap, String rut) throws ClienteNoEncontradoException {
        Client client = clientMap.get(rut);
        if (client == null) {
            throw new ClienteNoEncontradoException(rut);
        }
        return client;
    }

    //Editar cliente
    private static void editarCliente(BufferedReader reader, Map<String, Client> clientMap) throws IOException {
        System.out.println("\n--- Editar Cliente ---");
        System.out.println("Ingrese el RUT del cliente a editar: ");
        String rut = reader.readLine().trim();

        try {
            Client client = obtenerClientePorRut(clientMap, rut);

            System.out.println("¿Qué desea actualizar?");
            System.out.println("1. Solo el telefono");
            System.out.println("2. Nombre y telefono");
            System.out.println("Seleccione una opcion: ");
            String opcion = reader.readLine().trim();

            switch (opcion) {
                case "1": {
                    System.out.println("Ingrese el nuevo telefono: ");
                    String phone = reader.readLine().trim();

                    if (phone.isEmpty()) {
                        System.out.println("-> Error: El telefono no puede estar vacío.");
                        break;
                    }

                    client.actualizarDatos(phone); //Sobrecarga
                    System.out.println("-> Telefono actualizado con éxito.");
                    break;
                }

                case "2": {
                    System.out.println("Ingrese el nuevo nombre: ");
                    String name = reader.readLine().trim();
                    System.out.println("Ingrese el nuevo telefono: ");
                    String phone = reader.readLine().trim();

                    if (name.isEmpty() || phone.isEmpty()) {
                        System.out.println("-> Error: El nombre y el telefono no pueden estar vacíos.");
                        break;
                    }

                    client.actualizarDatos(name, phone); //Sobrecarga
                    System.out.println("-> Nombre y telefono actualizados con éxito.");
                    break;
                }

                default:
                    System.out.println("-> Opción no válida. No se actualizaron los datos.");
                    break;
            }
        } catch (ClienteNoEncontradoException e) {
            System.out.println("-> " + e.getMessage());
        }
    }

    //Para eliminar cliente con sus mascotas
    private static void eliminarCliente(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        System.out.println("\n--- Eliminar Cliente ---");
        System.out.println("Ingrese el RUT del cliente a eliminar: ");
        String rut = reader.readLine().trim();

        try {
            Client client = obtenerClientePorRut(clientMap, rut);

            //Eliminar mascotas
            for (Pet pet : client.getPets()) {
                petMap.remove(pet.getId());
            }

            clientMap.remove(rut);
            System.out.println("-> Cliente '" + client.getName() + "' y sus mascotas fueron eliminados con éxito.");
        } catch (ClienteNoEncontradoException e) {
            System.out.println("-> " + e.getMessage());
        }
    }
}