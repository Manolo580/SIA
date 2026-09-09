import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class MascotaMenu {

    public static void mostrarMenu(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----- MENU MASCOTAS -----");
            System.out.println("1. Agregar Mascota");
            System.out.println("2. Mostrar Mascotas");
            System.out.println("3. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1" -> agregarMascotaAClienteExistente(reader, clientMap, petMap);
                case "2" -> mostrarMascotas(petMap);
                case "3" -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void agregarMascotaAClienteExistente(BufferedReader reader, Map<String, Client> clientMap, Map<String, Pet> petMap) throws IOException {
        // AGREGAR MASCOTA A CLIENTE EXISTENTE
        System.out.println("\n--- Agregar Mascota ---");
        if (clientMap.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema. Registre un cliente primero.");
            return;
        }

        System.out.print("¿Conoce el RUT del dueño de la mascota? (S/N): ");
        String conoceRut = reader.readLine().toUpperCase();

        if (conoceRut.equals("N")) {
            System.out.println("\n--- Clientes Registrados ---");
            for (Client client : clientMap.values()) {
                System.out.println("- " + client.getName() + " (RUT: " + client.getRut() + " | Tel: " + client.getPhone() + ")");
            }
        }

        System.out.print("\nIngrese el RUT del dueño: ");
        String rutBuscado = reader.readLine();

        Client clienteEncontrado = clientMap.get(rutBuscado);

        if (clienteEncontrado != null) {
            registrarMascota(reader, clienteEncontrado, petMap);
        } else {
            System.out.println("-> Error: No se encontró ningún cliente con ese RUT.");
        }
    }

    public static void registrarMascota(BufferedReader reader, Client client, Map<String, Pet> petMap) throws IOException {
        System.out.println("\n--- Registro de Mascota para " + client.getName() + " ---");
        System.out.print("Ingrese ID de la mascota: ");
        String id = reader.readLine();

        if (petMap.containsKey(id)) {
            System.out.println("-> Error: Ya existe una mascota registrada con ese ID.");
            return;
        }

        System.out.print("Ingrese nombre de la mascota: ");
        String name = reader.readLine();
        System.out.print("Ingrese edad de la mascota: ");
        int age = Integer.parseInt(reader.readLine());

        System.out.println("\nSeleccione la especie de la mascota:");
        System.out.println("1. Perro");
        System.out.println("2. Gato");
        System.out.println("3. Otro");
        System.out.print("Seleccione una opcion: ");
        String opcionEspecie = reader.readLine();

        Pet nuevaMascota;

        switch (opcionEspecie) {
            case "1" -> {
                System.out.print("Ingrese la raza del perro: ");
                String raza = reader.readLine();
                System.out.print("Ingrese algún problema de salud (si no tiene, escriba 'Ninguno'): ");
                String problemaSalud = reader.readLine();
                nuevaMascota = new Dog(id, name, age, "Perro", raza, problemaSalud);
            }

            case "2" -> {
                System.out.print("Ingrese la raza del gato: ");
                String raza = reader.readLine();
                System.out.print("Ingrese el estado de salud del gato: ");
                String salud = reader.readLine();
                nuevaMascota = new Cat(id, name, age, "Gato", raza, salud);
            }

            case "3" -> {
                System.out.print("Ingrese la especie de la mascota: ");
                String especie = reader.readLine();
                nuevaMascota = new Pet(id, name, age, especie);
            }

            default -> {
                System.out.println("-> Opción no válida. No se registró la mascota.");
                return;
            }
        }

        client.addPet(nuevaMascota);
        petMap.put(id, nuevaMascota);
        System.out.println("-> Mascota '" + name + "' asignada exitosamente a " + client.getName() + ".");
    }

    private static void mostrarMascotas(Map<String, Pet> petMap) {
        // MOSTRAR MASCOTAS
        System.out.println("\n--- Lista de Mascotas ---");
        if (petMap.isEmpty()) {
            System.out.println("No hay Mascotas registradas.");
        } else {
            for (Pet pet : petMap.values()) {
                System.out.println("\nMascota: " + pet.getName() + " | ID: " + pet.getId() + " | Especie: " + pet.getSpecies() + " | Edad: " + pet.getAge() + detalleEspecie(pet));
            }
        }
    }

    public static String detalleEspecie(Pet pet) {
        // Muestra los datos extra segun la subclase real de la mascota (polimorfismo)
        if (pet instanceof Dog dog) {
            return " | Raza: " + dog.getRaza() + " | Problema de salud: " + dog.getProblemaSalud();
        } else if (pet instanceof Cat cat) {
            return " | Raza: " + cat.getRaza() + " | Salud: " + cat.getSalud();
        }
        return "";
    }
}
