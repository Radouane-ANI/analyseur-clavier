package projet.poo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Classe pour écrire les fréquences des n-grammes dans un fichier CSV.
 */
public class FrequenceToCSV implements EcrireCSV {
    private final Map<String, Integer> ungramme;
    private final Map<String, Integer> bigramme;
    private final Map<String, Integer> trigramme;
    private final int taille;

    /**
     * Constructeur de la classe FrequenceToCSV.
     * 
     * @param ungramme  les fréquences des ungrammes.
     * @param bigramme  les fréquences des bigrammes.
     * @param trigramme les fréquences des trigrammes.
     * @param taille    la taille totale du texte analysé.
     */
    public FrequenceToCSV(Map<String, Integer> ungramme, Map<String, Integer> bigramme,
            Map<String, Integer> trigramme, int taille) {
        if (ungramme == null || bigramme == null || trigramme == null || taille < 0) {
            throw new IllegalArgumentException("Au moins un argument est null ou invalide.");
        }
        this.ungramme = ungramme;
        this.bigramme = bigramme;
        this.trigramme = trigramme;
        this.taille = taille;
    }

    /**
     * Écrit les fréquences des n-grammes dans un fichier CSV.
     * 
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void ecrisDansCSV() throws IOException {
        try (FileWriter writer = new FileWriter("frequence.csv")) {
            writer.append("Taille,").append(String.valueOf(taille)).append("\n");

            writer.append("Type,N-gramme,Valeur\n");

            writeMap(writer, "Ungramme", ungramme);

            writeMap(writer, "Bigramme", bigramme);

            writeMap(writer, "Trigramme", trigramme);

            System.out.println("Fichier CSV écrit avec succès !");
        }
    }

    /**
     * Échappe les caractères spéciaux pour le format CSV.
     * 
     * @param value la valeur à échapper.
     * @return la valeur échappée.
     */
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        value = value.replace("\"", "\"\"");
        return "\"" + value + "\"";
    }

    /**
     * Écrit une map de fréquences dans le fichier CSV.
     * 
     * @param writer le FileWriter pour écrire dans le fichier.
     * @param type   le type de n-gramme (ungramme, bigramme, trigramme).
     * @param map    la map contenant les fréquences des n-grammes.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    private void writeMap(FileWriter writer, String type, Map<String, Integer> map) throws IOException {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            writer.append(type).append(",")
                    .append(escapeCSV(entry.getKey())).append(",")
                    .append(entry.getValue() + "").append("\n");
        }
    }

}
