package projet.poo;

import java.io.*;
import java.util.*;

/**
 * Classe permettant de lire et traiter un fichier CSV contenant des informations sur les n-grammes.
 * Implémente l'interface ICSVTraitement.
 * Le traitement inclut la lecture du fichier, la création des n-grammes, 
 * le filtrage en fonction du nombre de touches nécessaires et l'affichage des résultats.
 */
public class LectureEtTraitementCSV implements ICSVTraitement {
    
    /**
     * Lit un fichier CSV et effectue un traitement sur les n-grammes qu'il contient.
     * Le fichier CSV doit respecter le format suivant :
     * 
     * Chaque ligne contient trois colonnes séparées par des virgules :
     *   Type de l'n-gramme
     *   Séquence de caractères représentant l'n-gramme.
     *   Valeur associée (une fréquence).
     * 
     * Le traitement inclut les étapes suivantes :
     *   Ignorer les deux premières lignes (entête).
     *   Créer des objets Ngram à partir des données valides.
     *   Filtrer les n-grammes ayant une séquence de touches de plus de 3.
     *   Afficher les n-grammes restants avec leur coût de touches calculé.
     *
     * @param cheminFichier
     * @param dispositionClavier
     */
    @Override
    public void traiterCSV(String cheminFichier,Map<String, List<Mouvement>> dispositionClavier) {
        List<INgram> ngrammes = new ArrayList<>();
        PoidsMouvements poids = new CalculPoids();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            boolean premiereLigne = true;
            int tailleCorpus = 1;
            boolean enTete = true;
    
            while ((ligne = br.readLine()) != null) {
                if (premiereLigne) {
                    String[] headerParts = ligne.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (headerParts.length < 2) {
                        throw new IllegalArgumentException("Le fichier CSV a un mauvais format.");
                    }
                    try {
                        tailleCorpus = Integer.parseInt(headerParts[1]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Le deuxième élément de l'entête du fichier csv doit être un entier.", e);
                    }
                    premiereLigne = false; 
                    continue;
                }
                if (enTete) {
                    enTete = false; 
                    continue;
                }
    
                String[] parties = ligne.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parties.length == 3) {
                    String type = parties[0];
                    String ngramme = parties[1].replace("\"", "");
                    int valeur = Integer.parseInt(parties[2].trim());
                    INgram ngram;
                    try {
                        ngram = new Ngram(type, ngramme, valeur, dispositionClavier);
                    } catch (Exception e) {
                        System.out.println("Le ngramme " + ngramme + " n'a pas pu être créé.");
                        System.out.println(e.getMessage());
                        continue;
                    }
                    ngrammes.add(ngram);
                    poids.fequence1Gram(ngram.getSequenceTouches());
                    ngram.supprimeLongueSequence(3);
                }
            }

            // Supprimer les n-grammes avec que des séquences de touches > 3
            ngrammes.removeIf(ngram -> ngram.getSequenceTouches().size() == 0);

            double somme = 0;
            List<Double> res = poids.calculerPoids(ngrammes);
            for (Double d : res) {
                somme += d;
            }
            System.out.println("Score du clavier pour ce corpus: " + somme/(double)tailleCorpus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
