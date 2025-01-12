package projet.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.Mouvement;
import projet.poo.evaluateurdisposition.SequenceToucheFactory;
import projet.poo.evaluateurdisposition.Touche;
import projet.poo.evaluateurdisposition.ToucheClavierFactory;

public class SequenceToucheFactoryTest {

    @Test
    public void testCreateAvecTouche() {
        Touche touche = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Mouvement mouvement = SequenceToucheFactory.create(touche);

        assertEquals(1, mouvement.getLongueur());
        assertEquals(touche, mouvement.get(0));
    }

    @Test
    public void testCreateAvecTouche_Null() {
        assertThrows(IllegalArgumentException.class, () -> SequenceToucheFactory.create((Touche) null));
    }

    @Test
    public void testCreateAvecMouvement() {
        Touche touche = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Mouvement mouvementInitial = SequenceToucheFactory.create(touche);

        Mouvement mouvement = SequenceToucheFactory.create(mouvementInitial);

        assertEquals(1, mouvement.getLongueur());
        assertEquals(touche, mouvement.get(0));
    }

    @Test
    public void testCreateAvecMouvement_Null() {
        assertThrows(IllegalArgumentException.class, () -> SequenceToucheFactory.create((Mouvement) null));
    }

    @Test
    public void testCreateAvecMouvementEtTouche() {
        Touche touche1 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Touche touche2 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Mouvement mouvementInitial = SequenceToucheFactory.create(touche1);

        Mouvement mouvement = SequenceToucheFactory.create(mouvementInitial, touche2);

        assertEquals(2, mouvement.getLongueur());
        assertEquals(touche1, mouvement.get(0));
        assertEquals(touche2, mouvement.get(1));
    }

    @Test
    public void testCreateAvecDeuxMouvements() {
        Touche touche1 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Touche touche2 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Touche touche3 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);

        Mouvement mouvement1 = SequenceToucheFactory.create(touche1);
        Mouvement mouvement2 = SequenceToucheFactory.create(touche2);

        Mouvement mouvement = SequenceToucheFactory.create(mouvement1, mouvement2);
        mouvement = SequenceToucheFactory.create(mouvement, SequenceToucheFactory.create(touche3));

        assertEquals(3, mouvement.getLongueur());
        assertEquals(touche1, mouvement.get(0));
        assertEquals(touche2, mouvement.get(1));
        assertEquals(touche3, mouvement.get(2));
    }

    @Test
    public void testSequenceTouche_GetLongueur() {
        Touche touche1 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Touche touche2 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);

        Mouvement mouvement = SequenceToucheFactory.create(SequenceToucheFactory.create(touche1), touche2);

        assertEquals(2, mouvement.getLongueur());
    }

    @Test
    public void testSequenceTouche_Get() {
        Touche touche1 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Touche touche2 = ToucheClavierFactory.create(1, 1, Geometry.ABNT);

        Mouvement mouvement = SequenceToucheFactory.create(SequenceToucheFactory.create(touche1), touche2);

        assertEquals(touche1, mouvement.get(0));
        assertEquals(touche2, mouvement.get(1));
    }

    @Test
    public void testSequenceTouche_Get_IndexHorsLimites() {
        Touche touche = ToucheClavierFactory.create(1, 1, Geometry.ABNT);
        Mouvement mouvement = SequenceToucheFactory.create(touche);

        assertThrows(IndexOutOfBoundsException.class, () -> mouvement.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> mouvement.get(1));
    }
}
