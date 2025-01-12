package projet.poo;

import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.CalculPoids;
import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.Mouvement;
import projet.poo.evaluateurdisposition.SequenceToucheFactory;
import projet.poo.evaluateurdisposition.Touche;
import projet.poo.evaluateurdisposition.ToucheClavierFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CalculPoidsTest {
    @Test
    public void testCalculLongeur1_CentralPosition_Index() {
        Touche touche = ToucheClavierFactory.create(3, 6, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur1(mouvement);
        assertTrue(note > 0.5, "La note devrait être élevée pour une touche centrale utilisée par l'index.");
    }

    @Test
    public void testCalculLongeur1_UpperRow_Auriculaire() {

        Touche touche = ToucheClavierFactory.create(1, 1, Geometry.ERGO); // Ligne 1, colonne 1, main gauche

        Mouvement mouvement = SequenceToucheFactory.create(touche);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur1(mouvement);

        assertTrue(note < 0.5, "La note devrait être faible pour une touche en rangée supérieure avec l'auriculaire.");
    }

    @Test
    public void testCalculLongeur1_InvalidMouvement() {

        Touche touche1 = ToucheClavierFactory.create(0, 0, Geometry.ERGO); // Ligne 0, colonne 0
        Touche touche2 = ToucheClavierFactory.create(1, 1, Geometry.ERGO); // Ligne 1, colonne 1

        Mouvement mouvement = SequenceToucheFactory.create(
                SequenceToucheFactory.create(touche1),
                SequenceToucheFactory.create(touche2));

        CalculPoids calculPoids = new CalculPoids();

        assertThrows(IllegalArgumentException.class, () -> {
            calculPoids.calculLongeur1(mouvement);
        });
    }

    @Test
    public void testFequence1Gram() {
        Touche touche1 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);
        Touche touche3 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);

        Mouvement mouvement1 = SequenceToucheFactory.create(touche1);
        Mouvement mouvement2 = SequenceToucheFactory.create(touche2);
        Mouvement mouvement3 = SequenceToucheFactory.create(touche3);

        List<Mouvement> mouvements = List.of(mouvement1, mouvement2, mouvement3);

        CalculPoids calculPoids = new CalculPoids();
        calculPoids.fequence1Gram(mouvements);

        double note = calculPoids.calculLongeur1(mouvement1);

        assertTrue(note < 0.5, "La note devrait être faible pour une touche suremployée.");
    }

    @Test
    public void testCalculLongeur2_SameFinger() {
        Touche touche1 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(mouvement, touche2);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur2(mouvement1);
        assertTrue(note <= 0.75, "La note devrait être faible pour un mouvement utilisant le même doigt.");
    }

    @Test
    public void testCalculLongeur2_AlternatingHands() {
        Touche touche1 = ToucheClavierFactory.create(3, 7, Geometry.ERGO); // Main droite
        Touche touche2 = ToucheClavierFactory.create(3, 4, Geometry.ERGO); // Main gauche

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(mouvement, touche2);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur2(mouvement1);
        assertTrue(note > 0.5, "La note devrait être élevée pour une alternance entre les mains.");
    }

    @Test
    public void testCalculLongeur2_ScissorMovement() {
        Touche touche1 = ToucheClavierFactory.create(1, 3, Geometry.ERGO); // Main gauche
        Touche touche2 = ToucheClavierFactory.create(4, 4, Geometry.ERGO); // Main gauche, colonne éloignée

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(mouvement, touche2);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur2(mouvement1);
        assertTrue(note <= 0.75, "La note devrait être faible pour un mouvement en ciseaux.");
    }

    @Test
    public void testCalculLongeur3_GoodSequence() {
        Touche touche1 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 5, Geometry.ERGO);
        Touche touche3 = ToucheClavierFactory.create(3, 4, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(mouvement, touche2);
        Mouvement mouvement2 = SequenceToucheFactory.create(mouvement1, touche3);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur3(mouvement2);
        assertTrue(note > 0.5, "La note devrait être élevée pour une bonne séquence.");
    }

    @Test
    public void testCalculLongeur3_BadRedirection() {
        Touche touche1 = ToucheClavierFactory.create(3, 1, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 5, Geometry.ERGO);
        Touche touche3 = ToucheClavierFactory.create(3, 2, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(mouvement, touche2);
        Mouvement mouvement2 = SequenceToucheFactory.create(mouvement1, touche3);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur3(mouvement2);
        assertTrue(note <= 0.6, "La note devrait être faible pour une mauvaise redirection.");
    }

    @Test
    public void testCalculLongeur3_Skipgram() {
        Touche touche1 = ToucheClavierFactory.create(3, 3, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 8, Geometry.ERGO);
        Touche touche3 = ToucheClavierFactory.create(3, 3, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(mouvement, touche2);
        Mouvement mouvement2 = SequenceToucheFactory.create(mouvement1, touche3);

        CalculPoids calculPoids = new CalculPoids();

        double note = calculPoids.calculLongeur3(mouvement2);
        assertTrue(note <= 0.6, "La note devrait être faible pour un skipgram.");
    }

}
