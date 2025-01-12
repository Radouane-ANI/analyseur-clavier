package projet.poo.evaluateurdisposition;

import java.util.List;

public interface NoteDisposition {

    double note(List<INgram> listNgrams, int tailleCorpus);
}
