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
    public void traiterCSV(String cheminFichier,Map<String, List<Touche>> dispositionClavier) {
        List<Ngram> ngrammes = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            boolean premiereLigne = true;
            boolean enTete = true;
    
            while ((ligne = br.readLine()) != null) {
                if (premiereLigne) {
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
    
                    ngrammes.add(new Ngram(type, ngramme, valeur));
                }
            }

             // Supprimer les n-grammes avec des séquences de touches > 3
             ngrammes.removeIf(ngram -> ngram.getSequenceTouches(dispositionClavier).size() > 3);

             // Afficher les n-grammes filtrés
             System.out.println("N-grammes restants après filtrage :");
             for (INgram ngram : ngrammes) {
                 int coutTouches = ngram.calculerCoutTouches(dispositionClavier);
                 System.out.println("Type: " + ngram.getType() + ", N-gramme: " + ngram.getNgramme() +
                                    ", Valeur: " + ngram.getValeur() + ", Coût: " + coutTouches);
             } 
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
