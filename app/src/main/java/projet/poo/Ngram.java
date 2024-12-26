package projet.poo;

import java.util.Map;

public class Ngram {
    private final String type;
    private final String ngramme;
    private final int valeur;

    public Ngram(String type, String ngramme, int valeur) {
        this.type = type;
        this.ngramme = ngramme;
        this.valeur = valeur;
    }

    public String getType() {
        return type;
    }

    public String getNgramme() {
        return ngramme;
    }

    public int getValeur() {
        return valeur;
    }

    public int calculerCoutTouches(Map<String, String> dispositionClavier) {
        int cout = 0;
        for (char c : ngramme.toCharArray()) { // Parcourt chaque caractère du n-gramme
            String sequence = dispositionClavier.getOrDefault(String.valueOf(c), ""); // Récupère les touches nécessaires
            cout += sequence.split(",").length; // Compte le nombre de touches nécessaires
        }
        return cout;
    }
}
