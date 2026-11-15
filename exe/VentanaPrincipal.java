import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VentanaPrincipal extends JFrame {

    private Controlador controlador;
    private Map<String, Client> clientMap;
    private Map<String, Pet> petMap;
    private Map<String, Quote> quoteMap;
    private Map<String, Service> serviceCatalog;

    //Constructor
    public VentanaPrincipal(Map<String, Client> clientMap, Map<String, Pet> petMap, Map<String, Quote> quoteMap, Map<String, Service> serviceCatalog) {
        this.clientMap = clientMap;
        this.petMap = petMap;
        this.quoteMap = quoteMap;
        this.serviceCatalog = serviceCatalog;
        this.controlador = new Controlador(clientMap, petMap, quoteMap, serviceCatalog);

        setTitle("Sistema Veterinario");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Servicios", crearPanelServicios());
        pestañas.addTab("Clientes", crearPanelClientes());
        pestañas.addTab("Mascotas", crearPanelMascotas());
        pestañas.addTab("Citas", crearPanelCitas());

        add(pestañas, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    //Panel con el boton para guardar los datos
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton botonGuardar = new JButton("Guardar Datos");

        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            Persistencia.guardarDatos(new Datos(clientMap, petMap, quoteMap, serviceCatalog));
            }
        });

        panel.add(botonGuardar);
        return panel;
    }

    //Panel de servicios
    private JPanel crearPanelServicios() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField campoId = new JTextField();
        JTextField campoTipo = new JTextField();
        form.add(new JLabel("ID Servicio:"));
        form.add(campoId);
        form.add(new JLabel("Tipo:"));
        form.add(campoTipo);

        JTextArea salida = new JTextArea();
        salida.setEditable(false);

        JPanel botones = new JPanel(new FlowLayout());
        JButton botonAgregar = new JButton("Agregar");
        JButton botonMostrar = new JButton("Mostrar");

        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.agregarServicio(campoId.getText().trim(), campoTipo.getText().trim()));
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.mostrarServicios());
            }
        });

        botones.add(botonAgregar);
        botones.add(botonMostrar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(botones, BorderLayout.SOUTH);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(salida), BorderLayout.CENTER);
        return panel;
    }

    //Panel de clientes
    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField campoRut = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoTelefono = new JTextField();
        form.add(new JLabel("RUT (sin guion):"));
        form.add(campoRut);
        form.add(new JLabel("Nombre:"));
        form.add(campoNombre);
        form.add(new JLabel("Telefono:"));
        form.add(campoTelefono);

        JTextArea salida = new JTextArea();
        salida.setEditable(false);

        JPanel botones = new JPanel(new FlowLayout());
        JButton botonRegistrar = new JButton("Registrar");
        JButton botonMostrar = new JButton("Mostrar");
        JButton botonBuscar = new JButton("Buscar");
        JButton botonEditar = new JButton("Editar");
        JButton botonEliminar = new JButton("Eliminar");

        botonRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.registrarCliente(campoRut.getText().trim(), campoNombre.getText().trim(), campoTelefono.getText().trim()));
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.mostrarClientes());
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.buscarCliente(campoRut.getText().trim()));
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.editarCliente(campoRut.getText().trim(), campoNombre.getText().trim(), campoTelefono.getText().trim()));
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.eliminarCliente(campoRut.getText().trim()));
            }
        });

        botones.add(botonRegistrar);
        botones.add(botonMostrar);
        botones.add(botonBuscar);
        botones.add(botonEditar);
        botones.add(botonEliminar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(botones, BorderLayout.SOUTH);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(salida), BorderLayout.CENTER);
        return panel;
    }

    //Panel de mascotas
    private JPanel crearPanelMascotas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField campoRutDueno = new JTextField();
        JTextField campoId = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoEdad = new JTextField();
        JTextField campoEspecie = new JTextField();
        JTextField campoRaza = new JTextField();
        form.add(new JLabel("RUT Dueño:"));
        form.add(campoRutDueno);
        form.add(new JLabel("ID Mascota:"));
        form.add(campoId);
        form.add(new JLabel("Nombre:"));
        form.add(campoNombre);
        form.add(new JLabel("Edad:"));
        form.add(campoEdad);
        form.add(new JLabel("Especie (Perro/Gato/Otro):"));
        form.add(campoEspecie);
        form.add(new JLabel("Raza (opcional):"));
        form.add(campoRaza);

        JTextArea salida = new JTextArea();
        salida.setEditable(false);

        JPanel botones = new JPanel(new FlowLayout());
        JButton botonRegistrar = new JButton("Registrar");
        JButton botonMostrar = new JButton("Mostrar");
        JButton botonBuscar = new JButton("Buscar");
        JButton botonEditar = new JButton("Editar");
        JButton botonEliminar = new JButton("Eliminar");

        botonRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.registrarMascota(campoRutDueno.getText().trim(), campoId.getText().trim(),
                    campoNombre.getText().trim(), campoEdad.getText().trim(), campoEspecie.getText().trim(), campoRaza.getText().trim()));
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.mostrarMascotas());
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.buscarMascota(campoId.getText().trim()));
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.editarMascota(campoId.getText().trim(), campoNombre.getText().trim(), campoEdad.getText().trim()));
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.eliminarMascota(campoId.getText().trim()));
            }
        });

        botones.add(botonRegistrar);
        botones.add(botonMostrar);
        botones.add(botonBuscar);
        botones.add(botonEditar);
        botones.add(botonEliminar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(botones, BorderLayout.SOUTH);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(salida), BorderLayout.CENTER);
        return panel;
    }

    //Panel de citas
    private JPanel crearPanelCitas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(10, 2, 5, 5));
        JTextField campoRut = new JTextField();
        JTextField campoIdMascota = new JTextField();
        JTextField campoIdServicio = new JTextField();
        JTextField campoMonto = new JTextField();
        JTextField campoDescuento = new JTextField();
        JTextField campoFecha = new JTextField();
        JTextField campoHora = new JTextField();
        JTextField campoIdCita = new JTextField();
        JTextField campoResultado = new JTextField();
        JTextField campoEstadoSalud = new JTextField();
        form.add(new JLabel("RUT Cliente:"));
        form.add(campoRut);
        form.add(new JLabel("ID Mascota:"));
        form.add(campoIdMascota);
        form.add(new JLabel("ID Servicio:"));
        form.add(campoIdServicio);
        form.add(new JLabel("Monto:"));
        form.add(campoMonto);
        form.add(new JLabel("Descuento (opcional):"));
        form.add(campoDescuento);
        form.add(new JLabel("Fecha (dd-mm-aaaa):"));
        form.add(campoFecha);
        form.add(new JLabel("Hora (hh:mm):"));
        form.add(campoHora);
        form.add(new JLabel("ID Cita a finalizar:"));
        form.add(campoIdCita);
        form.add(new JLabel("Resultado (opcional):"));
        form.add(campoResultado);
        form.add(new JLabel("Nuevo estado de salud (opcional):"));
        form.add(campoEstadoSalud);

        JTextArea salida = new JTextArea();
        salida.setEditable(false);

        JPanel botones = new JPanel(new FlowLayout());
        JButton botonAgendar = new JButton("Agendar");
        JButton botonCalendario = new JButton("Calendario");
        JButton botonHistorial = new JButton("Historial");
        JButton botonFinalizar = new JButton("Finalizar Cita");

        botonAgendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.agendarCita(campoRut.getText().trim(), campoIdMascota.getText().trim(),
                    campoIdServicio.getText().trim(), campoMonto.getText().trim(), campoDescuento.getText().trim(),
                    campoFecha.getText().trim(), campoHora.getText().trim()));
            }
        });

        botonCalendario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.calendarioCitas());
            }
        });

        botonHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.historialCitas());
            }
        });

        botonFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            salida.setText(controlador.finalizarCita(campoIdCita.getText().trim(), campoResultado.getText().trim(), campoEstadoSalud.getText().trim()));
            }
        });

        botones.add(botonAgendar);
        botones.add(botonCalendario);
        botones.add(botonHistorial);
        botones.add(botonFinalizar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(botones, BorderLayout.SOUTH);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(salida), BorderLayout.CENTER);
        return panel;
    }
}
