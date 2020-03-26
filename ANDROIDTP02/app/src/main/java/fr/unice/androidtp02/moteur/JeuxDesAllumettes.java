package fr.unice.androidtp02.moteur;

import java.util.ArrayList;

public class JeuxDesAllumettes {
    private ArrayList<Joueur> joueurs;
    private final static int NB_TOTAL_ALLUMETTES = 21;
    private int nbAllumettesRestantes;
    private boolean demarre;
    private int joueurActuel;
    private int gagnant;

    public JeuxDesAllumettes() {
        joueurs = new ArrayList<>();
        nbAllumettesRestantes = NB_TOTAL_ALLUMETTES;
        demarre = false;
        joueurActuel = -1;
        gagnant = -1;
    }

    void reinitialiser() {
    }

    boolean ajouterJoueur(Joueur j) {
        if (joueurs.size() < 2) {
            joueurs.add(j);
            return true;
        } else {
            return false;
        }
    }

    boolean partiComplete() {
        return joueurs.size() == 2;
    }

    int obtenirNbTotalAllumettes() {
        return NB_TOTAL_ALLUMETTES;
    }

    private int obtenirNbAllumettesRestantes() {
        return nbAllumettesRestantes;
    }

    boolean demarrer(boolean premierEnPremier) {
        if (nbAllumettesRestantes > 0) {
            if (premierEnPremier) {
                joueurActuel = 0;
            } else {
                joueurActuel = 1;
            }

            demarre = true;
        }

        return demarre;
    }

    private Joueur aQuiDeJouer() {
        if (joueurActuel == 0 || joueurActuel == 1) {
            return joueurs.get(joueurActuel);
        } else {
            return null;
        }
    }

    int jouerUnTour() {
        int nbAllumettesPrises = 0;
        Joueur joueur = aQuiDeJouer();

        if (joueur != null && obtenirGagnant() == null) {
            nbAllumettesPrises = joueur.jouer(obtenirNbAllumettesRestantes());
            nbAllumettesRestantes -= nbAllumettesPrises;
            if (nbAllumettesRestantes > 0) {
                joueurActuel++;
                if (joueurActuel == 2) {
                    joueurActuel = 0;
                }
            } else {
                if (joueurActuel == 0) {
                    gagnant = 1;
                } else {
                    gagnant = 0;
                }
            }
        }

        return nbAllumettesPrises;
    }

    private Joueur obtenirGagnant() {
        if (gagnant != -1) {
            return joueurs.get(gagnant);
        } else {
            return null;
        }
    }
}
