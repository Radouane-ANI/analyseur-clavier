package projet.poo;

import java.util.HashMap;
import java.util.Map;

public class CompteurFrequences implements GestionnaireFrequences {
    private final Map<String, Integer> frequencesResult;
    private boolean calculTerminer;
    private final Corpus corpus;

    public CompteurFrequences(String urlCorpus) {
        this.frequencesResult = new HashMap<>();
        calculTerminer = false;
        this.corpus = new DosierCorpus(urlCorpus);
    }

    @Override
    public void calculerFrequences() {
        // TODO
    }

    @Override
    public Map<String, Integer> obtenirFrequences() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenirFrequences'");
    }

    @Override
    public boolean calculTerminer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculTerminer'");
    }

}
