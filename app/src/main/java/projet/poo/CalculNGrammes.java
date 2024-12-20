package projet.poo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class CalculNGrammes implements AnalyseurNGrammes {
    private final Path cheminFichier;
    private final Map<String, Integer> ungramme;
    private final Map<String, Integer> bigramme;
    private final Map<String, Integer> trigramme;
    private int tailleTexte;

    public CalculNGrammes(Path cheminFichier, Map<String, Integer> ungramme, Map<String, Integer> bigramme,
            Map<String, Integer> trigramme) {
        if (cheminFichier == null || ungramme == null || bigramme == null || trigramme == null) {
            throw new IllegalArgumentException("Au moins un argument est null.");
        }

        File fichier = new File(cheminFichier.toString());
        if (!fichier.exists()) {
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas : " + fichier.getAbsolutePath());
        }
        if (!fichier.isFile()) {
            throw new IllegalArgumentException(
                    "Le chemin spécifié ne correspond pas à un fichier : " + fichier.getAbsolutePath());
        }
        if (!fichier.canRead()) {
            throw new IllegalArgumentException(
                    "Le fichier spécifié ne peut pas être lu : " + fichier.getAbsolutePath());
        }

        this.cheminFichier = cheminFichier;
        this.ungramme = ungramme;
        this.bigramme = bigramme;
        this.trigramme = trigramme;
    }

    @Override
    public void run() {
        StringBuilder contenu = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier.toString()))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append("\n");
            }
            analyserTexte(contenu.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + cheminFichier, e);
        }

    }

    @Override
    public void analyserTexte(String texte) {
        if (texte == null) {
            return;
        }
        tailleTexte = texte.length();
        for (int i = 0; i < tailleTexte; i++) {
            ungramme.compute(texte.substring(i, i + 1), (k, v) -> (v == null) ? 0 : v + 1);
            if (i < tailleTexte - 1) {
                bigramme.compute(texte.substring(i, i + 2), (k, v) -> (v == null) ? 0 : v + 1);
            }
            if (i < tailleTexte - 2) {
                trigramme.compute(texte.substring(i, i + 3), (k, v) -> (v == null) ? 0 : v + 1);
            }
        }
    }

    @Override
    public int getTaileTexte() {
        return tailleTexte;
    }

}
