public class Dog extends Pet {
    private String raza;
    private String problema_salud;

    public Dog(String i, String n, int a, String e, String r, String s){
        super(i, n, a, e);
        raza = r;
        problema_salud = s;
    }

    //GET
    public String getRaza(){
        return raza;
    }

    public String getProblemaSalud(){
        return problema_salud;
    }

    //SET
    public void setRaza(String r){
        raza = r;
    }

    public void setProblemaSalud(String s){
        problema_salud = s;
    }
}