package projet.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.LectureDispositionClavier;
import projet.poo.evaluateurdisposition.Mouvement;

public class LectureDispositionClavierTest {

    @Test
    void testConstructorWithValidTOML() {
        String validPath = "src/test/resources/layout.toml";
        LectureDispositionClavier disposition = new LectureDispositionClavier(validPath);

        assertNotNull(disposition.getGeometrie(), "La géométrie ne devrait pas être null.");
        assertNotNull(disposition.getSequenceTouche(), "Les séquences de touches ne devraient pas être nulles.");
    }

    @Test
    void testConstructorAttributesInitialization() {
        String validPath = "src/test/resources/layout.toml";
        LectureDispositionClavier disposition = new LectureDispositionClavier(validPath);

        assertEquals(Geometry.ISO, disposition.getGeometrie(), "Le type de géométrie devrait être 'ISO'.");
        assertTrue(disposition.getSequenceTouche().containsKey("shift"),
                "Les touches spéciales devraient être initialisées.");
    }

    @Test
    void testConstructorWithNonExistentFile() {
        String invalidPath = "src/test/resources/nonexistent.toml";
        assertThrows(IllegalArgumentException.class, () -> new LectureDispositionClavier(invalidPath),
                "Un fichier inexistant devrait lever une IllegalArgumentException.");
    }

    @Test
    void testInitToucheDeBase() {
        String validPath = "src/test/resources/layout.toml";
        LectureDispositionClavier disposition = new LectureDispositionClavier(validPath);

        Map<String, List<Mouvement>> sequenceTouche = disposition.getSequenceTouche();

        assertTrue(sequenceTouche.containsKey("shift"), "La touche shift devrait être initialisée.");
        assertTrue(sequenceTouche.containsKey("altgr"), "La touche altgr devrait être initialisée.");
        assertTrue(sequenceTouche.containsKey(" "), "La barre d'espace devrait être initialisée.");
        assertTrue(sequenceTouche.containsKey("\n"), "La touche Enter devrait être initialisée.");
        assertTrue(sequenceTouche.containsKey("\t"), "La touche Tab devrait être initialisée.");
    }

    @Test
    void testAnalyseDispositionBase() {
        String basePath = "src/test/resources/layout.toml";
        LectureDispositionClavier disposition = new LectureDispositionClavier(basePath);
        disposition.analyseDisposition();

        assertTrue(disposition.getSequenceTouche().containsKey("a"),
                "La touche 'a' devrait être analysée et ajoutée à sequenceTouche.");
        assertTrue(disposition.getSequenceTouche().containsKey("A"),
                "La touche 'A' (majuscule) devrait être analysée et ajoutée.");
    }

    @Test
    void testAnalyseDispositionWithDeadKeys() {
        String deadKeysPath = "src/test/resources/layout.toml";
        LectureDispositionClavier disposition = new LectureDispositionClavier(deadKeysPath);
        disposition.analyseDisposition();

        assertTrue(disposition.getSequenceTouche().containsKey("é"),
                "Les séquences générées avec les touches mortes devraient être ajoutées.");
    }
    @Test
    void testAnalyseDispositionWithCustomDeadKeys() {
        String deadKeysPath = "src/test/resources/layout.toml";
        LectureDispositionClavier disposition = new LectureDispositionClavier(deadKeysPath);
        disposition.analyseDisposition();

        assertTrue(disposition.getSequenceTouche().containsKey("è"),
                "Les séquences générées avec les touches mortes devraient être ajoutées.");
    }

}
