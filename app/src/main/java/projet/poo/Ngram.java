package projet.poo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Représente un n-gramme, utilisé pour modéliser une séquence de caractères
 * avec un type, une valeur associée et des méthodes pour calculer son coût
 * et obtenir la séquence de touches associées sur un clavier.
 */
public class Ngram implements INgram {
    private final String type;
    private final String ngramme;
    private final int valeur;


    /**
     * Constructeur pour créer un nouvel objet Ngram.
     *
     * @param type    Le type de l'n-gramme (par exemple, "bigramme", "trigramme").
     * @param ngramme La séquence de caractères représentant l'n-gramme.
     * @param valeur  La valeur associée à cet n-gramme (sa fréquence).
     */
    public Ngram(String type, String ngramme, int valeur) {
        this.type = type;
        this.ngramme = ngramme;
        this.valeur = valeur;
    }

    /**
     * Retourne le type de l'n-gramme.
     *
     * @return Le type de l'n-gramme (par exemple, "bigramme").
     */
    public String getType() {
        return type;
    }

    /**
     * Retourne la séquence de caractères représentant l'n-gramme.
     *
     * @return La séquence de caractères de l'n-gramme.
     */
    public String getNgramme() {
        return ngramme;
    }

    /**
     * Retourne la valeur associée à cet n-gramme.
     *
     * @return La valeur associée (sa fréquence).
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Calcule le coût d'utilisation des touches nécessaires pour saisir cet n-gramme.
     *
     * @param dispositionClavier Une carte associant chaque caractère à une liste de touches.
     * @return Le coût calculé basé sur le nombre de touches nécessaires pour cet n-gramme.
     */
    @Override
    public int calculerCoutTouches(Map<String, List<Touche>> dispositionClavier) {
        return getSequenceTouches(dispositionClavier).size(); // Nombre de touches
    }

    /**
     * Retourne la séquence de touches nécessaires pour saisir cet n-gramme.
     *
     * @param dispositionClavier Une carte associant chaque caractère à une liste de touches.
     * @return La liste des touches nécessaires pour cet n-gramme.
     */
    @Override
    public List<Touche> getSequenceTouches(Map<String, List<Touche>> dispositionClavier) {
        List<Touche> sequence = new ArrayList<>();
        for (char c : ngramme.toCharArray()) {
            List<Touche> touches = dispositionClavier.getOrDefault(String.valueOf(c), new ArrayList<>());
            sequence.addAll(touches);
        }
        return sequence;
}

}
