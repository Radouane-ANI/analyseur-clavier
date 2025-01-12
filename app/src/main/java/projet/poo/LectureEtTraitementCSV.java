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

    private String[] extraireParties(String ligne) {
        String[] parties = new String[3];
    
        // Trouver les indices des virgules
        int premiereVirgule = ligne.indexOf(',');
        int derniereVirgule = ligne.lastIndexOf(',');

         // Vérification : S'assurer que les indices sont valides
        if (premiereVirgule == -1 || derniereVirgule == -1 || premiereVirgule == derniereVirgule) {
            throw new IllegalArgumentException("La ligne est mal formatée : " + ligne);
        }
    
        // Extraire chaque partie
        String type = ligne.substring(0, premiereVirgule).trim();
        String ngramme = ligne.substring(premiereVirgule + 1, derniereVirgule).trim();
        String valeur = ligne.substring(derniereVirgule + 1).trim();
    
        // Validation du type
        if (!type.equals("ungramme") && !type.equals("bigramme") && !type.equals("trigramme")) {
            throw new IllegalArgumentException("Type invalide : " + type);
        }
    
        // Supprimer les guillemets et enlever 2 caractères devant et derrière
        if (ngramme.length() >= 2 && ngramme.startsWith("\"") && ngramme.endsWith("\"")) {
            ngramme = ngramme.substring(1, ngramme.length() - 1);
        } else {
            throw new IllegalArgumentException("Ngramme mal formaté : " + ngramme);
        }
    
        // Validation de la valeur (doit être une suite de chiffres)
        if (!valeur.matches("\\d+")) {
            throw new IllegalArgumentException("Valeur invalide : " + valeur);
        }
    
        // Attribuer les parties validées
        parties[0] = type;      // Type
        parties[1] = ngramme;   // Ngramme
        parties[2] = valeur;    // Valeur
    
        return parties;
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
                    String[] headerParts = extraireParties(ligne);
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
    
                String[] parties = extraireParties(ligne);
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
