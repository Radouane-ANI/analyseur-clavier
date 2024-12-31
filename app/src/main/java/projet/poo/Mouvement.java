package projet.poo;

import java.util.List;

public interface Mouvement {
    
    int getLongueur();

    List<Touche> sequenceDeTouche();

    Touche get(int i);
}
