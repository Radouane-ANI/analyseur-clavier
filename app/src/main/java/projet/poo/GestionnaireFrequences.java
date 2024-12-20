package projet.poo;

import java.util.Map;

public interface GestionnaireFrequences {

    void calculerFrequences();

    Map<String, Integer> obtenirFrequences();

    boolean calculTerminer();
}
