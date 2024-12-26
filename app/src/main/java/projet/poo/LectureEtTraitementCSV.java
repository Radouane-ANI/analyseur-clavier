package projet.poo;

import java.io.*;
import java.util.*;

public class LectureEtTraitementCSV {
    
    public static void traiterCSV(String cheminFichier,Map<String, String> dispositionClavier) {
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

            // Calculer et trier les n-grammes par coût des touches nécessaires
            ngrammes.sort((a, b) -> {
            int coutA = a.calculerCoutTouches(dispositionClavier);
            int coutB = b.calculerCoutTouches(dispositionClavier);
            return Integer.compare(coutA, coutB); // Tri par coût croissant
            });

             // Afficher les n-grammes triés
        System.out.println("Affichage de tous les n-grammes triés par coût des touches :");
        for (Ngram ngram : ngrammes) {
            int coutTouches = ngram.calculerCoutTouches(dispositionClavier);
            System.out.println("Type: " + ngram.getType() + ", N-gramme: " + ngram.getNgramme() +
                               ", Valeur: " + ngram.getValeur() + ", Coût: " + coutTouches);
        }
    
            // Trier les n-grammes par fréquence décroissante
            // ngrammes.sort(Comparator.comparingInt(Ngram::getValeur).reversed());
    
            // System.out.println("Affichage de tous les n-grammes :");
            // for (Ngram ngram : ngrammes) {
            //     System.out.println("Type: " + ngram.getType() + ", N-gramme: " + ngram.getNgramme() + ", Valeur: " + ngram.getValeur());
            // }
            
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
