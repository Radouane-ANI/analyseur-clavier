package projet.poo;

import java.util.Map;

public interface AccumulateurFrequences {
    
    void accumulerFrequences(Map<String, Integer> frequences);

    Map<String, Integer> obtenirFrequences();

}
