import java.util.ArrayList;
import java.util.List;

public class Client{
    private String rut;
    private String name;
    private String phone;
    private List<Pet> pets;

    public Client(String rut, String name, String phone){
        this.rut = rut;
        this.name = name;
        this.phone = phone;
        this.pets = new ArrayList<>();
    }

    //Los get y setters

    public String getRut() {
        return this.rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getName() {
        return name; 
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() { 
        return phone; 
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Pet> getPets() { 
        return this.pets; 
    }
    public void addPet(Pet pet) { 
        this.pets.add(pet); 
    }

}