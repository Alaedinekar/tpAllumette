package fr.unice.androidtp02.moteur;


public class Joueur {
    private String nom;

    public void changerNom(String n) {
        nom = n;
    }

    public String obtenirNom() {
        return nom;
    }

    public String toString() {
        return "nom joueur :" + nom + ".";
    }

    int jouer(int nbRestantes) {
        return 1;
    }


}
