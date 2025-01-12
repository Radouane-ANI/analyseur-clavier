package projet.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import projet.poo.evaluateurdisposition.CalculNoteDisposition;
import projet.poo.evaluateurdisposition.Geometry;
import projet.poo.evaluateurdisposition.INgram;
import projet.poo.evaluateurdisposition.Mouvement;
import projet.poo.evaluateurdisposition.Ngram;
import projet.poo.evaluateurdisposition.SequenceToucheFactory;
import projet.poo.evaluateurdisposition.Touche;
import projet.poo.evaluateurdisposition.ToucheClavierFactory;

public class CalculNoteDispositionTest {

    @Test
    public void testNote_TailleCorpusZero() {
        CalculNoteDisposition calculateur = new CalculNoteDisposition();
        List<INgram> ngrams = List.of(); // Liste vide de n-grammes

        double resultat = calculateur.note(ngrams, 0);

        assertEquals(0, resultat, "Si la taille du corpus est 0, la note doit être 0.");
    }

    @Test
    public void testNote_TailleCorpusNegative() {
        CalculNoteDisposition calculateur = new CalculNoteDisposition();
        List<INgram> ngrams = List.of(); // Liste vide de n-grammes

        double resultat = calculateur.note(ngrams, -5);

        assertEquals(0, resultat, "Si la taille du corpus est négative, la note doit être 0.");
    }

    @Test
    public void testNote_ListeVide() {
        CalculNoteDisposition calculateur = new CalculNoteDisposition();
        List<INgram> ngrams = List.of(); // Liste vide de n-grammes

        double resultat = calculateur.note(ngrams, 100);

        assertEquals(0, resultat, "Si la liste de n-grammes est vide, la note doit être 0.");
    }

    @Test
    public void testNote_NGramsAvecPoids() {
        CalculNoteDisposition calculateur = new CalculNoteDisposition();
        Touche touche1 = ToucheClavierFactory.create(3, 6, Geometry.ERGO);
        Touche touche2 = ToucheClavierFactory.create(3, 5, Geometry.ERGO);
        Touche touche3 = ToucheClavierFactory.create(3, 4, Geometry.ERGO);

        Mouvement mouvement = SequenceToucheFactory.create(touche1);
        Mouvement mouvement1 = SequenceToucheFactory.create(touche2);
        Mouvement mouvement2 = SequenceToucheFactory.create(touche3);

        Map<String, List<Mouvement>> dispositionClavier = new HashMap<>();
        dispositionClavier.put("a", List.of(mouvement));
        dispositionClavier.put("b", List.of(mouvement1));
        dispositionClavier.put("c", List.of(mouvement2));

        // Mock des INgram avec une séquence et une valeur
        INgram ngram1 = new Ngram("ab", 5, dispositionClavier); // Poids simulé : 5
        INgram ngram2 = new Ngram("c", 10, dispositionClavier); // Poids simulé : 10
        INgram ngram3 = new Ngram("abc", 15, dispositionClavier); // Poids simulé : 15

        List<INgram> ngrams = List.of(ngram1, ngram2, ngram3);
        int tailleCorpus = 10;

        double resultat = calculateur.note(ngrams, tailleCorpus);

        assertEquals(2, resultat, 0.5,
                "La note doit être correctement calculée en fonction des poids et de la taille du corpus.");
    }

    @Test
    public void testNote_NGramsAvecZeroPoids() {
        CalculNoteDisposition calculateur = new CalculNoteDisposition();

        Map<String, List<Mouvement>> m = new HashMap<>();
        // Mock des INgram avec des poids nuls
        INgram ngram1 = new Ngram("", 0, m); // Poids simulé : 0
        INgram ngram2 = new Ngram("", 0, m); // Poids simulé : 0

        List<INgram> ngrams = List.of(ngram1, ngram2);
        int tailleCorpus = 100;

        double resultat = calculateur.note(ngrams, tailleCorpus);

        assertEquals(0, resultat, "Si tous les n-grammes ont un poids nul, la note doit être 0.");
    }

}
