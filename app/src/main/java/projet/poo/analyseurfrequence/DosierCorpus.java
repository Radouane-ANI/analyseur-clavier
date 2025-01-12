package projet.poo.analyseurfrequence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour gérer un corpus de textes stockés dans un dossier.
 */
public class DosierCorpus implements Corpus {
    private final File corpus;
    private final List<String> fichierCorpus;

    /**
     * Constructeur de la classe DosierCorpus.
     * 
     * @param urlCorpus le chemin du dossier contenant les textes.
     */
    public DosierCorpus(String urlCorpus) {
        if (urlCorpus == null) {
            throw new IllegalArgumentException("Le chemin spécifié n'est pas un dossier valide.");
        }
        this.corpus = new File(urlCorpus);
        if (!corpus.isDirectory()) {
            throw new IllegalArgumentException("Le chemin spécifié n'est pas un dossier valide.");
        }
        this.fichierCorpus = new ArrayList<>();
        File[] fichiers = corpus.listFiles((dir, name) -> name.endsWith(".txt"));

        if (fichiers != null) {
            for (File fichier : fichiers) {
                if (fichier.isFile()) {
                    this.fichierCorpus.add(fichier.getPath());
                }
            }
        }
    }

    /**
     * Obtient la liste des chemins des textes dans le corpus.
     * 
     * @return une liste de chemins de fichiers.
     */
    @Override
    public List<String> obtenirTextes() {
        return new ArrayList<>(fichierCorpus);
    }

    /**
     * Ajoute un texte au corpus à partir d'une URL.
     * 
     * @param urlTexte l'URL du texte à ajouter.
     */
    @Override
    public void ajouterTexte(String urlTexte) {
        if (urlTexte == null) {
            return;
        }
        File fic = new File(urlTexte);
        if (urlTexte.endsWith(".txt") && fic.isFile()) {
            fichierCorpus.add(urlTexte);
        }
    }

}
