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

    public SequenceTouche(Mouvement mouvement, Touche touche) {
        if (mouvement == null) {
            throw new IllegalArgumentException("La touche ne peut pas être null.");
        }
        this.sequenceDeTouche = mouvement.getMouvement();
        this.sequenceDeTouche.add(touche);
    }
    public SequenceTouche(Mouvement mouvement, Mouvement mouvement2) {
        if (mouvement == null || mouvement2 == null) {
            throw new IllegalArgumentException("La touche ne peut pas être null.");
        }
        this.sequenceDeTouche = mouvement.getMouvement();
        this.sequenceDeTouche.addAll(mouvement2.getMouvement());
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

    @Override
    public Touche get(int i) {
        if (sequenceDeTouche.size() > i) {
            return sequenceDeTouche.get(i);
        } else {
            throw new IllegalArgumentException("L'index est trop grand.");
        }
    }
}
