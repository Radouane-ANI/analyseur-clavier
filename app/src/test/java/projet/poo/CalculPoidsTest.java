package projet.poo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertThrows;



public class CalculPoidsTest {
    // @Test
    // public void testCalculLongeur1_CentralPosition_Index() {
    //     Touche touche = ToucheClavierFactory.create(0, 0, Geometry.ERGO); 
    
    //     Mouvement mouvement = SequenceToucheFactory.create(touche);
    
    //     CalculPoids calculPoids = new CalculPoids();
    
    //     double note = calculPoids.calculLongeur1(mouvement);
    
    //     assertTrue(note > 8.0, "La note devrait être élevée pour une touche centrale utilisée par l'index.");
    // }
    
    
    @Test
    public void testCalculLongeur1_UpperRow_Auriculaire() {

    Touche touche = ToucheClavierFactory.create(1, 5, Geometry.ERGO); // Ligne 1, colonne 5, main gauche

    Mouvement mouvement = SequenceToucheFactory.create(touche);

    CalculPoids calculPoids = new CalculPoids();

    double note = calculPoids.calculLongeur1(mouvement);

    assertTrue(note < 7.0, "La note devrait être faible pour une touche en rangée supérieure avec l'auriculaire.");
    }


    @Test
    public void testCalculLongeur1_InvalidMouvement() {
        
        Touche touche1 = ToucheClavierFactory.create(0, 0, Geometry.ERGO); // Ligne 0, colonne 0
        Touche touche2 = ToucheClavierFactory.create(1, 1, Geometry.ERGO); // Ligne 1, colonne 1
    
        
        Mouvement mouvement = SequenceToucheFactory.create(
            SequenceToucheFactory.create(touche1), 
            SequenceToucheFactory.create(touche2)
        );
    
        CalculPoids calculPoids = new CalculPoids();
    
       
        assertThrows(IllegalArgumentException.class, () -> {
            calculPoids.calculLongeur1(mouvement);
        });
    }
    
}
