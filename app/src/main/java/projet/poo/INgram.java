package projet.poo;

import java.util.List;

/**
 * Représente une interface pour les n-grammes, qui sont des séquences
 * de caractères utilisées pour modéliser des structures de texte.
 * Fournit des méthodes pour récupérer les informations sur l'n-gramme,
 * calculer son coût en termes de touches, et obtenir la séquence de touches
 * associées sur un clavier.
 */
public interface INgram {

    /**
     * Retourne le type de l'n-gramme.
     *
     * @return Le type de l'n-gramme (par exemple, "bigramme", "trigramme").
     */
    String getType();
    
    /**
     * Retourne la séquence de caractères représentant l'n-gramme.
     *
     * @return La séquence de caractères de l'n-gramme.
     */
    String getNgramme();

    /**
     * Retourne la valeur associée à cet n-gramme.
     *
     * @return La valeur associée (sa fréquence).
     */
    int getValeur();

/**
     * Calcule le coût d'utilisation des touches nécessaires pour saisir cet n-gramme.
     *
     * @return Le coût calculé basé sur le nombre de touches nécessaires pour cet n-gramme.
     */    
    int calculerCoutTouches();

    /**
     * Retourne la séquence de touches nécessaires pour saisir cet n-gramme.
     *
     * @return Les differentes liste des touches nécessaires pour cet n-gramme.
     */
    List<Mouvement> getSequenceTouches();

    /**
     * Retourne la plus petite séquence de touches nécessaires pour saisir cet n-gramme,
     *
     * @return Le Mouvement minimales nécessaires pour cet n-gramme.
     */
    Mouvement getSequenceTouchesMin();

    /**
     * Supprime les séquences dont la longueur dépasse une certaine limite.
     *
     * @param longueurMax la longueur maximale autorisée pour les séquences
     */
    void supprimeLongueSequence(int longueurMax);
    
}
