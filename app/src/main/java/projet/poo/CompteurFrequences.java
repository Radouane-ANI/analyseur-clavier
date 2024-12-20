package projet.poo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompteurFrequences implements GestionnaireFrequences {
    private final Map<String, Integer> ungrammeResult;
    private final Map<String, Integer> bigrammeResult;
    private final Map<String, Integer> trigrammeResult;

    private boolean calculTerminer;
    private final Corpus corpus;
    private int tailleTotal;

    public CompteurFrequences(String urlCorpus) {
        this.ungrammeResult = new ConcurrentHashMap<>();
        this.bigrammeResult = new ConcurrentHashMap<>();
        this.trigrammeResult = new ConcurrentHashMap<>();
        calculTerminer = false;
        this.corpus = new DosierCorpus(urlCorpus);
    }

    @Override
    public void calculerFrequences() {
        ungrammeResult.clear();
        bigrammeResult.clear();
        trigrammeResult.clear();
        calculTerminer = false;
        tailleTotal = 0;
        List<Path> textes = corpus.obtenirTextes();
        List<Thread> compteur = new ArrayList<>();
        List<AnalyseurNGrammes> calculs = new ArrayList<>();

        for (Path cheminTexte : textes) {
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
        calculTerminer = true;
    }

    @Override
    public boolean calculTerminer() {
        return calculTerminer;
    }

    @Override
    public Map<String, Integer> getUngramme() {
        return new HashMap<>(ungrammeResult);
    }

    @Override
    public Map<String, Integer> getBigramme() {
        return new HashMap<>(bigrammeResult);
    }

    @Override
    public Map<String, Integer> getTrigramme() {
        return new HashMap<>(trigrammeResult);
    }

    @Override
    public int getTailleTotal() {
        return tailleTotal;
    }
}
