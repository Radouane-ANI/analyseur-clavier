package projet.poo;

import java.util.List;

/**
 * Interface pour gérer un corpus de textes.
 */
public interface Corpus {

    /**
     * Obtient la liste des chemins des textes dans le corpus.
     * 
     * @return une liste de chemins de fichiers.
     */
    List<String> obtenirTextes();

    /**
     * Ajoute un texte au corpus à partir d'une URL.
     * 
     * @param urlTexte l'URL du texte à ajouter.
     */
    void ajouterTexte(String urlTexte);

}
