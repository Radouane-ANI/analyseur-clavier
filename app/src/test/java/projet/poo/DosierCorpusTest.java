package projet.poo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projet.poo.analyseurfrequence.DosierCorpus;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DosierCorpusTest {

    private DosierCorpus dosierCorpus;

    @BeforeEach
    public void setUp() {
        dosierCorpus = new DosierCorpus("src/test/resources/corpus");
    }

    @Test
    public void testConstructorWithValidPath() {
        assertNotNull(dosierCorpus);
    }

    @Test
    public void testConstructorWithNullPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DosierCorpus(null);
        });
    }

    @Test
    public void testConstructorWithInvalidPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DosierCorpus("invalid/path");
        });
    }

    @Test
    public void testObtenirTextes() {
        List<String> textes = dosierCorpus.obtenirTextes();
        assertNotNull(textes);
        assertFalse(textes.isEmpty());
        assertEquals(1, textes.size());
        assertEquals("src/test/resources/corpus/test.txt", textes.get(0));
    }

    @Test
    public void testAjouterTexteWithValidFile() {
        String newTextFilePath = "src/test/resources/test2.txt";
        dosierCorpus.ajouterTexte(newTextFilePath);
        List<String> textes = dosierCorpus.obtenirTextes();
        assertTrue(textes.contains(newTextFilePath));
    }

    @Test
    public void testAjouterTexteWithInvalidFile() {
        String invalidFilePath = "src/test/resources/corpus/invalid_file.doc";
        dosierCorpus.ajouterTexte(invalidFilePath);
        List<String> textes = dosierCorpus.obtenirTextes();
        assertFalse(textes.contains(invalidFilePath));
    }

    @Test
    public void testAjouterTexteWithNull() {
        dosierCorpus.ajouterTexte(null);
        List<String> textes = dosierCorpus.obtenirTextes();
        assertFalse(textes.contains(null));
    }
}