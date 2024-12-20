package projet.poo;

import java.nio.file.Path;
import java.util.List;

public interface Corpus {

    List<Path> obtenirTextes();

    void ajouterTexte(String urlTexte);

}
