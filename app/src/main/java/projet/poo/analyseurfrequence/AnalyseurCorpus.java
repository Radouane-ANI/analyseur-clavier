package projet.poo.analyseurfrequence;

import java.io.IOException;
import java.util.Scanner;

import projet.poo.utils.*;

public class AnalyseurCorpus implements Logicel {

    /**
     * Lance l'analyse de fréquence sur un corpus. Demande à l'utilisateur le chemin
     * du corpus,
     * calcule les fréquences des n-grammes (unigrammes, bigrammes, trigrammes), et
     * écrit les résultats dans un fichier CSV.
     *
     */
    @Override
    public void exec() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez le chemin du corpus: ");
            String cheminCorpus = scanner.nextLine();
            GestionnaireFrequences g = new CompteurFrequences(cheminCorpus);
            g.calculerFrequences();
            EcrireCSV e = new FrequenceToCSV(g.getUngramme(), g.getBigramme(), g.getTrigramme(), g.getTailleTotal());
            try {
                e.ecrisDansCSV();
            } catch (IOException e1) {
                System.out.println("Une erreur s'est produite lors de l'écriture du CSV");
                System.out.println(e1.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}