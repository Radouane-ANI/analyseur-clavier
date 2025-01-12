package projet.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.INgram;
import projet.poo.evaluateurdisposition.LectureEtTraitementCSV;
import projet.poo.evaluateurdisposition.Mouvement;
import projet.poo.evaluateurdisposition.SequenceToucheFactory;
import projet.poo.evaluateurdisposition.Touche;
import projet.poo.evaluateurdisposition.ToucheClavierFactory;

public class LectureEtTraitementCSVTest {

    private final LectureEtTraitementCSV lecteur = new LectureEtTraitementCSV();

    /**
     * Crée un fichier CSV temporaire pour les tests.
     */
    private File creerFichierCSV(String contenu) throws IOException {
        File fichierTemp = File.createTempFile("test", ".csv");
        fichierTemp.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierTemp))) {
            writer.write(contenu);
        }
        return fichierTemp;
    }

    @Test
    public void testTailleCorpusCSV_CorpusValide() throws IOException {
        String contenu = """
                header,100
                type,ngramme,valeur
                Bigramme,ab,10
                """;
        File fichierCSV = creerFichierCSV(contenu);

        int tailleCorpus = lecteur.tailleCorpusCSV(fichierCSV.getAbsolutePath());

        assertEquals(100, tailleCorpus, "La taille du corpus extraite doit être correcte.");
    }

    @Test
    public void testTailleCorpusCSV_FichierMalFormate() throws IOException {
        String contenu = """
                header
                type,ngramme,valeur
                Bigramme,ab,10
                """;
        File fichierCSV = creerFichierCSV(contenu);

        assertThrows(IllegalArgumentException.class,
                () -> lecteur.tailleCorpusCSV(fichierCSV.getAbsolutePath()),
                "Le fichier mal formaté doit lever une exception.");
    }

    @Test
    public void testTraitementCSV_LectureValide() throws IOException {
        String contenu = """
                header,100
                type,ngramme,valeur
                Bigramme,ab,10
                Trigramme,abc,20
                Ungramme,a,5
                """;
        File fichierCSV = creerFichierCSV(contenu);

        Touche touche1 = ToucheClavierFactory.create(3, 4, Geometry.ERGO);
        Mouvement mouvement = SequenceToucheFactory.create(touche1);

        Map<String, List<Mouvement>> dispositionClavier = new HashMap<>();
        dispositionClavier.put("a", List.of(mouvement));
        dispositionClavier.put("b", List.of(mouvement));
        dispositionClavier.put("c", List.of(mouvement));

        List<INgram> result = lecteur.traiterCSV(fichierCSV.getAbsolutePath(), dispositionClavier);

        assertEquals(3, result.size(), "Le fichier doit générer 3 n-grammes.");
    }

    @Test
    public void testTraitementCSV_NgrammeInvalide() throws IOException {
        String contenu = """
                header,100
                type,ngramme,valeur
                Bigramme,ab,10
                Trigramme,,20
                Ungramme,a,X
                """;
        File fichierCSV = creerFichierCSV(contenu);

        Touche touche1 = ToucheClavierFactory.create(3, 4, Geometry.ERGO);
        Mouvement mouvement = SequenceToucheFactory.create(touche1);

        Map<String, List<Mouvement>> dispositionClavier = new HashMap<>();
        dispositionClavier.put("a", List.of(mouvement));
        dispositionClavier.put("b", List.of(mouvement));

        assertThrows(IllegalArgumentException.class,
                () -> lecteur.traiterCSV(fichierCSV.getAbsolutePath(), dispositionClavier),
                "Les n-grammes invalides doivent lever une exception.");
    }

    @Test
    public void testTraitementCSV_FiltrageLonguesSequences() throws IOException {
        String contenu = """
                header,100
                type,ngramme,valeur
                Bigramme,ab,10
                Trigramme,abcd,20
                """;
        File fichierCSV = creerFichierCSV(contenu);

        Touche touche1 = ToucheClavierFactory.create(3, 4, Geometry.ERGO);
        Mouvement mouvement = SequenceToucheFactory.create(touche1);

        Map<String, List<Mouvement>> dispositionClavier = new HashMap<>();
        dispositionClavier.put("a", List.of(mouvement));
        dispositionClavier.put("b", List.of(mouvement));
        dispositionClavier.put("c", List.of(mouvement));
        dispositionClavier.put("d", List.of(mouvement));

        List<INgram> result = lecteur.traiterCSV(fichierCSV.getAbsolutePath(), dispositionClavier);

        assertEquals(1, result.size(), "Les n-grammes ayant une séquence > 3 doivent être supprimés.");
    }
}
