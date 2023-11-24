package demo;

//Classe représentant une personne avec ses attributs (ID, nom, âge)
public class Personne {
    private int id;
    private String nom;
    private int age;

    // Méthodes getters et setters pour accéder et modifier les attributs

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}