package fr.unice.androidtp02.controle;




        import android.os.AsyncTask;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import fr.unice.androidtp02.moteur.JeuxDesAllumettes;
        import fr.unice.androidtp02.view.Allumettes;

public class Controleur extends AsyncTask<Object,String,Object> {
    private JeuxDesAllumettes jeu;
    private Allumettes viewAllumettes;
    private TextView t;
    private ScrollView s;

    public Controleur(JeuxDesAllumettes jeu, Allumettes viewAllumettes, TextView t, ScrollView s) {
        this.jeu = jeu;
        this.viewAllumettes = viewAllumettes;
        this.t = t;
        this.s = s;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        return null;
    }
}