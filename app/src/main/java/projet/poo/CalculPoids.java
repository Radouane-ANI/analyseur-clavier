package projet.poo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculPoids implements PoidsMouvements{
    private final Map<Doigts, Integer> repartitionDoigts;
    private final int[] repartitionMains;

    public CalculPoids() {
        this.repartitionDoigts = new HashMap<>();
        this.repartitionMains = new int[2];
    }

    @Override
    public double calculLongeur1(Mouvement m) {
        if (m.getLongueur() != 1) {
            throw new IllegalArgumentException("Le mouvement n'est pas de longueur 1.");
        }
    
        //on recupere les information sur la touche 
        Touche touche = m.get(0); // Mouvement à une seule touche
        Doigts doigtUtilise = touche.doigt();
        boolean mainDroite = touche.mainsDroite();
        int nbTotal = Math.max(1, repartitionMains[0] + repartitionMains[1]);
    
        // On definit les poids pour chaque doigt
        Map<Doigts, Double> repartitionIdeale = Map.of(
            Doigts.POUCE, 10.0,
            Doigts.INDEX, 30.0,
            Doigts.MAJEUR, 25.0,
            Doigts.ANNULAIRE, 20.0,
            Doigts.AURICULAIRE, 15.0
        );

        double distanceX = Math.abs(touche.ligne() - 3);
        double distanceY = Math.abs(touche.colonne() - doigtUtilise.getColonneRepos(touche.geometry(), touche.ligne(), mainDroite));
    
        // Calculer l’effort en fonction de la difficulté d’accès
        double effortAcces = distanceX + distanceY;
    
        // Calculer l’écart entre la répartition réelle et idéale
        double repartitionIdealePourDoigt = repartitionIdeale.getOrDefault(doigtUtilise, 0.0);
        double repartitionReelDoigt = ((double) repartitionDoigts.getOrDefault(doigtUtilise, 0)) / nbTotal * 100;
        double ecartRepartition = Math.abs(repartitionReelDoigt - repartitionIdealePourDoigt); // Exprimé en pourcentage
    
        // Calculer l’équilibre entre les mains
        double effortMain = repartitionMains[0] / nbTotal * 100;
        double ecartEquilibreMains = Math.abs(effortMain - 50);

        // Note finale combiner avec tous les critères
        double note = Math.max(0.0, 10.0 - (effortAcces + ecartRepartition / 100 + ecartEquilibreMains / 100));
        return note;
    }

    @Override
    public double calculLongeur2(Mouvement m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculLongeur2'");
    }

    @Override
    public double calculLongeur3(Mouvement m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculLongeur3'");
    }

    @Override
    public void fequence1Gram(List<Mouvement> mouv) {
        for (Mouvement mouvement : mouv) {
            if (mouvement.getLongueur() == 1) {
                Touche touche = mouvement.get(0);
                Doigts doigt = touche.doigt();
                repartitionDoigts.put(doigt, repartitionDoigts.getOrDefault(doigt, 0) + 1);
                if (touche.mainsDroite()) {
                    repartitionMains[1]++;
                } else {
                    repartitionMains[0]++;
                }
            }
        }
    }

}
