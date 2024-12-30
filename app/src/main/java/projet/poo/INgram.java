package projet.poo;

import java.util.List;
import java.util.Map;

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
     * @param dispositionClavier Une carte associant chaque caractère à une liste de touches.
     * @return Le coût calculé basé sur le nombre de touches nécessaires pour cet n-gramme.
     */    
    int calculerCoutTouches(Map<String, List<Touche>> dispositionClavier);

    /**
     * Retourne la séquence de touches nécessaires pour saisir cet n-gramme.
     *
     * @param dispositionClavier Une carte associant chaque caractère à une liste de touches.
     * @return La liste des touches nécessaires pour cet n-gramme.
     */
    List<Touche> getSequenceTouches(Map<String, List<Touche>> dispositionClavier);
    
}
