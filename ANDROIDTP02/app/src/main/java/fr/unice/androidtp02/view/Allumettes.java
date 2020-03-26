package fr.unice.androidtp02.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import fr.unice.androidtp02.R;

public class Allumettes extends View {
    Drawable allumette;

    // normalement valeur à mettre dans dimens.xml
    int padding = 30;

    int nombreTotalAllumettes = 21;
    int nombreAllumettesVisibles = 21;

    static final float MAXRATIO = 0.15f; // 30 sur 200
    static final float MINRATIO = 0.05f; // 10 sur 200
    static final int MAX_NB_ALLUMETTES = 50;

    float largeurAllumette = 30f;
    float hauteurAllumette = 200f;

    Paint pTiret;
    Paint pPlein;
    int nbSelectionnées = 0;

    boolean etatInitial = true;


    public Allumettes(Context context) {
        super(context, null);
    }

    /**
     * Constructeur pour initialiser le drawable et les "paints" pour dessiner
     * @param context le contexte (l'app), fourni par l'environnment Android
     * @param attrs les attributs, fournis par l'environnment Android
     */
    public Allumettes(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            allumette = context.getDrawable(R.drawable.allumettes);
        } else {
            allumette = context.getResources().getDrawable(R.drawable.allumettes);
        }


        pPlein = new Paint();

        // p.setColor(Color.argb(100,0,255,0));
        pPlein.setColor(Color.rgb(0, 128, 0));
        pPlein.setAntiAlias(true);
        pPlein.setStrokeWidth(padding / 2);
        pPlein.setStyle(Paint.Style.STROKE);

        // normalement valeur à mettre dans dimens.xml
        pPlein.setTextSize(18);



        DashPathEffect effet = new DashPathEffect(new float[]{10, 25}, 0);
        pTiret = new Paint(pPlein);
        pTiret.setPathEffect(effet);
        pTiret.setStrokeWidth(2);
        pTiret.setColor(Color.GRAY);

    }


    /**
     * Pour savoir quand la vue a été redimensionnée. On ne change pas son comportement par
     * défaut, on exploite le résultat (à travers getWidth() et getHeight() )
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        calculDimensionAllumette();
    }


    /**
     * getter sur le nombre total (si elles sont toutes présentes) d'allumettes affichées
     * @return le nombre d'allumette total/max
     */
    public int obtenirNombreAllumettes() {
        return nombreTotalAllumettes;
    }

    /**
     * setter sur le nombre total (si elles sont toutes présentes) d'allumettes affichées
     * @param nb le nombre d'allumette total/max
     */
    public void changerNombreAllumettes(int nb) {
        if ((nb >= 0) && (nb <= MAX_NB_ALLUMETTES)) {
            this.nombreTotalAllumettes = nb;
            this.nombreAllumettesVisibles = nombreTotalAllumettes;
            etatInitial = false;
            calculDimensionAllumette();
        }
    }


    /**
     * pour enlever nb allumettes. Si on en enlève trop, on enlève tout.
     * @param nb nombre d'allumettes à enlever
     * @return vrai si on a pu enlever au moins une allumette
     */
    public boolean retirerAllumettes(int nb) {
        boolean result = false;

        if ((nb >= 0) && (nombreAllumettesVisibles > 0)) {
            nombreAllumettesVisibles = nombreAllumettesVisibles - nb;
            if (nombreAllumettesVisibles < 0) nombreAllumettesVisibles = 0;
        }

        return result;

    }


    /**
     * getter sur le nombre d'allumettes encore "présentes"
     * @return le nombre d'allumettes encore "présentes"
     */
    public int obtenirNbAllumettesDisponibles() {
        return this.nombreAllumettesVisibles;
    }


    /**
     * setter sur le nombre d'allumettes encore "présentes"
     * @param nb le nombre d'allumettes encore "présentes"
     */
    public void changerNbAllumettesDisponibles(int nb) {
        if ((nb >= 0) && (nb < obtenirNombreAllumettes())) {
            this.nombreAllumettesVisibles = nb;
        }
    }


    /**
     * getter sur l'état initial (pour savoir si on affiche du texte ou si on affiche des allumettes)
     * @return l'état initial d'affichage
     */
    public boolean obtenirEtatInitial() { return etatInitial ; }

    /**
     * setter sur l'état initial (pour savoir si on affiche du texte ou si on affiche des allumettes)
     * @param etat l'état initial d'affichage
     */
    public void changerEtatInitial(boolean etat) { etatInitial = etat ; }


    /**
     * pour calculer la dimension d'une allumette. Appelée dans onMeasure
     */
    protected void calculDimensionAllumette() {

        int nbEmplacementParLigne = nombreTotalAllumettes;  // 1 sur deux, sur deux lignes
        float nbLignes = 2f;

        largeurAllumette = ((float) (getWidth() - padding * 2)) / ((float) nbEmplacementParLigne);
        hauteurAllumette = ((float) (getHeight() - padding * 3)) / nbLignes;

        if ((largeurAllumette > 0) && (hauteurAllumette > 0)) {
            float ratio = largeurAllumette / hauteurAllumette;

            if (ratio > MAXRATIO) largeurAllumette = hauteurAllumette * MAXRATIO;
            else if (ratio < MINRATIO) hauteurAllumette = largeurAllumette / MINRATIO;
        }

    }

    /**
     * pour sélectionner (avec retour visuel) nb Allumettes
     * @param nb d'allumettes sélectionnées
     */
    public void sélectionnerAllumettes(int nb) {
        if (nb >= 0) this.nbSelectionnées = Math.min(nb, nombreAllumettesVisibles);
    }


    /**
     * Dessin de la View
     * @param canvas objet fourni par l'environnement Android
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (etatInitial) {
            pPlein.setStrokeWidth(2);
            String ligne1 = "Chacun à son tour, prenez 1, 2 ou 3 allumettes";
            canvas.drawText(ligne1, padding, padding, pPlein);
            Rect dim = new Rect();
            pPlein.getTextBounds(ligne1, 0, ligne1.length(),dim);
            canvas.drawText("Celui qui prend la dernière allumette perd", padding, padding+dim.height(), pPlein);
            pPlein.setStrokeWidth(padding / 2);
        }
        else if ((largeurAllumette > 0) && (hauteurAllumette > 0)) {

            float x = padding;
            float y = padding;
            float epaisseur = pPlein.getStrokeWidth() / 2;

            int indice_selectionne = nombreAllumettesVisibles - nbSelectionnées;

            // 1re ligne
            int nb1reLigne = nombreTotalAllumettes / 2 + (nombreTotalAllumettes % 2);

            for (int ligne = 0; ligne < 2; ligne++) {
                x = padding;
                y = y + (hauteurAllumette + padding) * ligne;

                int debut = nb1reLigne * ligne;
                int fin = nb1reLigne + (nombreTotalAllumettes - nb1reLigne) * ligne;

                int i ;
                for ( i = debut; (i < fin) && (i < nombreAllumettesVisibles); i++) {
                    int lx = (int) x;
                    int ly = (int) y;
                    int dx = (int) (x + largeurAllumette);
                    int dy = (int) (y + hauteurAllumette);


                    if (i >= indice_selectionne) {
                        canvas.drawRect(lx - epaisseur, ly - epaisseur, dx + epaisseur, dy + epaisseur, pPlein);
                    }

                    allumette.setBounds(lx, ly, dx, dy);
                    allumette.draw(canvas);

                    x = x + 2 * largeurAllumette;
                }

                // on continue là où on s'était arrêté
                // pour dessiner des traces des allumettes enlevées.
                // non obligatoire...
                for (int j = i ; j < fin; j++) {
                    int lx = (int) x;
                    int ly = (int) y;
                    int dx = (int) (x + largeurAllumette);
                    int dy = (int) (y + hauteurAllumette);

                    canvas.drawRect(lx , ly , dx , dy , pTiret);

                    x = x + 2 * largeurAllumette;

                }

            }


        }

        canvas.drawRect(0, 0, getWidth() - 1, getHeight() - 1, pPlein);


    }

}