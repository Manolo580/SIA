public class Pet{
    protected String id;
    protected String name;
    protected int age;

    public Pet(String i, String n, int a){
        id = i;
        name = n;
        age = a;
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

    //Registrar a la mascota
}