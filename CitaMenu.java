import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitaMenu {

    //Menu de citas
    public static void mostrarMenu(BufferedReader reader, Map<String, Client> clientMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog) throws IOException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----- MENU CITAS (SERVICIOS) -----");
            System.out.println("1. Agendar Cita");
            System.out.println("2. Calendario de Citas");
            System.out.println("3. Finalizar Cita");
            System.out.println("4. Historial de Citas");
            System.out.println("5. Volver al Menu Principal");
            System.out.println("Seleccione una opcion: ");

            String option = reader.readLine();

            switch (option) {
                case "1":
                    agendarCita(reader, clientMap, quoteMap, serviceCatalog);
                    break;

                case "2":
                    calendarioCitas(quoteMap);
                    break;

                case "3":
                    finalizarCita(reader, quoteMap);
                    break;

                case "4":
                    historialCitas(quoteMap);
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

    //Agendar una nueva cita
    private static void agendarCita(BufferedReader reader, Map<String, Client> clientMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog) throws IOException {
        System.out.println("\n--- Agendar Cita ---");
        if (clientMap.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema. Registre un cliente primero.");
            return;
        }

        System.out.println("Ingrese el RUT del dueño: ");
        String rutCita = reader.readLine().trim();
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
            System.out.println("- ID: " + p.getId() + " | Nombre: " + p.getName() + " | Especie: " + p.getSpecies() + " | Edad: " + p.getAge() + p.getDetalleEspecie());
        }

        System.out.println("\nIngrese el ID de la mascota para la cita: ");
        String idMascotaCita = reader.readLine().trim();
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

        System.out.println("\nIngrese el ID del servicio a agendar: ");
        String idServicio = reader.readLine().trim();
        Service servicioBase = serviceCatalog.get(idServicio);

        if (servicioBase == null) {
            System.out.println("-> Error: Ese servicio no existe. Debe ser uno de los servicios previamente creados.");
            return;
        }

        double monto = leerMonto(reader, "Ingrese el monto del servicio: ");

        Service servicioCita = new Service(servicioBase.getType(), servicioBase.getIdService(), 0);

        System.out.println("¿Tiene descuento? (S/N): ");
        String tieneDescuento = reader.readLine().trim().toUpperCase();
        if (tieneDescuento.startsWith("S")) {
            double descuento = leerDescuento(reader, monto);
            servicioCita.setPrice(monto, descuento); //Sobrecarga con descuento
        } else {
            servicioCita.setPrice(monto); //Sobrecarga sin descuento
        }

        String fechaCita = pedirFecha(reader, "Ingrese la fecha de la cita (dd-mm-aaaa): ");
        System.out.println("Ingrese la hora de la cita (hh:mm): ");
        String horaCita = reader.readLine();

        String idCita = generarIdCita(quoteMap);
        Quote cita = new Quote(idCita, fechaCita, horaCita, "Pendiente", clienteCita, mascotaCita, servicioCita);
        quoteMap.put(idCita, cita);

        System.out.println("-> Cita " + idCita + " agendada con éxito para " + mascotaCita.getName() + ".");

        //Menu rapido post-agendamiento
        boolean seguir = true;
        while (seguir) {
            System.out.println("\n¿Qué desea hacer ahora?");
            System.out.println("1. Agendar otra cita");
            System.out.println("2. Finalizar una cita");
            System.out.println("3. Volver al Menu de Citas");
            System.out.println("Seleccione una opcion: ");
            String siguienteOpcion = reader.readLine();

            switch (siguienteOpcion) {
                case "1":
                    agendarCita(reader, clientMap, quoteMap, serviceCatalog);
                    seguir = false;
                    break;

                case "2":
                    finalizarCita(reader, quoteMap);
                    seguir = false;
                    break;

                case "3":
                    seguir = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    //Finalizar una cita pendiente
    private static void finalizarCita(BufferedReader reader, Map<String, Quote> quoteMap) throws IOException {
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

        System.out.println("\nIngrese el ID de la cita a finalizar: ");
        String idCita = reader.readLine();
        Quote cita = quoteMap.get(idCita);

        if (cita == null || !cita.getStatus().equals("Pendiente")) {
            System.out.println("-> Error: No existe una cita pendiente con ese ID.");
            return;
        }

        if (esServicioConResultado(cita.getService())) {
            System.out.println("Ingrese el resultado de la cita (diagnóstico/observaciones): ");
            String resultado = reader.readLine();
            cita.setResultado(resultado);
        }

        cita.setStatus("Finalizada");
        System.out.println("-> Cita " + cita.getIdQuote() + " finalizada con éxito.");
    }

    //Mostrar historial de citas finalizadas
    private static void historialCitas(Map<String, Quote> quoteMap) {
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

    //Mostrar el calendario de citas pendientes ordenadas por fecha y hora
    private static void calendarioCitas(Map<String, Quote> quoteMap) {
        System.out.println("\n--- Calendario de Citas Pendientes ---");
        List<Quote> pendientes = obtenerCitasPendientes(quoteMap);

        if (pendientes.isEmpty()) {
            System.out.println("No hay citas pendientes.");
            return;
        }

        ordenarPorFechaYHora(pendientes);

        for (Quote cita : pendientes) {
            System.out.println(cita.getDate() + " " + cita.getHour() + " - " + cita.getPet().getName()
                    + " (" + cita.getClient().getName() + ") - " + cita.getService().getType());
        }
    }

    //===================== METODOS DE APOYO =====================

    //Pedir un monto validando que sea un numero valido y no negativo
    private static double leerMonto(BufferedReader reader, String mensaje) throws IOException {
        double monto = -1;
        boolean valido = false;

        while (!valido) {
            System.out.println(mensaje);
            String texto = reader.readLine();
            try {
                monto = Double.parseDouble(texto);
                if (monto < 0) {
                    System.out.println("-> El monto no puede ser negativo.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("-> Debe ingresar un número válido.");
            }
        }

        return monto;
    }

    //Pedir el descuento validando que no sea negativo ni mayor al monto del servicio
    private static double leerDescuento(BufferedReader reader, double monto) throws IOException {
        double descuento = -1;
        boolean valido = false;

        while (!valido) {
            System.out.println("Ingrese el monto del descuento: ");
            String texto = reader.readLine();
            try {
                descuento = Double.parseDouble(texto);
                if (descuento < 0) {
                    System.out.println("-> El descuento no puede ser negativo.");
                } else if (descuento > monto) {
                    System.out.println("-> El descuento no puede ser mayor que el monto del servicio.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("-> Debe ingresar un número válido.");
            }
        }

        return descuento;
    }

    //Generar un ID de cita que no choque con uno ya existente
    private static String generarIdCita(Map<String, Quote> quoteMap) {
        int numero = quoteMap.size() + 1;
        String idCita = String.valueOf(numero);
        while (quoteMap.containsKey(idCita)) {
            numero++;
            idCita = String.valueOf(numero);
        }
        return idCita;
    }

    //Obtener las citas con estado Pendiente
    private static List<Quote> obtenerCitasPendientes(Map<String, Quote> quoteMap) {
        List<Quote> pendientes = new ArrayList<>();
        for (Quote cita : quoteMap.values()) {
            if (cita.getStatus().equals("Pendiente")) {
                pendientes.add(cita);
            }
        }
        return pendientes;
    }

    //Ordenar la lista de citas por fecha y hora (ordenamiento burbuja)
    private static void ordenarPorFechaYHora(List<Quote> pendientes) {
        int n = pendientes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Quote actual = pendientes.get(j);
                Quote siguiente = pendientes.get(j + 1);

                int fechaActual = valorFecha(actual.getDate());
                int fechaSiguiente = valorFecha(siguiente.getDate());

                boolean debeCambiar;
                if (fechaActual == fechaSiguiente) {
                    debeCambiar = actual.getHour().compareTo(siguiente.getHour()) > 0;
                } else {
                    debeCambiar = fechaActual > fechaSiguiente;
                }

                if (debeCambiar) {
                    pendientes.set(j, siguiente);
                    pendientes.set(j + 1, actual);
                }
            }
        }
    }

    //Verificar si el servicio requiere ingresar un resultado
    private static boolean esServicioConResultado(Service service) {
        String tipo = service.getType().toLowerCase();
        return tipo.contains("medic") || tipo.contains("médic") || tipo.contains("urgencia") || tipo.contains("vacuna");
    }

    //Imprimir los datos de una cita
    private static void imprimirCita(Quote cita) {
        System.out.println("\nCita: " + cita.getIdQuote() + " | Estado: " + cita.getStatus());
        System.out.println("Fecha: " + cita.getDate() + " | Hora: " + cita.getHour());
        System.out.println("Cliente: " + cita.getClient().getName() + " | RUT: " + cita.getClient().getRut());
        System.out.println("Mascota: " + cita.getPet().getName() + " | ID: " + cita.getPet().getId());
        System.out.println("Servicio: " + cita.getService().getType() + " | Precio: $" + cita.getService().getCash());
    }

    //Pedir una fecha con formato valido
    private static String pedirFecha(BufferedReader reader, String mensaje) throws IOException {
        String fecha = null;
        while (fecha == null) {
            System.out.println(mensaje);
            String texto = reader.readLine();
            fecha = formatearFecha(texto);
            if (fecha == null) {
                System.out.println("-> Formato inválido. Use dd-mm-aaaa.");
            }
        }
        return fecha;
    }

    //Validar y normalizar una fecha dd-mm-aaaa
    private static String formatearFecha(String texto) {
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

    //Convertir una fecha dd-mm-aaaa en un numero comparable, ej: 08-09-2026 -> 20260908
    private static int valorFecha(String fecha) {
        String[] partes = fecha.split("-");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);
        return anio * 10000 + mes * 100 + dia;
    }
}
