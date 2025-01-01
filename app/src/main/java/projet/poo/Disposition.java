package projet.poo;

import java.util.List;
import java.util.Map;

public interface Disposition {

    void analyseDisposition();

    List<Mouvement> getsequenceTouche(char caractere);

    Geometry getGeometrie();

    Map<String, List<Mouvement>> getSequenceTouche();
}
