package projet.poo.evaluateurdisposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface représentant un mouvement sur un clavier, composé d'une séquence de touches.
 * Permet de modéliser et manipuler des mouvements effectués sur le clavier.
 */
public interface Mouvement {

    /**
     * Retourne la longueur du mouvement, c'est-à-dire le nombre de touches dans la séquence.
     *
     * @return la longueur du mouvement.
     */
    int getLongueur();

    /**
     * Retourne la séquence complète des touches associées à ce mouvement.
     *
     * @return une liste de touches représentant le mouvement.
     */
    List<Touche> sequenceDeTouche();

    /**
     * Retourne une touche spécifique de la séquence, identifiée par un paramètre donné.
     *
     * @param i un entier représentant une position  pour accéder à une touche.
     *         
     * @return la touche associée au paramètre spécifié.
     * @throws IndexOutOfBoundsException si le paramètre dépasse les limites définies par la séquence.
     */
    Touche get(int i);

    /**
     * Fusionne deux listes de mouvements pour générer une nouvelle liste de mouvements combinés.
     * Chaque mouvement de la première liste est combiné avec chaque mouvement de la seconde liste
     * en utilisant la méthode {@code SequenceToucheFactory.create()}.
     *
     * @param mouvements  la première liste de mouvements.
     * @param mouvements2 la seconde liste de mouvements.
     * @return une liste de nouveaux mouvements résultant de la fusion.
     */
    public static List<Mouvement> fusionneMouvements(List<Mouvement> mouvements, List<Mouvement> mouvements2) {
        List<Mouvement> res = new ArrayList<>();
        for (Mouvement mouv : mouvements) {
            for (Mouvement mouv2 : mouvements2) {
                res.add(SequenceToucheFactory.create(mouv, mouv2));
            }
        }
        return res;
    }

}
