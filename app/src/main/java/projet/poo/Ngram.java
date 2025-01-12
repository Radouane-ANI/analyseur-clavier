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
    private final String ngramme;
    private final int valeur;
    private final List<Mouvement> sequenceTouches;

    /**
     * Constructeur pour créer un nouvel objet Ngram.
     *
     * @param ngramme La séquence de caractères représentant l'n-gramme.
     * @param valeur  La valeur associée à cet n-gramme (sa fréquence).
     * @param dispositionClavier Une Map associant chaque caractère une liste des mouvements possibles pour l'ecrire.
     */
    public Ngram(String ngramme, int valeur, Map<String, List<Mouvement>> dispositionClavier) {
        this.ngramme = ngramme;
        this.valeur = valeur;
        this.sequenceTouches = calculSequenceTouches(dispositionClavier);
    }

    /**
     * Calcule la séquence de mouvements pour le n-gramme donné en fonction de la disposition du clavier.
     *
     * @param dispositionClavier Une map représentant la disposition du clavier, où la clé est un caractère
     *                           et la valeur est une liste de mouvements associés à ce caractère.
     * @return Une liste de mouvements représentant les séquences de touches pour le n-gramme.
     */
    private List<Mouvement> calculSequenceTouches(Map<String, List<Mouvement>> dispositionClavier) {
        List<Mouvement> sequence = new ArrayList<>();
        for (char c : ngramme.toCharArray()) {
            List<Mouvement> m =new ArrayList<>();
            if (!dispositionClavier.containsKey(String.valueOf(c))) {
                throw new IllegalArgumentException("Le caractère " + c + " n'est pas dans la disposition du clavier.");
            }
            List<Mouvement> mouveC = dispositionClavier.get(String.valueOf(c));
            for (Mouvement mouvement : mouveC) {
                m.add(mouvement);
            }
            if (sequence.size()==0) {
                sequence.addAll(m);
            }else{
                sequence = Mouvement.fusionneMouvements(sequence, mouveC);
            }
        }
        return sequence;
    }
        
    /**
     * Retourne le type de l'n-gramme.
     *
     * @return Le type de l'n-gramme (par exemple, "bigramme").
     */
    public String getType() {
        int longueur = getSequenceTouchesMin().getLongueur();
        switch (longueur) {
            case 1:
            return "unigramme";
            case 2:
            return "bigramme";
            case 3:
            return "trigramme";
            default:
            return longueur + "gramme";
        }
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
     * @return Le coût calculé basé sur le nombre de touches nécessaires pour cet n-gramme.
     */
    @Override
    public int calculerCoutTouches() {
        Mouvement min = getSequenceTouchesMin();
        if (min == null) {
            return 0;       
        }
        return min.getLongueur(); // Nombre de touches
    }

    /**
     * Retourne la séquence de touches nécessaires pour saisir cet n-gramme.
     *
     * @return La liste des touches nécessaires pour cet n-gramme.
     */
    @Override
    public List<Mouvement> getSequenceTouches() {
        return new ArrayList<>(sequenceTouches);
    }

    /**
     * Récupère le mouvement avec la longueur minimale d'une séquence de mouvements.
     *
     * @return le mouvement avec la longueur la plus courte, ou null si la séquence est vide.
     */
    @Override
    public Mouvement getSequenceTouchesMin() {
        List<Mouvement> sequence = getSequenceTouches();
        return sequence.stream().min((m1, m2) -> Integer.compare(m1.getLongueur(), m2.getLongueur())).orElse(null);
    }

    /**
     * Supprime les séquences de mouvements dont la longueur dépasse une valeur maximale.
     *
     * @param longueurMax La longueur maximale autorisée pour une séquence de mouvements.
     */
    @Override
    public void supprimeLongueSequence(int longueurMax) {
        sequenceTouches.removeIf(mouvement -> mouvement.getLongueur() > longueurMax);
    }
}
