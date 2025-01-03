package projet.poo;

public enum Doigts {
    POUCE, INDEX, MAJEUR, ANNULAIRE, AURICULAIRE;

    int getColonneRepos(Geometry g, int ligne, boolean droite) {
        int decalage = 0;
        switch (this) {
            case POUCE:
                return g.getEsp().colonne();
            case INDEX:
                decalage = droite ? 1 : 0;
                break;
            case MAJEUR:
                decalage = droite ? 3 : -2;
                break;
            case ANNULAIRE:
                decalage = droite ? 4 : -3;
                break;
            case AURICULAIRE:
                decalage = droite ? 5 : -4;
                break;
        }
        return g.getMillieu()[ligne] + decalage;
    }
}
