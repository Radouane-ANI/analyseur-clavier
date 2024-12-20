package projet.poo;

import java.util.Map;

public interface AnalyseurNGrammes {

    void analyserTexte(String texte);

    Map<String, Integer> getUngramme();

    Map<String, Integer> getBigramme();

    Map<String, Integer> getTrigramme();

}
