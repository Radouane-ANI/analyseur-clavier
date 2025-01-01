package projet.poo;

import java.util.ArrayList;
import java.util.List;

public class SequenceToucheFactory {

    public static Mouvement create(Touche touche) {
        if (touche == null) {
            throw new IllegalArgumentException("La touche ne peut pas être null.");
        }
        List<Touche> sequence = new ArrayList<>();
        sequence.add(touche);
        return new SequenceTouche(sequence);
    }

    public static Mouvement create(Mouvement mouvement) {
        if (mouvement == null) {
            throw new IllegalArgumentException("Le mouvement ne peut pas être null.");
        }
        return new SequenceTouche(mouvement.sequenceDeTouche());
    }

    public static Mouvement create(Mouvement mouvement, Touche touche) {
        if (mouvement == null || touche == null) {
            throw new IllegalArgumentException("Le mouvement et la touche ne peuvent pas être null.");
        }
        List<Touche> sequence = new ArrayList<>(mouvement.sequenceDeTouche());
        sequence.add(touche);
        return new SequenceTouche(sequence);
    }

    public static Mouvement create(Mouvement mouvement, Mouvement mouvement2) {
        if (mouvement == null || mouvement2 == null) {
            throw new IllegalArgumentException("Les mouvements ne peuvent pas être null.");
        }
        List<Touche> sequence = new ArrayList<>(mouvement.sequenceDeTouche());
        sequence.addAll(mouvement2.sequenceDeTouche());
        return new SequenceTouche(sequence);
    }

    private record SequenceTouche(List<Touche> sequenceDeTouche) implements Mouvement {
        public int getLongueur() {
            return sequenceDeTouche.size();
        }

        public Touche get(int i) {
            if (i < 0 || i >= sequenceDeTouche.size()) {
                throw new IndexOutOfBoundsException("L'index est hors des limites.");
            }
            return sequenceDeTouche.get(i);
        }

    }
}
