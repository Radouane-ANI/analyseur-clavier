package projet.poo;

import java.util.ArrayList;
import java.util.List;

public interface Mouvement {

    int getLongueur();

    List<Touche> sequenceDeTouche();

    Touche get(int i);

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
