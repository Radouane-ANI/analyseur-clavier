package projet.poo;

import java.util.ArrayList;
import java.util.List;

public class SequenceTouche implements Mouvement {
    private final List<Touche> sequenceDeTouche;

    public SequenceTouche(Touche touche) {
        if (touche == null) {
            throw new IllegalArgumentException("La touche ne peut pas être null.");
        }
        this.sequenceDeTouche = new ArrayList<>();
        this.sequenceDeTouche.add(touche);
    }

    public SequenceTouche(List<Touche> sequenceDeTouche) {
        if (sequenceDeTouche == null) {
            throw new IllegalArgumentException("La touche ne peut pas être null.");
        }
        this.sequenceDeTouche = new ArrayList<>(sequenceDeTouche);
    }

    @Override
    public int getLongueur() {
        return sequenceDeTouche.size();
    }

    @Override
    public List<Touche> getMouvement() {
        return new ArrayList<>(sequenceDeTouche);
    }

    @Override
    public String toString() {
        return sequenceDeTouche + "\n";
    }
}
