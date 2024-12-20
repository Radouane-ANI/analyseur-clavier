package projet.poo;

public interface AnalyseurNGrammes extends Runnable {

    void analyserTexte(String texte);

    int getTaileTexte();

}
