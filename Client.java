import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable{
    private static final long serialVersionUID = 1L;

    private String rut;
    private String name;
    private String phone;
    private List<Pet> pets;

    //Constructor
    public Client(String rut, String name, String phone){
        this.rut = rut;
        this.name = name;
        this.phone = phone;
        this.pets = new ArrayList<>();
    }

    //Get
    public String getRut() {
        return this.rut;
    }

    public String getName() {
        return name; 
    }

    public String getPhone() { 
        return phone; 
    }

    public List<Pet> getPets() { 
        return this.pets; 
    }

    //Set
    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Agregar mascota a la lista del cliente
    public void addPet(Pet pet) { 
        this.pets.add(pet); 
    }

    //Buscar mascota por id
    public Pet getPetById(String id) {
        for (Pet pet : this.pets) {
            if (pet.getId().equals(id)) {
                return pet;
            }
        }
        return null;
    }

}
