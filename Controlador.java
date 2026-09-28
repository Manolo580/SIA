import java.util.List;
import java.util.Map;

public class Controlador {

    private Map<String, Client> clientMap;
    private Map<String, Pet> petMap;
    private Map<String, Quote> quoteMap;
    private Map<String, Service> serviceCatalog;

    //Constructor
    public Controlador(Map<String, Client> clientMap, Map<String, Pet> petMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog) {
        this.clientMap = clientMap;
        this.petMap = petMap;
        this.quoteMap = quoteMap;
        this.serviceCatalog = serviceCatalog;
    }

    //Registrar cliente
    public String registrarCliente(String rut, String name, String phone) {
        if (rut.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            return "Error: Ningún campo puede estar vacío.";
        }

        if (!rut.matches("[0-9]+")) {
            return "Error: El RUT solo puede contener números, sin guion.";
        }

        if (clientMap.containsKey(rut)) {
            return "Error: El RUT ya está registrado.";
        }

        Client client = new Client(rut, name, phone);
        clientMap.put(rut, client);
        return "Cliente registrado con éxito.";
    }

    //Mostrar clientes
    public String mostrarClientes() {
        if (clientMap.isEmpty()) {
            return "No hay clientes registrados.";
        }

        StringBuilder texto = new StringBuilder();
        for (Client client : clientMap.values()) {
            texto.append("Cliente: ").append(client.getName())
                    .append(" | RUT: ").append(client.getRut())
                    .append(" | Tel: ").append(client.getPhone())
                    .append(" | Mascotas: ").append(client.getPets().size())
                    .append("\n");
        }
        return texto.toString();
    }

    //Buscar cliente
    public String buscarCliente(String rut) {
        try {
            Client client = ClienteMenu.obtenerClientePorRut(clientMap, rut);
            return "Nombre: " + client.getName() + " | RUT: " + client.getRut()
                    + " | Tel: " + client.getPhone() + " | Mascotas: " + client.getPets().size();
        } catch (ClienteNoEncontradoException e) {
            return "Error: " + e.getMessage();
        }
    }

    //Editar cliente
    public String editarCliente(String rut, String name, String phone) {
        try {
            Client client = ClienteMenu.obtenerClientePorRut(clientMap, rut);

            if (phone.isEmpty()) {
                return "Error: El telefono no puede estar vacío.";
            }

            if (name.isEmpty()) {
                client.actualizarDatos(phone); //Sobrecarga
            } else {
                client.actualizarDatos(name, phone); //Sobrecarga
            }

            return "Cliente actualizado con éxito.";
        } catch (ClienteNoEncontradoException e) {
            return "Error: " + e.getMessage();
        }
    }

    //Eliminar cliente
    public String eliminarCliente(String rut) {
        try {
            Client client = ClienteMenu.obtenerClientePorRut(clientMap, rut);

            for (Pet pet : client.getPets()) {
                petMap.remove(pet.getId());
            }

            clientMap.remove(rut);
            return "Cliente '" + client.getName() + "' y sus mascotas fueron eliminados.";
        } catch (ClienteNoEncontradoException e) {
            return "Error: " + e.getMessage();
        }
    }

    //Registrar mascota
    public String registrarMascota(String rutDueno, String id, String name, String edadTexto, String especie, String raza) {
        try {
            Client client = ClienteMenu.obtenerClientePorRut(clientMap, rutDueno);

            if (id.isEmpty() || name.isEmpty()) {
                return "Error: El ID y el nombre no pueden estar vacíos.";
            }

            if (petMap.containsKey(id)) {
                return "Error: Ya existe una mascota con ese ID.";
            }

            int age;
            try {
                age = Integer.parseInt(edadTexto);
                if (age < 0) {
                    return "Error: La edad no puede ser negativa.";
                }
            } catch (NumberFormatException e) {
                return "Error: La edad debe ser un número.";
            }

            Pet nuevaMascota;
            if (especie.equalsIgnoreCase("Perro")) {
                nuevaMascota = new Dog(id, name, age, "Perro", raza.isEmpty() ? "Sin raza" : raza, "Sin evaluar");
            } else if (especie.equalsIgnoreCase("Gato")) {
                nuevaMascota = new Cat(id, name, age, "Gato", raza.isEmpty() ? "Sin raza" : raza, "Sin evaluar");
            } else {
                nuevaMascota = new Pet(id, name, age, especie);
            }

            client.addPet(nuevaMascota);
            petMap.put(id, nuevaMascota);
            return "Mascota '" + name + "' asignada exitosamente a " + client.getName() + ".";
        } catch (ClienteNoEncontradoException e) {
            return "Error: " + e.getMessage();
        }
    }

    //Mostrar mascotas
    public String mostrarMascotas() {
        if (petMap.isEmpty()) {
            return "No hay mascotas registradas.";
        }

        StringBuilder texto = new StringBuilder();
        for (Pet pet : petMap.values()) {
            texto.append("Mascota: ").append(pet.getName())
                    .append(" | ID: ").append(pet.getId())
                    .append(" | Especie: ").append(pet.getSpecies())
                    .append(" | Edad: ").append(pet.getAge())
                    .append(pet.getDetalleEspecie())
                    .append("\n");
        }
        return texto.toString();
    }

    //Buscar mascota
    public String buscarMascota(String id) {
        try {
            Pet pet = MascotaMenu.obtenerMascotaPorId(petMap, id);
            return "Nombre: " + pet.getName() + " | ID: " + pet.getId() + " | Especie: " + pet.getSpecies()
                    + " | Edad: " + pet.getAge() + pet.getDetalleEspecie();
        } catch (MascotaNoEncontradaException e) {
            return "Error: " + e.getMessage();
        }
    }

    //Editar mascota
    public String editarMascota(String id, String name, String edadTexto) {
        try {
            Pet pet = MascotaMenu.obtenerMascotaPorId(petMap, id);

            if (name.isEmpty()) {
                return "Error: El nombre no puede estar vacío.";
            }

            if (edadTexto.isEmpty()) {
                pet.actualizarDatos(name); //Sobrecarga
            } else {
                int edad = Integer.parseInt(edadTexto);
                pet.actualizarDatos(name, edad); //Sobrecarga
            }

            return "Mascota actualizada con éxito.";
        } catch (MascotaNoEncontradaException e) {
            return "Error: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Error: La edad debe ser un número.";
        }
    }

    //Eliminar mascota
    public String eliminarMascota(String id) {
        try {
            Pet pet = MascotaMenu.obtenerMascotaPorId(petMap, id);

            for (Client client : clientMap.values()) {
                if (client.getPetById(id) != null) {
                    client.removePet(id);
                    break;
                }
            }

            petMap.remove(id);
            return "Mascota '" + pet.getName() + "' eliminada con éxito.";
        } catch (MascotaNoEncontradaException e) {
            return "Error: " + e.getMessage();
        }
    }

    //Agregar servicio
    public String agregarServicio(String idServicio, String tipo) {
        if (idServicio.isEmpty() || tipo.isEmpty()) {
            return "Error: El ID y el tipo no pueden estar vacíos.";
        }

        if (serviceCatalog.containsKey(idServicio)) {
            return "Error: Ya existe un servicio con ese ID.";
        }

        Service servicio = new Service(tipo, idServicio, 0);
        serviceCatalog.put(idServicio, servicio);
        return "Servicio '" + tipo + "' agregado con éxito.";
    }

    //Mostrar servicios
    public String mostrarServicios() {
        if (serviceCatalog.isEmpty()) {
            return "No hay servicios registrados.";
        }

        StringBuilder texto = new StringBuilder();
        for (Service servicio : serviceCatalog.values()) {
            texto.append("ID: ").append(servicio.getIdService())
                    .append(" | Tipo: ").append(servicio.getType())
                    .append("\n");
        }
        return texto.toString();
    }

    //Agendar cita
    public String agendarCita(String rut, String idMascota, String idServicio, String montoTexto, String descuentoTexto, String fecha, String hora) {
        Client client;
        Pet pet;

        try {
            client = ClienteMenu.obtenerClientePorRut(clientMap, rut);
            pet = CitaMenu.obtenerMascotaDelCliente(client, idMascota);
        } catch (ClienteNoEncontradoException | MascotaNoEncontradaException e) {
            return "Error: " + e.getMessage();
        }

        Service servicioBase = serviceCatalog.get(idServicio);
        if (servicioBase == null) {
            return "Error: Ese servicio no existe.";
        }

        if (hora.isEmpty()) {
            return "Error: Debe ingresar una hora.";
        }

        String fechaFormateada = CitaMenu.formatearFecha(fecha);
        if (fechaFormateada == null) {
            return "Error: Formato de fecha inválido. Use dd-mm-aaaa.";
        }

        double monto;
        try {
            monto = Double.parseDouble(montoTexto);
            if (monto < 0) {
                return "Error: El monto no puede ser negativo.";
            }
        } catch (NumberFormatException e) {
            return "Error: El monto debe ser un número.";
        }

        Service servicioCita = new Service(servicioBase.getType(), servicioBase.getIdService(), 0);

        if (descuentoTexto.isEmpty()) {
            servicioCita.setPrice(monto); //Sobrecarga
        } else {
            double descuento;
            try {
                descuento = Double.parseDouble(descuentoTexto);
            } catch (NumberFormatException e) {
                return "Error: El descuento debe ser un número.";
            }
            if (descuento < 0 || descuento > monto) {
                return "Error: El descuento no puede ser negativo ni mayor al monto.";
            }
            servicioCita.setPrice(monto, descuento); //Sobrecarga
        }

        String idCita = CitaMenu.generarIdCita(quoteMap);
        Quote cita = new Quote(idCita, fechaFormateada, hora, "Pendiente", client, pet, servicioCita);
        quoteMap.put(idCita, cita);

        return "Cita " + idCita + " agendada con éxito para " + pet.getName() + ".";
    }

    //Calendario de citas
    public String calendarioCitas() {
        List<Quote> pendientes = CitaMenu.obtenerCitasPendientes(quoteMap);
        if (pendientes.isEmpty()) {
            return "No hay citas pendientes.";
        }

        CitaMenu.ordenarPorFechaYHora(pendientes);

        StringBuilder texto = new StringBuilder();
        for (Quote cita : pendientes) {
            texto.append(cita.getDate()).append(" ").append(cita.getHour())
                    .append(" - ").append(cita.getPet().getName())
                    .append(" (").append(cita.getClient().getName()).append(") - ")
                    .append(cita.getService().getType())
                    .append("\n");
        }
        return texto.toString();
    }

    //Historial de citas
    public String historialCitas() {
        StringBuilder texto = new StringBuilder();
        boolean hayFinalizadas = false;

        for (Quote cita : quoteMap.values()) {
            if (cita.getStatus().equals("Finalizada")) {
                hayFinalizadas = true;
                texto.append("Cita: ").append(cita.getIdQuote())
                        .append(" | ").append(cita.getDate()).append(" ").append(cita.getHour())
                        .append(" | Cliente: ").append(cita.getClient().getName())
                        .append(" | Mascota: ").append(cita.getPet().getName())
                        .append(" | Servicio: ").append(cita.getService().getType());
                if (cita.getResultado() != null) {
                    texto.append(" | Resultado: ").append(cita.getResultado());
                }
                texto.append("\n");
            }
        }

        return hayFinalizadas ? texto.toString() : "Aún no hay citas finalizadas.";
    }

    //Finalizar cita
    public String finalizarCita(String idCita, String resultado, String nuevoEstadoSalud) {
        Quote cita = quoteMap.get(idCita);

        if (cita == null || !cita.getStatus().equals("Pendiente")) {
            return "Error: No existe una cita pendiente con ese ID.";
        }

        if (CitaMenu.esServicioConResultado(cita.getService())) {
            if (!resultado.isEmpty()) {
                cita.setResultado(resultado);
            }
            if (!nuevoEstadoSalud.isEmpty()) {
                cita.getPet().actualizarSalud(nuevoEstadoSalud); //Polimorfismo
            }
        }

        cita.setStatus("Finalizada");
        return "Cita " + cita.getIdQuote() + " finalizada con éxito.";
    }
}
