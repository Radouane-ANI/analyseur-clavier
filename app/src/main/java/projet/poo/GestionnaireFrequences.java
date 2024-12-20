package projet.poo;

import java.util.Map;

public interface GestionnaireFrequences {

    void calculerFrequences();

    boolean calculTerminer();

    Map<String, Integer> getUngramme();

    Map<String, Integer> getBigramme();

    Map<String, Integer> getTrigramme();

    int getTailleTotal();

}
