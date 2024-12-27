package projet.poo;

public class ToucheClavier implements Touche {
    private final int ligne;
    private final int colonne;
    private final Doigts doigt;
    private final boolean mainsDroite;

    public ToucheClavier(int ligne, int colonne, String geometrieClavier) {
        if (ligne < 0 || colonne < 0 || geometrieClavier == null) {
            throw new IllegalArgumentException("Les paramètres fournis sont invalides.");
        }
        this.ligne = ligne;
        this.colonne = colonne;
        this.doigt = getDoigt(geometrieClavier);
        this.mainsDroite = isMainsDroite(geometrieClavier);
    }

    @Override
    public int getColonne() {
        return colonne;
    }

    @Override
    public int getLigne() {
        return ligne;
    }

    @Override
    public Doigts getDoigt() {
        return doigt;
    }

    @Override
    public boolean isMainsDroite() {
        return mainsDroite;
    }

    private static boolean isMainsDroite(String geometrieClavier) {
        return true;
    }

    private static Doigts getDoigt(String geometrieClavier) {
        return Doigts.AURICULAIRE;
    }

    @Override
    public String toString() {
        return "ToucheClavier{" +
                "ligne=" + ligne +
                ", colonne=" + colonne + "}";
    }
}
