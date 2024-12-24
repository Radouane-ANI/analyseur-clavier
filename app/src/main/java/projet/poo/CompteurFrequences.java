package projet.poo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe pour gérer le calcul des fréquences des n-grammes dans un corpus de
 * textes.
 */
public class CompteurFrequences implements GestionnaireFrequences {
    private final Map<String, Integer> ungrammeResult;
    private final Map<String, Integer> bigrammeResult;
    private final Map<String, Integer> trigrammeResult;

    private final Corpus corpus;
    private int tailleTotal;

    /**
     * Constructeur de la classe CompteurFrequences.
     * 
     * @param urlCorpus le chemin du dossier contenant les textes.
     */
    public CompteurFrequences(String urlCorpus) {
        this.ungrammeResult = new ConcurrentHashMap<>();
        this.bigrammeResult = new ConcurrentHashMap<>();
        this.trigrammeResult = new ConcurrentHashMap<>();
        this.corpus = new DosierCorpus(urlCorpus);
    }

    /**
     * Calcule les fréquences des n-grammes dans le corpus de textes, en utilisant
     * un thread par texte. Attends la fin de tous les threads avant de retourner.
     */
    @Override
    public void calculerFrequences() {
        ungrammeResult.clear();
        bigrammeResult.clear();
        trigrammeResult.clear();
        tailleTotal = 0;
        List<String> textes = corpus.obtenirTextes();
        List<Thread> compteur = new ArrayList<>();
        List<AnalyseurNGrammes> calculs = new ArrayList<>();

        for (String cheminTexte : textes) {
            AnalyseurNGrammes calcul = new CalculNGrammes(cheminTexte, ungrammeResult, bigrammeResult, trigrammeResult);
            calculs.add(calcul);
            Thread thread = new Thread(calcul);
            thread.start();
            compteur.add(thread);
        }

        for (Thread thread : compteur) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (AnalyseurNGrammes analyseurNGrammes : calculs) {
            tailleTotal += analyseurNGrammes.getTaileTexte();
        }
    }

    /**
     * Obtient les fréquences des ungrammes.
     * 
     * @return une map contenant les fréquences des ungrammes.
     */
    @Override
    public Map<String, Integer> getUngramme() {
        return new HashMap<>(ungrammeResult);
    }

    /**
     * Obtient les fréquences des bigrammes.
     * 
     * @return une map contenant les fréquences des bigrammes.
     */
    @Override
    public Map<String, Integer> getBigramme() {
        return new HashMap<>(bigrammeResult);
    }

    /**
     * Obtient les fréquences des trigrammes.
     * 
     * @return une map contenant les fréquences des trigrammes.
     */
    @Override
    public Map<String, Integer> getTrigramme() {
        return new HashMap<>(trigrammeResult);
    }

    /**
     * Obtient la taille totale du texte analysé.
     * 
     * @return la taille totale du texte.
     */
    @Override
    public int getTailleTotal() {
        return tailleTotal;
    }
}
