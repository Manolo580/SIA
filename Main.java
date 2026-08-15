package proyecto;

import java.util.ArrayList;
import java.util.List;

class Client{
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

    //Sobrecarga (Client)

    public void addPet(Pet newPet){
        this.pets.add(newPet);
    }

    public void addPet(String id, String name, String species, String race){
        Pet p = new Pet(id, name, species, race);
        this.pet.add(p);
    }
}

class Pet{
    private String idPet;
    private String name;
    private String species;
    private String race;

    public Pet(String idPet, String name, String species, String race){
        this.idPet = idPet;
        this.name = name;
        this.species = species;
        this.race = race;
    }
}

class Service{
    private String type;
    private String idService;
    private double cash;

    public Service(String type, String idService, double cash){
        this.type = type;
        this.idService = idService;
        this.cash = cash;
    }

    //Sobrecarga en Service

    public void setPrice(double amount){
        this.cash = amount;
    }

    public void setPrice(double amount, double discount){
        this.cash = amount - discount;
    }
}


class Quote{
    private String idQuote;
    private String date;
    private String status;
    private Client client;
    private Pet pet;
    private Service service;

    public Quote(String date, String service, String status, Client client, Pets pets){
        this.idQuote = idQuote;
        this.date = date;
        this.service = service;
        this.status = status;
        this.client = client;
        this.pets = pets;
    }
}

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Client> clientList = new ArrayList<>();
        List<Quote> quoteList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();
    }

}
