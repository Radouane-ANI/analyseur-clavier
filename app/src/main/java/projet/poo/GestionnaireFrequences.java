package projet.poo;

import java.util.HashMap;

public interface GestionnaireFrequences {

    void calculerFrequences(String urlCorpus);

    HashMap<String, Integer> obtenirFrequences();

    boolean calculTerminer();
}
