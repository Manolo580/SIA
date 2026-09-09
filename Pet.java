public class Pet{
    protected String id;
    protected String name;
    protected int age;
    protected String species;

    public Pet(String i, String n, int a, String e){
        id = i;
        name = n;
        age = a;
        species = e;
    }

    //GET

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

    //SET
    
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

    //Registrar a la mascota
}