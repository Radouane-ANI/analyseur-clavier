package projet.poo.evaluateurdisposition;

import java.util.List;
import java.util.Map;

/**
 * Interface définissant les opérations pour le traitement d'un fichier CSV
 * contenant des informations sur les n-grammes et leur association avec une
 * disposition de clavier.
 */
public interface ICSVTraitement {
    /* 
    * @param cheminFichier        Le chemin absolu ou relatif du fichier CSV à traiter.
    * @param dispositionClavier   Une Map associant chaque caractère à une liste
    *                             d'objets Touche, représentant la disposition du clavier.
    * @return                     La liste des n-grammes
    */
    List<INgram> traiterCSV(String cheminFichier,Map<String, List<Mouvement>> dispositionClavier);

    int tailleCorpusCSV(String cheminFichier);

}
