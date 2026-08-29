import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException{

        //HASHMAP
        Map<String, Client> clientMap = new HashMap<>();


        //Creacion del lector
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Para las opciones
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========== SISTEMA VETERINARIO (SIA) ==========");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Agregar Mascota");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            String option = reader.readLine();
            
            switch (option) {
                case "1" -> {
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
                            registrarMascota(reader, client);
                        }

                    } else {
                        System.out.println("-> El RUT ya está registrado.");
                    }
                }

                case "2" -> {
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

                case "3" -> {
                    // AGREGAR MASCOTA A CLIENTE EXISTENTE
                    System.out.println("\n--- Agregar Mascota ---");
                    if (clientMap.isEmpty()) {
                        System.out.println("No hay clientes registrados en el sistema. Registre un cliente primero.");
                        break;
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
                        registrarMascota(reader, clienteEncontrado);
                    } else {
                        System.out.println("-> Error: No se encontró ningún cliente con ese RUT.");
                    }
                }

                case "4" -> {
                    exit = true;
                    System.out.println("Saliendo del sistema...");
                }

                default -> System.out.println("Opción no válida.");
            }
        }

    }

    //METODOS

    private static void registrarMascota(BufferedReader reader, Client client) throws IOException {
        System.out.println("\n--- Registro de Mascota para " + client.getName() + " ---");
        System.out.print("Ingrese ID de la mascota: ");
        String id = reader.readLine();
        System.out.print("Ingrese nombre de la mascota: ");
        String name = reader.readLine();
        System.out.print("Ingrese edad de la mascota: ");
        int age = Integer.parseInt(reader.readLine());
        
        System.out.print("¿Es Perro (P) o Gato (G)?: ");
        String tipo = reader.readLine().toUpperCase();
        
        System.out.print("Ingrese la raza: ");
        String raza = reader.readLine();
        System.out.print("Ingrese su estado de salud: ");
        String salud = reader.readLine();

        Pet nuevaMascota;

        // Aquí aplicamos Polimorfismo: La referencia es de la súperclase (Pet) 
        // pero la instanciación es de la subclase correspondiente.
        if (tipo.equals("P")) {
            nuevaMascota = new Dog(id, name, age, raza, salud);
        } else if (tipo.equals("G")) {
            nuevaMascota = new Cat(id, name, age, raza, salud);
        } else {
            System.out.println("Tipo no reconocido. Se guardará como Mascota genérica.");
            nuevaMascota = new Pet(id, name, age);
        }

        client.addPet(nuevaMascota);
        System.out.println("-> Mascota '" + name + "' asignada exitosamente a " + client.getName() + ".");
    }

}