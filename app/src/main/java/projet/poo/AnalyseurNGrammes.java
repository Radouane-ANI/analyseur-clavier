package projet.poo;

import java.util.HashMap;

public interface AnalyseurNGrammes {

    void analyserTexte(String texte);

    HashMap<String, Integer> getUngramme();

    HashMap<String, Integer> getBigramme();

    HashMap<String, Integer> getTrigramme();

}
