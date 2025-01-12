package projet.poo;

/**
 * Interface représentant une touche d'un clavier avec ses propriétés
 * géométriques et ergonomiques.
 */
public interface Touche {

    /**
     * Obtient l'indice de la colonne de la touche.
     *
     * @return l'indice de la colonne.
     */
    int colonne();

    /**
     * Obtient l'indice de la ligne de la touche.
     *
     * @return l'indice de la ligne.
     */
    int ligne();

    /**
     * Obtient le doigt utilisé pour appuyer sur la touche.
     *
     * @return le doigt associé à cette touche.
     */
    Doigts doigt();

    /**
     * Vérifie si la touche est utilisée par la main droite.
     *
     * @return {@code true} si la main droite est utilisée, sinon {@code false}.
     */
    boolean mainsDroite();

    /**
     * Obtient les informations géométriques de la touche.
     *
     * @return un objet {@code Geometry} contenant les informations géométriques.
     */
    Geometry geometry();
}
