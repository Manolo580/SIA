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

    //Sobrecarga
    public void actualizarDatos(String nombre){
        this.name = nombre;
    }

    //Sobrecarga
    public void actualizarDatos(String nombre, int edad){
        this.name = nombre;
        this.age = edad;
    }

    //Detalle de especie
    public String getDetalleEspecie() {
        return "";
    }

    //Actualizar salud
    public void actualizarSalud(String estado) {
    }
}