package projet.poo;

import java.util.List;

public interface Mouvement {
    
    int getLongueur();

    List<Touche> getMouvement();

    Touche get(int i);
}
