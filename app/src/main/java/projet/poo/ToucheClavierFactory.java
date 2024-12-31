package projet.poo;

public class ToucheClavierFactory {

    public static Touche create(int ligne, int colonne, Geometry geometrieClavier) {
        validateParameters(ligne, colonne, geometrieClavier);
        Doigts doigt = getDoigt(geometrieClavier, ligne, colonne);
        boolean mainsDroite = isMainsDroite(geometrieClavier, ligne, colonne);
        return new ToucheClavier(ligne, colonne, doigt, mainsDroite);
    }

    private static void validateParameters(int ligne, int colonne, Geometry geometrieClavier) {
        if (ligne < 0 || colonne < 0 || geometrieClavier == null) {
            throw new IllegalArgumentException("Les paramètres fournis sont invalides.");
        }
    }

    private static boolean isMainsDroite(Geometry geometrieClavier, int ligne, int colonne) {
        if (ligne < 1 || ligne > 5) {
            return false;
        }
        int[] limites = geometrieClavier.getMillieu();
        return colonne > limites[ligne];
    }

    private static Doigts getDoigt(Geometry geometrieClavier, int ligne, int colonne) {
        if (ligne == 5 && (geometrieClavier != Geometry.JIS && colonne == 4
                || geometrieClavier == Geometry.JIS && colonne == 5)) {
            return Doigts.POUCE; // touche espace
        }
        int[] limites = geometrieClavier.getMillieu();
        int milieu = limites[ligne];
        int decalage = milieu <= 6 ? 0 : 1;
        if (colonne >= milieu - decalage - 1 && colonne <= milieu + decalage) {
            return Doigts.INDEX;
        } else if (colonne >= milieu - decalage - 2 && colonne <= milieu + decalage + 1) {
            return Doigts.MAJEUR;
        } else if (colonne >= milieu - decalage - 3 && colonne <= milieu + decalage + 2) {
            return Doigts.ANNULAIRE;
        } else {
            return Doigts.AURICULAIRE;
        }
    }

    private static record ToucheClavier(int ligne, int colonne, Doigts doigt, boolean mainsDroite) implements Touche {
    }
}
