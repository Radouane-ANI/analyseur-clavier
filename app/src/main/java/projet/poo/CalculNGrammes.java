package projet.poo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CalculNGrammes implements AnalyseurNGrammes, Runnable {
    private final Path cheminFichier;
    private final Map<String, Integer> ungramme;
    private final Map<String, Integer> bigramme;
    private final Map<String, Integer> trigramme;

    public CalculNGrammes(Path cheminFichier) {
        if (cheminFichier == null) {
            throw new IllegalArgumentException("Le fichier ne peut pas être null.");
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
        this.ungramme = new HashMap<>();
        this.bigramme = new HashMap<>();
        this.trigramme = new HashMap<>();
    }

    @Override
    public void run() {
        StringBuilder contenu = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier.toString()))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + cheminFichier, e);
        }

    }

    @Override
    public void analyserTexte(String texte) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'analyserTexte'");
    }

    @Override
    public Map<String, Integer> getUngramme() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUngramme'");
    }

    @Override
    public Map<String, Integer> getBigramme() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBigramme'");
    }

    @Override
    public Map<String, Integer> getTrigramme() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTrigramme'");
    }

}
