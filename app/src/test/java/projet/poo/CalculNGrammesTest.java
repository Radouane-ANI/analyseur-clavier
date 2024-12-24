package projet.poo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class CalculNGrammesTest {

    private CalculNGrammes calculNGrammes;
    private Map<String, Integer> ungramme;
    private Map<String, Integer> bigramme;
    private Map<String, Integer> trigramme;

    @BeforeEach
    public void setUp() {
        ungramme = new HashMap<>();
        bigramme = new HashMap<>();
        trigramme = new HashMap<>();
        calculNGrammes = new CalculNGrammes("src/test/resources/corpus/test.txt", ungramme, bigramme, trigramme);
    }

    @Test
    public void testConstructorWithValidArguments() {
        assertNotNull(calculNGrammes);
    }

    @Test
    public void testConstructorWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CalculNGrammes(null, ungramme, bigramme, trigramme);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CalculNGrammes("src/test/resources/corpus/test.txt", null, bigramme, trigramme);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CalculNGrammes("src/test/resources/corpus/test.txt", ungramme, null, trigramme);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CalculNGrammes("src/test/resources/corpus/test.txt", ungramme, bigramme, null);
        });
    }

    @Test
    public void testConstructorWithInvalidFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CalculNGrammes("invalid/path.txt", ungramme, bigramme, trigramme);
        });
    }

    @Test
    public void testRun() {
        calculNGrammes.run();
        assertEquals(3, ungramme.get("\n"));
        assertEquals(1, bigramme.get("\\\\")); // chaque \ est échappé par un autre \
        assertEquals(1, trigramme.get("abc"));
    }

    @Test
    public void testAnalyserTexteWithNullText() {
        calculNGrammes.analyserTexte(null);
        assertTrue(ungramme.isEmpty());
        assertTrue(bigramme.isEmpty());
        assertTrue(trigramme.isEmpty());
    }

    @Test
    public void testAnalyserTexteWithValidText() {
        String texte = "abcabc";
        calculNGrammes.analyserTexte(texte);
        assertEquals(2, ungramme.get("a"));
        assertEquals(2, ungramme.get("b"));
        assertEquals(2, ungramme.get("c"));
        assertEquals(2, bigramme.get("ab"));
        assertEquals(2, bigramme.get("bc"));
        assertEquals(2, trigramme.get("abc"));
    }

    @Test
    public void testGetTaileTexte() {
        String texte = "abc";
        calculNGrammes.analyserTexte(texte);
        assertEquals(3, calculNGrammes.getTaileTexte());
    }
}