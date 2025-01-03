package projet.poo;

import java.util.Map;

public class CalculPoids implements PoidsMouvements{

    @Override
    public double calculLongeur1(Mouvement m) {
        if (m.getLongueur() != 1) {
            throw new IllegalArgumentException("Le mouvement n'est pas de longueur 1.");
        }
    
        //on recupere les information sur la touche 
        Touche touche = m.get(0); // Mouvement à une seule touche
        Doigts doigtUtilise = touche.doigt();
        boolean mainDroite = touche.mainsDroite();
    
        // On definit les poids pour chaque doigt
        Map<Doigts, Double> repartitionIdeale = Map.of(
            Doigts.POUCE, 10.0,
            Doigts.INDEX, 30.0,
            Doigts.MAJEUR, 25.0,
            Doigts.ANNULAIRE, 20.0,
            Doigts.AURICULAIRE, 15.0
        );
    
        // On definit les poids par rangée par difficulté d'accès
        Map<Integer, Double> poidsRangée = Map.of(
            0, 1.0, // Rangée centrale (position de repos)
            1, 1.5, // Rangée supérieure
            2, 1.5, // Rangée inférieure
            3, 2.0  // Autres (comme étirements extrêmes)
        );
    
        // Calculer l’effort en fonction de la difficulté d’accès
        double effortAcces = poidsRangée.getOrDefault(touche.ligne(), 2.0);
    
        // Calculer l’écart entre la répartition réelle et idéale
        double repartitionIdealePourDoigt = repartitionIdeale.getOrDefault(doigtUtilise, 0.0);
        double ecartRepartition = Math.abs(repartitionIdealePourDoigt - effortAcces * 10); // Exprimé en pourcentage
    
        // Calculer l’équilibre entre les mains
        double effortMain = mainDroite ? 50 + ecartRepartition : 50 - ecartRepartition;
    
        // Note finale combiner avec tous les critères
        return 10.0 - (effortAcces + ecartRepartition /2 + Math.abs(effortMain - 50) / 10);
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

}
