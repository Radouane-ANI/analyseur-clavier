package projet.poo;

import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Classe permettant de lire et traiter un fichier CSV contenant des informations sur les n-grammes.
 * Implémente l'interface ICSVTraitement.
 * Le traitement inclut la lecture du fichier, la création des n-grammes, 
 * le filtrage en fonction du nombre de touches nécessaires et l'affichage des résultats.
 */
public class LectureEtTraitementCSV implements ICSVTraitement {

    /**
     * Vérifie si une ligne est bien formé.
     *
     * @param parties Un tableau de taille 3 contenant [type, ngramme, valeur].
     * @return true si le tableau est bien formé, lève une exception sinon.
     */
    private boolean valideParties(String[] parties) {
        // Vérifier que le tableau contient exactement 3 éléments
        if (parties == null || parties.length != 3) {
            return false;
        }

        // Extraire les éléments
        String type = parties[0].trim();
        String valeur = parties[2].trim();

        // Validation du type
        if (!type.equals("Ungramme") && !type.equals("Bigramme") && !type.equals("Trigramme")) {
            return false;
        }

        // Validation de la valeur : doit être composée uniquement de chiffres
        if (!valeur.matches("\\d+")) {
            return false;
        }

        // Si toutes les validations passent, le tableau est bien formé
        return true;
    }    
    
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
     * 
     * @return La liste des n-grammes
     */
    @Override
    public List<INgram> traiterCSV(String cheminFichier,Map<String, List<Mouvement>> dispositionClavier) {
        List<INgram> ngrammes = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(cheminFichier))) {
            String[] ligne;
            boolean premiereLigne = true;
            boolean enTete = true;
    
            while ((ligne = reader.readNext()) != null) {
                if (premiereLigne) {
                    if (ligne.length < 2) {
                        throw new IllegalArgumentException("Le fichier CSV a un mauvais format.");
                    }
                    premiereLigne = false; 
                    continue;
                }
                if (enTete) {
                    enTete = false; 
                    continue;
                }
    
                if (!valideParties(ligne)) {
                    throw new IllegalArgumentException("Le fichier CSV a un mauvais format.");
                }
                if (ligne.length == 3) {
                    String ngramme = ligne[1].replace("\"", "");
                    int valeur = Integer.parseInt(ligne[2].trim());
                    INgram ngram;
                    try {
                        ngram = new Ngram(ngramme, valeur, dispositionClavier);
                    } catch (Exception e) {
                        System.out.println("Le ngramme " + ngramme + " n'a pas pu être créé.");
                        System.out.println(e.getMessage());
                        continue;
                    }
                    ngrammes.add(ngram);
                    ngram.supprimeLongueSequence(3);
                }
            }

            // Supprimer les n-grammes avec que des séquences de touches > 3
            ngrammes.removeIf(ngram -> ngram.getSequenceTouches().size() == 0);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return ngrammes;
    }


    /**
     * Donne la taille du corpus en lisant un fichier CSV.
     *
     * @param cheminFichier le chemin du fichier CSV contenant le corpus.
     * @return la taille du corpus extraite du deuxième élément de l'en-tête du
     *         fichier CSV.
     * @throws IllegalArgumentException si le fichier est null, mal formaté, ou si
     *                                  la taille du corpus
     *                                  n'est pas un entier valide.
     */
    @Override
    public int tailleCorpusCSV(String cheminFichier) {
        if (cheminFichier == null) {
            throw new IllegalArgumentException("Le fichier CSV a un mauvais format.");
        }
        int tailleCorpus = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne = br.readLine();
            if (ligne == null) {
                throw new IllegalArgumentException("Le fichier CSV a un mauvais format.");
            }

            String[] headerParts = ligne.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if (headerParts.length < 2) {
                throw new IllegalArgumentException("Le fichier CSV a un mauvais format.");
            }

            try {
                tailleCorpus = Integer.parseInt(headerParts[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Le deuxième élément de l'entête du fichier csv doit être un entier.", e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tailleCorpus;
    }

}
