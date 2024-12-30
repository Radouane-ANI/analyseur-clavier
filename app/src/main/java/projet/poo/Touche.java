package projet.poo;

/**
 * Représente une interface pour une touche de clavier.
 * Permet d'obtenir des informations sur la position de la touche
 * (colonne et rangée) ainsi que le doigt utilisé pour la presser.
 */
public interface Touche {

    /**
     * Retourne la colonne de la touche sur le clavier.
     *
     * @return La colonne de la touche (indexée à partir de 0).
     */
    int getColonne();

    /**
     * Retourne la rangée de la touche sur le clavier.
     *
     * @return La rangée de la touche (indexée à partir de 0).
     */
    int getRangee();

     /**
     * Retourne le doigt utilisé pour presser cette touche.
     *
     * @return Le doigt associé à cette touche (par exemple, "index").
     */
    String getDoigt();
}
