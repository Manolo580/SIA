public class Cat extends Pet {
    private String raza;
    private String salud;

    public Cat(String i, String n, int a, String e, String r, String s){
        super(i, n, a, e);
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