package projet.poo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FrequenceToCSV implements EcrireCSV {
    private final Map<String, Integer> ungramme;
    private final Map<String, Integer> bigramme;
    private final Map<String, Integer> trigramme;
    private final int taille;

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

    @Override
    public void ecrisDansCSV() throws IOException {
        try (FileWriter writer = new FileWriter("frequence.csv")) {
            writer.append("Taille,,").append(String.valueOf(taille)).append("\n");

            writer.append("Type,N-gramme,Valeur\n");

            writeMap(writer, "Ungramme", ungramme);

            writeMap(writer, "Bigramme", bigramme);

            writeMap(writer, "Trigramme", trigramme);

            System.out.println("Fichier CSV écrit avec succès !");
        }
    }

    private void writeMap(FileWriter writer, String type, Map<String, Integer> map) throws IOException {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            writer.append(type).append(",")
                    .append(entry.getKey()).append(",")
                    .append(String.valueOf(entry.getValue())).append("\n");
        }
    }

}
