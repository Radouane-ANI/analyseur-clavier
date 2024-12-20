package projet.poo;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DosierCorpus implements Corpus {
    private final File corpus;
    private final List<Path> fichierCorpus;

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
                    this.fichierCorpus.add(fichier.toPath());
                }
            }
        }
    }

    @Override
    public List<Path> obtenirTextes() {
        return new ArrayList<>(fichierCorpus);
    }

    @Override
    public void ajouterTexte(String urlTexte) {
        if (urlTexte == null) {
            return;
        }
        File fic = new File(urlTexte);
        if (urlTexte.endsWith(".txt") && fic.isFile()) {
            fichierCorpus.add(fic.toPath());
        }
    }

}
