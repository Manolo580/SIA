public class Cat extends Pet {
    private String raza;
    private String salud;

    //Constructor
    public Cat(String i, String n, int a, String e, String r, String s){
        super(i, n, a, e);
        raza = r;
        salud = s;
    }

    //Get
    public String getRaza(){
        return raza;
    }

    public String getSalud(){
        return salud;
    }

    //Set
    public void setRaza(String r){
        raza = r;
    }

    public void setSalud(String s){
        salud = s;
    }

    //Sobreescritura: detalle propio del gato
    public String getDetalleEspecie(){
        return " | Raza: " + raza + " | Salud: " + salud;
    }

    //Sobreescritura: actualizar el estado de salud del gato
    public void actualizarSalud(String estado){
        salud = estado;
    }
}
