package projet.poo.evaluateurdisposition;

import java.util.List;

public class CalculNoteDisposition implements NoteDisposition {

    /**
     * Calcule une note pour une liste de n-grammes donnée en fonction de leur poids
     * et de la taille du corpus.
     *
     * @param listNgrams   la liste des n-grammes à évaluer.
     * @param tailleCorpus la taille totale du corpus (doit être supérieure à 0).
     * @return une note normalisée en fonction des poids des n-grammes et de la
     *         taille du corpus.
     *         Retourne 0 si la taille du corpus est égale à 0.
     */
    @Override
    public double note(List<INgram> listNgrams, int tailleCorpus) {
        if (tailleCorpus <= 0) {
            return 0;
        }
        PoidsMouvements poids = new CalculPoids();

        for (INgram ngram : listNgrams) {
            poids.fequence1Gram(ngram.getSequenceTouches());
        }
        double somme = 0;
        List<Double> res = poids.calculerPoids(listNgrams);
        for (Double d : res) {
            somme += d;
        }
        return somme / (double) tailleCorpus;
    }

}
