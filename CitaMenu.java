import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitaMenu {

    public static void mostrarMenu(BufferedReader reader, Map<String, Client> clientMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----- MENU CITAS (SERVICIOS) -----");
            System.out.println("1. Agendar Cita");
            System.out.println("2. Mostrar Citas Pendientes");
            System.out.println("3. Finalizar Cita");
            System.out.println("4. Historial de Citas");
            System.out.println("5. Calendario de Citas");
            System.out.println("6. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1" -> agendarCita(reader, clientMap, quoteMap, serviceCatalog);
                case "2" -> mostrarCitasPendientes(quoteMap);
                case "3" -> finalizarCita(reader, quoteMap);
                case "4" -> historialCitas(quoteMap);
                case "5" -> calendarioCitas(quoteMap);
                case "6" -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void agendarCita(BufferedReader reader, Map<String, Client> clientMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog) throws IOException {
        // AGENDAR CITA
        System.out.println("\n--- Agendar Cita ---");
        if (clientMap.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema. Registre un cliente primero.");
            return;
        }

        System.out.print("Ingrese el RUT del dueño: ");
        String rutCita = reader.readLine();
        Client clienteCita = clientMap.get(rutCita);

        if (clienteCita == null) {
            System.out.println("-> Error: No se encontró ningún cliente con ese RUT.");
            return;
        }

        if (clienteCita.getPets().isEmpty()) {
            System.out.println("-> Este cliente no tiene mascotas registradas. Agregue una mascota primero.");
            return;
        }

        System.out.println("\n--- Mascotas de " + clienteCita.getName() + " ---");
        for (Pet p : clienteCita.getPets()) {
            System.out.println("- ID: " + p.getId() + " | Nombre: " + p.getName() + " | Especie: " + p.getSpecies() + " | Edad: " + p.getAge() + MascotaMenu.detalleEspecie(p));
        }

        System.out.print("\nIngrese el ID de la mascota para la cita: ");
        String idMascotaCita = reader.readLine();
        Pet mascotaCita = clienteCita.getPetById(idMascotaCita);

        if (mascotaCita == null) {
            System.out.println("-> Error: Esa mascota no pertenece a este cliente.");
            return;
        }

        System.out.println("\n--- Servicios Disponibles ---");
        if (serviceCatalog.isEmpty()) {
            System.out.println("No hay servicios registrados. Agregue uno desde el Menu Servicios.");
            return;
        }
        for (Service s : serviceCatalog.values()) {
            System.out.println("- ID: " + s.getIdService() + " | Tipo: " + s.getType());
        }

        System.out.print("\nIngrese el ID del servicio a agendar: ");
        String idServicio = reader.readLine();
        Service servicioBase = serviceCatalog.get(idServicio);

        if (servicioBase == null) {
            System.out.println("-> Error: Ese servicio no existe. Debe ser uno de los servicios previamente creados.");
            return;
        }

        System.out.print("Ingrese el monto del servicio: ");
        double monto = Double.parseDouble(reader.readLine());

        Service servicioCita = new Service(servicioBase.getType(), servicioBase.getIdService(), 0);

        System.out.print("¿Tiene descuento? (S/N): ");
        String tieneDescuento = reader.readLine().toUpperCase();
        if (tieneDescuento.equals("S")) {
            System.out.print("Ingrese el monto del descuento: ");
            double descuento = Double.parseDouble(reader.readLine());
            servicioCita.setPrice(monto, descuento); // Sobrecarga con descuento
        } else {
            servicioCita.setPrice(monto); // Sobrecarga sin descuento
        }

        String fechaCita = pedirFecha(reader, "Ingrese la fecha de la cita (dd-mm-aaaa): ");
        System.out.print("Ingrese la hora de la cita (hh:mm): ");
        String horaCita = reader.readLine();

        String idCita = String.valueOf(quoteMap.size() + 1);
        Quote cita = new Quote(idCita, fechaCita, horaCita, "Pendiente", clienteCita, mascotaCita, servicioCita);
        quoteMap.put(idCita, cita);

        System.out.println("-> Cita " + idCita + " agendada con éxito para " + mascotaCita.getName() + ".");

        // MENU RAPIDO POST-AGENDAMIENTO
        boolean seguir = true;
        while (seguir) {
            System.out.println("\n¿Qué desea hacer ahora?");
            System.out.println("1. Agendar otra cita");
            System.out.println("2. Finalizar una cita");
            System.out.println("3. Volver al Menu de Citas");
            System.out.print("Seleccione una opcion: ");
            String siguienteOpcion = reader.readLine();

            switch (siguienteOpcion) {
                case "1" -> {
                    agendarCita(reader, clientMap, quoteMap, serviceCatalog);
                    seguir = false;
                }
                case "2" -> {
                    finalizarCita(reader, quoteMap);
                    seguir = false;
                }
                case "3" -> seguir = false;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarCitasPendientes(Map<String, Quote> quoteMap) {
        // MOSTRAR CITAS PENDIENTES
        System.out.println("\n--- Citas Pendientes ---");
        List<Quote> pendientes = obtenerCitasPendientes(quoteMap);

        if (pendientes.isEmpty()) {
            System.out.println("No hay citas pendientes.");
        } else {
            for (Quote cita : pendientes) {
                imprimirCita(cita);
            }
        }
    }

    private static void finalizarCita(BufferedReader reader, Map<String, Quote> quoteMap) throws IOException {
        // FINALIZAR CITA
        System.out.println("\n--- Finalizar Cita ---");

        List<Quote> pendientes = obtenerCitasPendientes(quoteMap);

        if (pendientes.isEmpty()) {
            System.out.println("No hay citas pendientes para finalizar.");
            return;
        }

        System.out.println("\n--- Citas Pendientes ---");
        for (Quote cita : pendientes) {
            System.out.println("- ID Cita: " + cita.getIdQuote() + " | " + cita.getDate() + " " + cita.getHour()
                    + " | Cliente: " + cita.getClient().getName() + " | Mascota: " + cita.getPet().getName()
                    + " | Servicio: " + cita.getService().getType());
        }

        System.out.print("\nIngrese el ID de la cita a finalizar: ");
        String idCita = reader.readLine();
        Quote cita = quoteMap.get(idCita);

        if (cita == null || !cita.getStatus().equals("Pendiente")) {
            System.out.println("-> Error: No existe una cita pendiente con ese ID.");
            return;
        }

        if (esServicioConResultado(cita.getService())) {
            System.out.print("Ingrese el resultado de la cita (diagnóstico/observaciones): ");
            String resultado = reader.readLine();
            cita.setResultado(resultado);
        }

        cita.setStatus("Finalizada");
        System.out.println("-> Cita " + cita.getIdQuote() + " finalizada con éxito.");
    }

    private static void historialCitas(Map<String, Quote> quoteMap) {
        // HISTORIAL DE CITAS FINALIZADAS
        System.out.println("\n--- Historial de Citas Finalizadas ---");
        boolean hayFinalizadas = false;

        for (Quote cita : quoteMap.values()) {
            if (cita.getStatus().equals("Finalizada")) {
                hayFinalizadas = true;
                imprimirCita(cita);
                if (cita.getResultado() != null) {
                    System.out.println("Resultado: " + cita.getResultado());
                }
            }
        }

        if (!hayFinalizadas) {
            System.out.println("Aún no hay citas finalizadas.");
        }
    }

    private static void calendarioCitas(Map<String, Quote> quoteMap) {
        // CALENDARIO: TODAS LAS CITAS PENDIENTES, DE MAS PROXIMA A MAS LEJANA
        System.out.println("\n--- Calendario de Citas Pendientes ---");
        List<Quote> pendientes = obtenerCitasPendientes(quoteMap);

        if (pendientes.isEmpty()) {
            System.out.println("No hay citas pendientes.");
            return;
        }

        pendientes.sort((a, b) -> {
            int cmpFecha = Integer.compare(valorFecha(a.getDate()), valorFecha(b.getDate()));
            if (cmpFecha != 0) {
                return cmpFecha;
            }
            return a.getHour().compareTo(b.getHour());
        });

        for (Quote cita : pendientes) {
            System.out.println(cita.getDate() + " " + cita.getHour() + " - " + cita.getPet().getName()
                    + " (" + cita.getClient().getName() + ") - " + cita.getService().getType());
        }
    }

    //===================== METODOS DE APOYO =====================

    private static List<Quote> obtenerCitasPendientes(Map<String, Quote> quoteMap) {
        List<Quote> pendientes = new ArrayList<>();
        for (Quote cita : quoteMap.values()) {
            if (cita.getStatus().equals("Pendiente")) {
                pendientes.add(cita);
            }
        }
        return pendientes;
    }

    private static boolean esServicioConResultado(Service service) {
        // Solo se pide resultado si el servicio es medico, de urgencia o vacuna
        String tipo = service.getType().toLowerCase();
        return tipo.contains("medic") || tipo.contains("médic") || tipo.contains("urgencia") || tipo.contains("vacuna");
    }

    private static void imprimirCita(Quote cita) {
        System.out.println("\nCita: " + cita.getIdQuote() + " | Estado: " + cita.getStatus());
        System.out.println("Fecha: " + cita.getDate() + " | Hora: " + cita.getHour());
        System.out.println("Cliente: " + cita.getClient().getName() + " | RUT: " + cita.getClient().getRut());
        System.out.println("Mascota: " + cita.getPet().getName() + " | ID: " + cita.getPet().getId());
        System.out.println("Servicio: " + cita.getService().getType() + " | Precio: $" + cita.getService().getCash());
    }

    private static String pedirFecha(BufferedReader reader, String mensaje) throws IOException {
        String fecha = null;
        while (fecha == null) {
            System.out.print(mensaje);
            String texto = reader.readLine();
            fecha = formatearFecha(texto);
            if (fecha == null) {
                System.out.println("-> Formato inválido. Use dd-mm-aaaa.");
            }
        }
        return fecha;
    }

    private static String formatearFecha(String texto) {
        // Valida y normaliza una fecha dd-mm-aaaa sin usar java.time
        String[] partes = texto.split("-");
        if (partes.length != 3) {
            return null;
        }

        try {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12) {
                return null;
            }

            return String.format("%02d-%02d-%04d", dia, mes, anio);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int valorFecha(String fecha) {
        // Convierte dd-mm-aaaa en un numero comparable, ej: 08-09-2026 -> 20260908
        String[] partes = fecha.split("-");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);
        return anio * 10000 + mes * 100 + dia;
    }
}
