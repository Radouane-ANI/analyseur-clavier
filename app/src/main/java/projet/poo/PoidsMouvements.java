package projet.poo;

import java.util.ArrayList;
import java.util.List;

public interface PoidsMouvements {

    double calculLongeur1(Mouvement m);

    double calculLongeur2(Mouvement m);

    double calculLongeur3(Mouvement m);

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

    void fequence1Gram(List<Mouvement> mouv);
}