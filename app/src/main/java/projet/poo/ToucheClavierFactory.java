package projet.poo;

/**
 * Fabrique de touches de clavier permettant de créer des instances de {@code Touche}.
 * Cette classe contient des méthodes utilitaires pour valider les paramètres,
 * déterminer si une touche est utilisée par la main droite ou la main gauche,
 * et associer un doigt spécifique à une touche en fonction de la géométrie du clavier.
 */
public class ToucheClavierFactory {

    /**
     * Crée une instance de {@code Touche} en fonction de la ligne, de la colonne,
     * et de la géométrie du clavier spécifiée.
     *
     * @param ligne            la ligne de la touche (doit être un entier positif).
     * @param colonne          la colonne de la touche (doit être un entier positif).
     * @param geometrieClavier la géométrie du clavier ({@code ANSI}, {@code ISO}, etc.).
     * @return une instance de {@code Touche} correspondant aux paramètres spécifiés.
     * @throws IllegalArgumentException si les paramètres sont invalides (ligne ou colonne négative, géométrie nulle).
     */
    public static Touche create(int ligne, int colonne, Geometry geometrieClavier) {
        validateParameters(ligne, colonne, geometrieClavier);
        Doigts doigt = getDoigt(geometrieClavier, ligne, colonne);
        boolean mainsDroite = isMainsDroite(geometrieClavier, ligne, colonne);
        return new ToucheClavier(ligne, colonne, doigt, mainsDroite, geometrieClavier);
    }

    /**
     * Valide les paramètres fournis pour créer une touche.
     *
     * @param ligne            la ligne de la touche.
     * @param colonne          la colonne de la touche.
     * @param geometrieClavier la géométrie du clavier.
     * @throws IllegalArgumentException si la ligne ou la colonne est négative, ou si la géométrie est nulle.
     */
    private static void validateParameters(int ligne, int colonne, Geometry geometrieClavier) {
        if (ligne < 0 || colonne < 0 || geometrieClavier == null) {
            throw new IllegalArgumentException("Les paramètres fournis sont invalides.");
        }
    }

    /**
     * Détermine si une touche est utilisée par la main droite en fonction de
     * sa position et de la géométrie du clavier.
     *
     * @param geometrieClavier la géométrie du clavier.
     * @param ligne            la ligne de la touche.
     * @param colonne          la colonne de la touche.
     * @return {@code true} si la touche est utilisée par la main droite, sinon {@code false}.
     */
    private static boolean isMainsDroite(Geometry geometrieClavier, int ligne, int colonne) {
        if (ligne < 1 || ligne > 5) {
            return false;
        }
        int[] limites = geometrieClavier.getMillieu();
        return colonne > limites[ligne];
    }

    /**
     * Associe un doigt spécifique à une touche en fonction de la position de
     * celle-ci et de la géométrie du clavier.
     *
     * @param geometrieClavier la géométrie du clavier.
     * @param ligne            la ligne de la touche.
     * @param colonne          la colonne de la touche.
     * @return le doigt associé à la touche.
     */
    private static Doigts getDoigt(Geometry geometrieClavier, int ligne, int colonne) {
        if (ligne == 5 && (geometrieClavier != Geometry.JIS && colonne == 4
                || geometrieClavier == Geometry.JIS && colonne == 5)) {
            return Doigts.POUCE; // touche espace
        }
        int[] limites = geometrieClavier.getMillieu();
        int milieu = limites[ligne];
        if (colonne >= milieu - 1 && colonne <= milieu + 2) {
            return Doigts.INDEX;
        } else if (colonne >= milieu - 2 && colonne <= milieu + 3) {
            return Doigts.MAJEUR;
        } else if (colonne >= milieu - 3 && colonne <= milieu + 4) {
            return Doigts.ANNULAIRE;
        } else {
            return Doigts.AURICULAIRE;
        }
    }

    /**
     * Implémentation interne de {@code Touche} utilisée pour représenter une touche de clavier.
     */
    private static record ToucheClavier(int ligne, int colonne, Doigts doigt, boolean mainsDroite, Geometry geometry) implements Touche {
    }
}
