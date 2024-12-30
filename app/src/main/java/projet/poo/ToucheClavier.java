package projet.poo;

/**
 * Représente une touche d'un clavier en fonction de sa position 
 * (colonne et rangée) et du doigt utilisé pour la presser.
 * Cette classe implémente l'interface Touche.
 */
public class ToucheClavier implements Touche {
    private final int colonne;
    private final int rangee;
    private final String doigt;


    /**
     * Constructeur pour créer une nouvelle touche de clavier.
     *
     * @param colonne La colonne de la touche sur le clavier (indexée à partir de 0).
     * @param rangee  La rangée de la touche sur le clavier (indexée à partir de 0).
     * @param doigt   Le doigt utilisé pour presser cette touche 
     *                (par exemple, "index", "majeur", "annulaire").
     */
    public ToucheClavier(int colonne, int rangee, String doigt) {
        this.colonne = colonne;
        this.rangee = rangee;
        this.doigt = doigt;
    }

    /**
     * Retourne la colonne de la touche sur le clavier.
     *
     * @return La colonne de la touche (indexée à partir de 0).
     */
    @Override
    public int getColonne() {
        return colonne;
    }

    /**
     * Retourne la rangée de la touche sur le clavier.
     *
     * @return La rangée de la touche (indexée à partir de 0).
     */
    @Override
    public int getRangee() {
        return rangee;
    }

    /**
     * Retourne le doigt utilisé pour presser cette touche.
     *
     * @return Le doigt associé à cette touche (par exemple, "index").
     */
    @Override
    public String getDoigt() {
        return doigt;
    }
}
