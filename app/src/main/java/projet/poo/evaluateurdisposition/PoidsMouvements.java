package projet.poo.evaluateurdisposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface représentant les calculs de poids  associés aux mouvements effectués sur un clavier.
 * Elle permet de définir des méthodes pour évaluer l'efficacité et la difficulté de mouvements de différentes longueurs,
 * ainsi que pour calculer les fréquences des mouvements.
 */
public interface PoidsMouvements {

    /**
     * Calcule le poids d'un mouvement de longueur 1.
     *
     * @param m le mouvement à évaluer, doit être de longueur 1.
     * @return un score représentant l'efficacité du mouvement.
     */
    double calculLongeur1(Mouvement m);

    /**
     * Calcule le poids d'un mouvement de longueur 2.
     *
     * @param m le mouvement à évaluer, doit être de longueur 2.
     * @return un score représentant l'efficacité du mouvement.
     */
    double calculLongeur2(Mouvement m);

     /**
     * Calcule le poids d'un mouvement de longueur 3.
     *
     * @param m le mouvement à évaluer, doit être de longueur 3.
     * @return un score représentant l'efficacité du mouvement.
     */
    double calculLongeur3(Mouvement m);

    /**
     * Calcule les poids des mouvements à partir d'une liste d'objets {@code INgram}.
     * Chaque poids est pondéré par la valeur associée à l'objet {@code INgram}.
     *
     * @param n la liste des {@code INgram} contenant les séquences de mouvements à évaluer.
     * @return une liste de poids, un pour chaque mouvement dans les séquences.
     */
    default List<Double> calculerPoids(List<INgram> n) {
        List<Double> res = new ArrayList<>();
        for (INgram ngram : n) {
            for (Mouvement mouvement : ngram.getSequenceTouches()) {
                switch (mouvement.getLongueur()) {
                    case 1:
                        res.add(calculLongeur1(mouvement) * ngram.getValeur());
                        break;
                    case 2:
                        res.add(calculLongeur2(mouvement) * ngram.getValeur());
                        break;
                    case 3:
                        res.add(calculLongeur3(mouvement) * ngram.getValeur());
                        break;
                    default:
                        res.add(-1.0);
                }
            }

        }
        return res;
    }

    /**
     * Met à jour les fréquences des mouvements de longueur 1 dans une liste de mouvements donnée.
     * Cette méthode peut être utilisée pour analyser la distribution des doigts et des mains utilisés.
     *
     * @param mouv la liste des mouvements à analyser.
     */
    void fequence1Gram(List<Mouvement> mouv);
}