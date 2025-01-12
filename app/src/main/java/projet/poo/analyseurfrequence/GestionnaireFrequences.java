package projet.poo.analyseurfrequence;

import java.util.Map;

/**
 * Interface pour gérer les fréquences des n-grammes.
 */
public interface GestionnaireFrequences {

    /**
     * Calcule les fréquences des n-grammes.
     */
    void calculerFrequences();

    /**
     * Obtient les fréquences des ungrammes.
     * 
     * @return une map contenant les fréquences des ungrammes.
     */
    Map<String, Integer> getUngramme();

    /**
     * Obtient les fréquences des bigrammes.
     * 
     * @return une map contenant les fréquences des bigrammes.
     */
    Map<String, Integer> getBigramme();

    /**
     * Obtient les fréquences des trigrammes.
     * 
     * @return une map contenant les fréquences des trigrammes.
     */
    Map<String, Integer> getTrigramme();

    /**
     * Obtient la taille totale du texte analysé.
     * 
     * @return la taille totale du texte.
     */
    int getTailleTotal();

}
