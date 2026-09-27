import java.io.Serializable;

public class Pet implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int age;
    private String species;

    //Constructor
    public Pet(String i, String n, int a, String e){
        id = i;
        name = n;
        age = a;
        species = e;
    }

    //Get
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSpecies() {
        return species;
    }

    //Set
    public void setId(String i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    public void setAge(int a) {
        age = a;
    }

    public void setSpecies(String e) {
        species = e;
    }

    //Sobrecarga: actualizar solo el nombre de la mascota
    public void actualizarDatos(String nombre){
        this.name = nombre;
    }

    //Sobrecarga: actualizar nombre y edad de la mascota (ej: al cumplir años)
    public void actualizarDatos(String nombre, int edad){
        this.name = nombre;
        this.age = edad;
    }

    //Detalle extra de la especie (se sobreescribe en las subclases)
    public String getDetalleEspecie() {
        return "";
    }

    //Actualizar el estado de salud (se sobreescribe en las subclases que lo tienen)
    public void actualizarSalud(String estado) {
        //Por defecto no hace nada: no todas las mascotas tienen este atributo
    }
}