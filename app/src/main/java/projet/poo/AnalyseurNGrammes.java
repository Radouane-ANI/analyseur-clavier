package projet.poo;

/**
 * Interface pour analyser les n-grammes dans un texte qui etends Runnable.
 */
public interface AnalyseurNGrammes extends Runnable {

    /**
     * Analyse le texte pour calculer les fréquences des 1-grammes, 2-grammes et 3-grammes.
     * 
     * @param texte le texte à analyser.
     */
    void analyserTexte(String texte);

    /**
     * Obtient la taille du texte analysé.
     * 
     * @return la taille du texte.
     */
    int getTaileTexte();

}
