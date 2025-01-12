package projet.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.Doigts;
import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.Touche;
import projet.poo.evaluateurdisposition.ToucheClavierFactory;

public class ToucheClavierFactoryTest {

    @Test
    public void testCreate_ToucheValide() {
        Geometry geometrieClavier = Geometry.ANSI; // Assurez-vous que cette classe est bien définie
        Touche touche = ToucheClavierFactory.create(2, 5, geometrieClavier);

        assertNotNull(touche, "La touche ne doit pas être null.");
        assertEquals(2, touche.ligne(), "La ligne doit être correcte.");
        assertEquals(5, touche.colonne(), "La colonne doit être correcte.");
    }

    @Test
    public void testCreate_LigneNegative() {
        Geometry geometrieClavier = Geometry.ANSI;
        assertThrows(IllegalArgumentException.class, () -> ToucheClavierFactory.create(-1, 5, geometrieClavier),
                "Une ligne négative doit lever une exception.");
    }

    @Test
    public void testCreate_ColonneNegative() {
        Geometry geometrieClavier = Geometry.ANSI;
        assertThrows(IllegalArgumentException.class, () -> ToucheClavierFactory.create(2, -1, geometrieClavier),
                "Une colonne négative doit lever une exception.");
    }

    @Test
    public void testCreate_GeometrieNull() {
        assertThrows(IllegalArgumentException.class, () -> ToucheClavierFactory.create(2, 5, null),
                "Une géométrie null doit lever une exception.");
    }

    @Test
    public void testIsMainsDroite_MainDroite() {
        Touche t = ToucheClavierFactory.create(1, 8, Geometry.ERGO);

        assertTrue(t.mainsDroite(), "La touche doit être utilisée par la main droite.");
    }

    @Test
    public void testIsMainsDroite_MainGauche() {
        Touche t = ToucheClavierFactory.create(1, 4, Geometry.ERGO);

        assertFalse(t.mainsDroite(), "La touche doit être utilisée par la main gauche.");
    }

    @Test
    public void testGetDoigt_Index() {
        Touche t = ToucheClavierFactory.create(1, 5, Geometry.ERGO);

        assertEquals(Doigts.INDEX, t.doigt(), "Le doigt associé doit être l'index.");
    }

    @Test
    public void testGetDoigt_Majeur() {
        Touche t = ToucheClavierFactory.create(1, 4, Geometry.ERGO);

        assertEquals(Doigts.MAJEUR, t.doigt(), "Le doigt associé doit être le majeur.");
    }

}
