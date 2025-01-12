package projet.poo.evaluateurdisposition;

import java.util.List;
import java.util.Scanner;

import projet.poo.utils.Logicel;

public class EvaluateurDisposition implements Logicel {

    /**
     * Lance une analyse basée sur une disposition de clavier et un fichier CSV et
     * leur attributs une notes.
     * Demande à l'utilisateur :
     * 1. Le chemin du fichier de disposition de clavier.
     * 2. Le chemin du fichier CSV contenant le corpus.
     * 
     */
    @Override
    public void exec() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez le chemin du fichier de disposition: ");
            String cheminDisposition = scanner.nextLine();
            Disposition d = new LectureDispositionClavier(cheminDisposition);
            d.analyseDisposition();

            System.out.println("Entrez le chemin du fichier CSV: ");
            String cheminFichier = scanner.nextLine();
            System.out.println("Lecture et tri du fichier CSV :");
            LectureEtTraitementCSV traitement = new LectureEtTraitementCSV();
            List<INgram> listNgrams = traitement.traiterCSV(cheminFichier, d.getSequenceTouche());
            int tailleCorpus = traitement.tailleCorpusCSV(cheminFichier);
            NoteDisposition evaluation = new CalculNoteDisposition();
            System.out.println("Score du clavier pour ce corpus: "
                    + evaluation.note(listNgrams, tailleCorpus));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
