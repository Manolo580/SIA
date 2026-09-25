import java.io.Serializable;

public class Pet implements Serializable{
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected int age;
    protected String species;

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

    //Detalle extra de la especie (se sobreescribe en las subclases)
    public String getDetalleEspecie() {
        return "";
    }

    //Actualizar el estado de salud (se sobreescribe en las subclases que lo tienen)
    public void actualizarSalud(String estado) {
        //Por defecto no hace nada: no todas las mascotas tienen este atributo
    }

}
