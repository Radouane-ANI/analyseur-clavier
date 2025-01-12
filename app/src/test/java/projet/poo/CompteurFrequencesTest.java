package projet.poo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projet.poo.analyseurfrequence.CompteurFrequences;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class CompteurFrequencesTest {

    private CompteurFrequences compteurFrequences;

    @BeforeEach
    public void setUp() {
        compteurFrequences = new CompteurFrequences("src/test/resources/corpus");
    }

    @Test
    public void testCalculerFrequences() {
        compteurFrequences.calculerFrequences();

        Map<String, Integer> ungramme = compteurFrequences.getUngramme();
        Map<String, Integer> bigramme = compteurFrequences.getBigramme();
        Map<String, Integer> trigramme = compteurFrequences.getTrigramme();

        assertNotNull(ungramme);
        assertNotNull(bigramme);
        assertNotNull(trigramme);

        assertFalse(ungramme.isEmpty());
        assertFalse(bigramme.isEmpty());
        assertFalse(trigramme.isEmpty());

        assertEquals(3, ungramme.get("a"));
        assertEquals(2, bigramme.get("ab"));
        assertEquals(1, trigramme.get("abc"));
    }

    @Test
    public void testGetUngramme() {
        compteurFrequences.calculerFrequences();
        Map<String, Integer> ungramme = compteurFrequences.getUngramme();
        assertNotNull(ungramme);
        assertFalse(ungramme.isEmpty());
    }

    @Test
    public void testGetBigramme() {
        compteurFrequences.calculerFrequences();
        Map<String, Integer> bigramme = compteurFrequences.getBigramme();
        assertNotNull(bigramme);
        assertFalse(bigramme.isEmpty());
    }

    @Test
    public void testGetTrigramme() {
        compteurFrequences.calculerFrequences();
        Map<String, Integer> trigramme = compteurFrequences.getTrigramme();
        assertNotNull(trigramme);
        assertFalse(trigramme.isEmpty());
    }

    @Test
    public void testGetTailleTotal() {
        compteurFrequences.calculerFrequences();
        assertEquals(16, compteurFrequences.getTailleTotal());
    }

    @Test
    public void testEmptyCorpus() {
        CompteurFrequences emptyCompteur = new CompteurFrequences("src/test/resources/empty_corpus");
        emptyCompteur.calculerFrequences();

        Map<String, Integer> ungramme = emptyCompteur.getUngramme();
        Map<String, Integer> bigramme = emptyCompteur.getBigramme();
        Map<String, Integer> trigramme = emptyCompteur.getTrigramme();

        assertTrue(ungramme.isEmpty());
        assertTrue(bigramme.isEmpty());
        assertTrue(trigramme.isEmpty());
        assertEquals(0, emptyCompteur.getTailleTotal());
    }

    @Test
    public void testInvalidCorpusPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CompteurFrequences("invalid/path");
        });
    }
}