package projet.poo;

import java.util.List;

public interface Disposition {

    void analyseDisposition();

    List<Mouvement> getsequenceTouche(char caractere);

    String getGeometrie();
}
