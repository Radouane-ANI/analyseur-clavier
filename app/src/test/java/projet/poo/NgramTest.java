package projet.poo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.Mouvement;
import projet.poo.evaluateurdisposition.Ngram;
import projet.poo.evaluateurdisposition.SequenceToucheFactory;
import projet.poo.evaluateurdisposition.Touche;
import projet.poo.evaluateurdisposition.ToucheClavierFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe Ngram.
 */
public class NgramTest {
    private Map<String, List<Mouvement>> dispositionClavier;

    @BeforeEach
    public void setUp() {
        // Initialisation des mouvements pour le clavier
        Touche touche1 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 5, Geometry.ERGO);
        Touche touche3 = ToucheClavierFactory.create(3, 4, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(touche2);
        Mouvement mouvement2 = SequenceToucheFactory.create(touche3);

        // Configuration de la disposition du clavier
        dispositionClavier = new HashMap<>();
        dispositionClavier.put("a", List.of(mouvement));
        dispositionClavier.put("b", List.of(mouvement1));
        dispositionClavier.put("c", List.of(mouvement2));
    }

    @Test
    public void testConstructeur_CasNormal() {
        Ngram ngram = new Ngram("abc", 10, dispositionClavier);

        assertEquals("abc", ngram.getNgramme());
        assertEquals(10, ngram.getValeur());
        assertNotNull(ngram.getSequenceTouches());
    }

    @Test
    public void testConstructeur_CaractereInexistant_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Ngram("xyz", 5, dispositionClavier));
        assertTrue(exception.getMessage().contains("Le caractère"));
    }

    @Test
    public void testGetType_Unigramme() {
        Ngram ngram = new Ngram("a", 1, dispositionClavier);
        assertEquals("unigramme", ngram.getType());
    }

    @Test
    public void testGetType_Bigramme() {
        Ngram ngram = new Ngram("ab", 2, dispositionClavier);
        assertEquals("bigramme", ngram.getType());
    }

    @Test
    public void testGetType_Trigramme() {
        Ngram ngram = new Ngram("abc", 3, dispositionClavier);
        assertEquals("trigramme", ngram.getType());
    }

    @Test
    public void testGetType_NgrammePlusGrand() {
        Ngram ngram = new Ngram("abcabc", 6, dispositionClavier);
        assertEquals("6gramme", ngram.getType());
    }

    @Test
    public void testSupprimeLongueSequence() {
        Ngram ngram = new Ngram("abc", 10, dispositionClavier);

        ngram.supprimeLongueSequence(1); // Suppression des mouvements ayant une longueur > 1
        assertTrue(ngram.getSequenceTouches().stream().allMatch(m -> m.getLongueur() <= 1));
    }

    @Test
    public void testGetSequenceTouchesMin_CasNormal() {
        Ngram ngram = new Ngram("abc", 10, dispositionClavier);

        Mouvement min = ngram.getSequenceTouchesMin();
        assertNotNull(min);
        assertEquals(3, min.getLongueur());
    }

    @Test
    public void testGetSequenceTouchesMin_SequenceVide() {
        assertThrows(IllegalArgumentException.class, () -> new Ngram("a", 10, new HashMap<>()));
    }

    @Test
    public void testSequenceTouchesImmuable() {
        Ngram ngram = new Ngram("abc", 10, dispositionClavier);

        List<Mouvement> original = ngram.getSequenceTouches();
        original.remove(0); // Modification de la copie
        assertEquals(3, ngram.getSequenceTouchesMin().getLongueur()); // L'original reste intact
    }
}
