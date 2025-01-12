package projet.poo.evaluateurdisposition;

/**
 * Enumération représentant les doigts utilisés pour appuyer sur les touches d'un clavier.
 * Chaque valeur de l'énumération correspond à un doigt humain : pouce, index, majeur, annulaire, auriculaire.
 */
public enum Doigts {
    POUCE, INDEX, MAJEUR, ANNULAIRE, AURICULAIRE;

    /**
     * Calcule la position de repos (colonne) pour un doigt donné sur une ligne spécifique,
     * en fonction de la géométrie du clavier et de la main utilisée (droite ou gauche).
     *
     * @param g      la géométrie du clavier ({@code ANSI}, {@code ISO}, etc.).
     * @param ligne  la ligne de la touche concernée (doit être un entier valide).
     * @param droite indique si le doigt appartient à la main droite ({@code true}) ou gauche ({@code false}).
     * @return la colonne correspondant à la position de repos pour le doigt spécifié.
     */
    int getColonneRepos(Geometry g, int ligne, boolean droite) {
        int decalage = 0;
        switch (this) {
            case POUCE:
             // Retourne la colonne de la touche espace définie pour la géométrie.
                return g.getEsp().colonne();
            case INDEX:
            // Décalage en fonction de la main utilisée.
                decalage = droite ? 1 : 0;
                break;
            case MAJEUR:
            // Décalage plus important pour le majeur.
                decalage = droite ? 3 : -2;
                break;
            case ANNULAIRE:
            // Décalage spécifique pour l'annulaire.
                decalage = droite ? 4 : -3;
                break;
            case AURICULAIRE:
            // Décalage maximal pour l'auriculaire.
                decalage = droite ? 5 : -4;
                break;
        }
        // Ajoute le décalage à la colonne centrale de la ligne pour la géométrie donnée.
        return g.getMillieu()[ligne] + decalage;
    }
}
