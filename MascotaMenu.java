import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class MascotaMenu {

    //Menu de mascotas
    public static void mostrarMenu(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("MENU MASCOTAS\n");
            System.out.println("1. Agregar Mascota");
            System.out.println("2. Mostrar Mascotas");
            System.out.println("3. Buscar Mascota");
            System.out.println("4. Eliminar Mascota");
            System.out.println("5. Volver al Menu Principal");
            System.out.println("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1":
                    agregarMascotaAClienteExistente(reader, clientMap, petMap);
                    break;

                case "2":
                    mostrarMascotas(petMap);
                    break;

                case "3":
                    buscarMascota(reader, petMap);
                    break;

                case "4":
                    eliminarMascota(reader, clientMap, petMap);
                    break;

                case "5":
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    //Buscar cliente existente para agregarle una mascota
    private static void agregarMascotaAClienteExistente(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        System.out.println("Agregar Mascota\n");
        if (clientMap.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema. Registre un cliente primero.");
            return;
        }

        System.out.println("¿Conoce el RUT del dueño de la mascota? (Y/N): ");
        String conoceRut = reader.readLine().trim().toUpperCase();

        if (conoceRut.equals("Y")) {
            System.out.println("Clientes Registrados\n");
            for (Client client : clientMap.values()) {
                System.out.println("- " + client.getName() + " (RUT: " + client.getRut() + " | Tel: " + client.getPhone() + ")");
            }
        }

        System.out.println("\nIngrese el RUT del dueño: ");
        String rutBuscado = reader.readLine().trim();

        try {
            Client clienteEncontrado = ClienteMenu.obtenerClientePorRut(clientMap, rutBuscado);
            registrarMascota(reader, clienteEncontrado, petMap);
        } catch (ClienteNoEncontradoException e) {
            System.out.println("-> " + e.getMessage());
        }
    }

    //Registrar mascota y asignarla al cliente
    public static void registrarMascota(BufferedReader reader, Client client, Map<String, Pet> petMap) throws IOException {
        System.out.println("Registro de Mascota para " + client.getName() + " \n");
        System.out.println("Ingrese ID de la mascota: ");
        String id = reader.readLine().trim();

        if (id.isEmpty()) {
            System.out.println("Error: El ID no puede estar vacío.");
            return;
        }

        if (petMap.containsKey(id)) {
            System.out.println("Error: Ya existe una mascota registrada con ese ID.");
            return;
        }

        System.out.println("Ingrese nombre de la mascota: ");
        String name = reader.readLine();
        int age = leerEdad(reader);

        System.out.println("\nSeleccione la especie de la mascota:");
        System.out.println("1. Perro");
        System.out.println("2. Gato");
        System.out.println("3. Otro");
        System.out.println("Seleccione una opcion: ");
        String opcionEspecie = reader.readLine().trim();

        Pet nuevaMascota;

        switch (opcionEspecie) {
            case "1": {
                String raza = leerRaza(reader, "Ingrese la raza del perro (deje vacío si no tiene raza definida): ");
                //El estado de salud se evalua y se actualiza recien en la primera cita (ver finalizarCita)
                nuevaMascota = new Dog(id, name, age, "Perro", raza, "Sin evaluar");
                break;
            }

            case "2": {
                String raza = leerRaza(reader, "Ingrese la raza del gato (deje vacío si no tiene raza definida): ");
                //El estado de salud se evalua y se actualiza recien en la primera cita (ver finalizarCita)
                nuevaMascota = new Cat(id, name, age, "Gato", raza, "Sin evaluar");
                break;
            }

            case "3": {
                System.out.println("Ingrese la especie de la mascota: ");
                String especie = reader.readLine();
                nuevaMascota = new Pet(id, name, age, especie);
                break;
            }

            default:
                System.out.println("-> Opción no válida. No se registró la mascota.");
                return;
        }

        client.addPet(nuevaMascota);
        petMap.put(id, nuevaMascota);
        System.out.println("-> Mascota '" + name + "' asignada exitosamente a " + client.getName() + ".");
    }

    //Pedir la raza de la mascota, si no se ingresa nada se asume "Sin raza"
    private static String leerRaza(BufferedReader reader, String mensaje) throws IOException {
        System.out.println(mensaje);
        String raza = reader.readLine().trim();

        if (raza.isEmpty()) {
            raza = "Sin raza";
        }

        return raza;
    }

    //Pedir la edad de la mascota validando que sea un numero valido
    private static int leerEdad(BufferedReader reader) throws IOException {
        int age = -1;
        boolean valido = false;

        while (!valido) {
            System.out.println("Ingrese edad de la mascota: ");
            String texto = reader.readLine();
            try {
                age = Integer.parseInt(texto);
                if (age < 0) {
                    System.out.println("-> La edad no puede ser negativa.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("-> Debe ingresar un número válido.");
            }
        }

        return age;
    }

    //Mostrar mascotas registradas
    private static void mostrarMascotas(Map<String, Pet> petMap) {
        System.out.println("\n--- Lista de Mascotas ---");
        if (petMap.isEmpty()) {
            System.out.println("No hay Mascotas registradas.");
        } else {
            for (Pet pet : petMap.values()) {
                System.out.println("\nMascota: " + pet.getName() + " | ID: " + pet.getId() + " | Especie: " + pet.getSpecies() + " | Edad: " + pet.getAge() + pet.getDetalleEspecie());
            }
        }
    }

    //Buscar una mascota especifica por ID
    private static void buscarMascota(BufferedReader reader, Map<String, Pet> petMap) throws IOException {
        System.out.println("\n--- Buscar Mascota ---");
        System.out.println("Ingrese el ID de la mascota: ");
        String id = reader.readLine().trim();

        try {
            Pet pet = obtenerMascotaPorId(petMap, id);
            System.out.println("\nMascota encontrada:");
            System.out.println("Nombre: " + pet.getName() + " | ID: " + pet.getId() + " | Especie: " + pet.getSpecies() + " | Edad: " + pet.getAge() + pet.getDetalleEspecie());
        } catch (MascotaNoEncontradaException e) {
            System.out.println("-> " + e.getMessage());
        }
    }

    //Obtener una mascota por ID, lanzando una excepcion si no existe
    //Sin modificador de acceso para poder reutilizarlo desde otros menus (CitaMenu)
    static Pet obtenerMascotaPorId(Map<String, Pet> petMap, String id) throws MascotaNoEncontradaException {
        Pet pet = petMap.get(id);
        if (pet == null) {
            throw new MascotaNoEncontradaException(id);
        }
        return pet;
    }

    //Eliminar una mascota del sistema y de la lista de su dueño
    private static void eliminarMascota(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        System.out.println("\n--- Eliminar Mascota ---");
        System.out.println("Ingrese el ID de la mascota a eliminar: ");
        String id = reader.readLine().trim();

        try {
            Pet pet = obtenerMascotaPorId(petMap, id);

            //Buscar al dueño para quitarla tambien de su lista de mascotas
            Client duenio = buscarDuenio(clientMap, id);
            if (duenio != null) {
                duenio.removePet(id);
            }

            petMap.remove(id);
            System.out.println("-> Mascota '" + pet.getName() + "' eliminada con éxito.");
        } catch (MascotaNoEncontradaException e) {
            System.out.println("-> " + e.getMessage());
        }
    }

    //Buscar el cliente dueño de una mascota, recorriendo el mapa de clientes
    private static Client buscarDuenio(Map<String, Client> clientMap, String idMascota) {
        for (Client client : clientMap.values()) {
            if (client.getPetById(idMascota) != null) {
                return client;
            }
        }
        return null;
    }
}
