import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Persistencia {

    private static final String ARCHIVO_DATOS = "datos_sia.dat";

    //Guardar los datos en el archivo
    public static void guardarDatos(Datos datos) {
        ObjectOutputStream salida = null;
        try {
            salida = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS));
            salida.writeObject(datos);
        } catch (IOException e) {
            System.out.println("-> Error al guardar los datos: " + e.getMessage());
        } finally {
            //Cerrar el archivo de salida
            try {
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println("-> Error al cerrar el archivo: " + e.getMessage());
            }
        }
    }

    //Cargar los datos guardados en el archivo
    public static Datos cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);

        //Si se ejecuta por primera vez
        if (!archivo.exists()) {
            return new Datos(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        ObjectInputStream entrada = null;
        try {
            entrada = new ObjectInputStream(new FileInputStream(archivo));
            return (Datos) entrada.readObject();
        } catch (IOException e) {
            System.out.println("-> Error al cargar los datos guardados, se inicia con un sistema vacío: " + e.getMessage());
            return new Datos(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        } catch (ClassNotFoundException e) {
            System.out.println("-> Error al cargar los datos guardados, se inicia con un sistema vacío: " + e.getMessage());
            return new Datos(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        } finally {
            //Cerrar el archivo de entrada
            try {
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println("-> Error al cerrar el archivo: " + e.getMessage());
            }
        }
    }
}
