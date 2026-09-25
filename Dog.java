public class Dog extends Pet {
    private String raza;
    private String problema_salud;

    //Constructor
    public Dog(String i, String n, int a, String e, String r, String s){
        super(i, n, a, e);
        raza = r;
        problema_salud = s;
    }

    //Get
    public String getRaza(){
        return raza;
    }

    public String getProblemaSalud(){
        return problema_salud;
    }

    //Set
    public void setRaza(String r){
        raza = r;
    }

    public void setProblemaSalud(String s){
        problema_salud = s;
    }

    //Sobreescritura: detalle propio del perro
    public String getDetalleEspecie(){
        return " | Raza: " + raza + " | Problema de salud: " + problema_salud;
    }

    //Sobreescritura: actualizar el problema de salud del perro
    public void actualizarSalud(String estado){
        problema_salud = estado;
    }
}
