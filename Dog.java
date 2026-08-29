public class Dog extends Pet {
    private String raza;
    private String salud;

    public Dog(String i, String n, int a, String r, String s){
        super(i,n,a);
        raza = r;
        salud = s;
    }

    //GET
    public String getRaza(){
        return raza;
    }

    public String getSalud(){
        return salud;
    }

    //SET
    public void setRaza(String r){
        raza = r;
    }

    public void setSalud(String s){
        salud = s;
    }
}