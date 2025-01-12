package projet.poo.evaluateurdisposition;

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
        if (m == null || m.getLongueur() != 1 || m.get(0) == null) {
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
        double note = Math.max(0.0, 10.0 - (effortAcces + ecartRepartition / 20 + ecartEquilibreMains / 20));
        return note/10.0;
    }

    @Override
    public double calculLongeur2(Mouvement m) {
        if (m == null || m.getLongueur() != 2 || m.get(0) == null || m.get(1) == null) {
            throw new IllegalArgumentException("Le mouvement n'est pas de longueur 2.");
        }
        double note = 7.5;
        // on recupere les information sur la touche
        Touche touche1 = m.get(0); // Mouvement à une seule touche
        Touche touche2 = m.get(1); // Mouvement à une seule touche
        Doigts doigt1 = touche1.doigt();
        Doigts doigt2 = touche2.doigt();
        boolean mainDroite1 = touche1.mainsDroite();
        boolean mainDroite2 = touche2.mainsDroite();

        if (doigt1 == doigt2) {
            note -= 2.5; // Mouvement à un seul doigt
        }
        if (doigt1.getColonneRepos(touche1.geometry(), touche1.ligne(), mainDroite1) != touche1.colonne()
                || doigt2.getColonneRepos(touche2.geometry(), touche2.ligne(), mainDroite2) != touche2.colonne()) {
            note -= 2.5; // Mouvement à extension latérale
        }
        if (mainDroite1 == mainDroite2) {
            if (Math.abs(touche1.ligne() - touche2.ligne()) >= 3) {
                note -= 2.5; // Mouvement de type ciseaux
            } else if (doigt1 != doigt2 && !(doigt1.getColonneRepos(touche1.geometry(), touche1.ligne(),
                    mainDroite1) != touche1.colonne()
                    || doigt2.getColonneRepos(touche2.geometry(), touche2.ligne(), mainDroite2) != touche2.colonne())) {
                if ((doigt1 == Doigts.AURICULAIRE && doigt2 == Doigts.ANNULAIRE) ||
                        (doigt1 == Doigts.ANNULAIRE && doigt2 == Doigts.MAJEUR) ||
                        (doigt1 == Doigts.MAJEUR && doigt2 == Doigts.INDEX)) {
                    note += 2.5; // Roulement de l'extérieur vers l'intérieur
                } else {
                    note += 1.5; // Autres roulements
                }
            }
        } else {
            note += 2.5; // Alternance main gauche/main droite
        }

        return note/10.0;
    }

    @Override
    public double calculLongeur3(Mouvement m) {
        if (m == null || m.getLongueur() != 3 || m.get(0) == null || m.get(1) == null || m.get(2) == null) {
            throw new IllegalArgumentException("Le mouvement n'est pas de longueur 3.");
        }

        double note = 10.0;
        Touche touche1 = m.get(0);
        Touche touche2 = m.get(1);
        Touche touche3 = m.get(2);
        Doigts doigt1 = touche1.doigt();
        Doigts doigt2 = touche2.doigt();
        Doigts doigt3 = touche3.doigt();
        boolean mainDroite1 = touche1.mainsDroite();
        boolean mainDroite2 = touche2.mainsDroite();
        boolean mainDroite3 = touche3.mainsDroite();

        if (mainDroite3 == mainDroite1 && mainDroite2 == mainDroite1) {
            if ((doigt1.ordinal() < doigt2.ordinal() && doigt2.ordinal() > doigt3.ordinal()) ||
                    (doigt1.ordinal() > doigt2.ordinal() && doigt2.ordinal() < doigt3.ordinal())) {
                note -= 4.0; // Mauvaises redirections
            }
            if (doigt1 != Doigts.INDEX && doigt2 != Doigts.INDEX && doigt3 != Doigts.INDEX) {
                note -= 2.0; // Mauvaises redirections sans l'index
            }
        }

        if (doigt1 == doigt3 && mainDroite1 == mainDroite3) {
            if (mainDroite3 != mainDroite2) {
                note -= 4.0; // Skipgrammes
            }
        }
        return note/10.0;
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
