package projet.poo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.moandjiezana.toml.Toml;

public class LectureDispositionClavier implements Disposition {
    private final Map<String, List<Mouvement>> sequenceTouche;
    private final String geometry;
    private final String full;
    private final String base;
    private final String altgr;
    private final Toml toml;

    public LectureDispositionClavier(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Le chemin fourni est null ou vide.");
        }
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas ou n'est pas un fichier valide.");
        }
        try {
            toml = new Toml().read(file);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fichiers TOML invalide.");
        }
        geometry = toml.getString("geometry");
        base = toml.getString("base");
        full = toml.getString("full");
        altgr = toml.getString("altgr");
        if (geometry == null || (base == null && full == null && altgr == null)) {
            throw new IllegalArgumentException("Le fichier TOML ne contient pas une disposition de clavier valide.");
        }
        sequenceTouche = new HashMap<>();
        Touche shift = new ToucheClavier(3, 1, geometry);
        List<Mouvement> mouvements = new ArrayList<>();
        mouvements.add(new SequenceTouche(shift));
        sequenceTouche.put("shift", mouvements);

        Touche altgr = new ToucheClavier(5, 9, geometry);
        List<Mouvement> mouvementsAltgr = new ArrayList<>();
        mouvementsAltgr.add(new SequenceTouche(altgr));
        sequenceTouche.put("altgr", mouvementsAltgr);
    }

    public void analyseDisposition() {
        if (full != null) {
            analyseToucheSimple(full, false);
            analyseToucheSimple(full, true);
            return;
        }
        if (base != null) {
            analyseToucheSimple(base, false);
        }
        if (altgr != null) {
            analyseToucheSimple(altgr, true);
        }
    }

    private void analyseToucheSimple(String clavier, boolean isAltGr) {
        String[] lignes = clavier.split("\n");
        for (int i = 1; i < 12; i++) {
            if (i % 3 != 0) {
                analyseLigneSimple(lignes, i, isAltGr);
            }
        }
        System.out.println(sequenceTouche);
    }

    private void analyseLigneSimple(String[] lignes, int i, boolean isAltGr) {
        int numLigne = (i / 3) + 1;
        int numColone = 0;
        for (int j = 0; j < lignes[i].length() - 5; j++) {
            if (!isDebutTouche(lignes, i, j))
                continue;
            numColone++;

            String touche = lignes[i].substring(j, j + 5);
            int decalage = isAltGr ? 2 : 0;

            if (touche.charAt(decalage + 1) == '*' || touche.charAt(decalage + 2) == ' ')
                continue;

            Touche t = new ToucheClavier(numLigne, numColone, geometry);
            List<Mouvement> mouvements = creerMouvements(t, i, isAltGr ? sequenceTouche.get("altgr") : null);

            decalage += 2;
            String key = touche.charAt(decalage) + "";

            ajouteSequenceTouche(key, mouvements);

            if ((i - 1) % 3 == 0) {
                gereMiniscule(lignes, i, j, isAltGr, key, numLigne, numColone);
            }
        }
        System.out.println(lignes[i]);

    }

    private void gereMiniscule(String[] lignes, int i, int j, boolean isAltGr, String key, int numLigne,
            int numColone) {
        char k = key.charAt(0);
        if (k >= 'A' && k <= 'Z' && lignes[i + 1].charAt(j + (isAltGr ? 4 : 2)) == ' ') {
            Touche t = new ToucheClavier(numLigne, numColone, geometry);
            List<Mouvement> mouvements = creerMouvements(t, i + 1, isAltGr ? sequenceTouche.get("altgr") : null);
            ajouteSequenceTouche(Character.toLowerCase(k) + "", mouvements);
        }
    }

    private void ajouteSequenceTouche(String key, List<Mouvement> res) {
        if (sequenceTouche.containsKey(key)) {
            sequenceTouche.get(key).addAll(res);
        } else {
            sequenceTouche.put(key, res);
        }
    }

    private List<Mouvement> creerMouvements(Touche newTouche, int i, List<Mouvement> mouvementPreliminaire) {
        List<Mouvement> res = new ArrayList<>();
        boolean isShiftIndex = (i - 1) % 3 == 0;

        if (mouvementPreliminaire == null) {
            if (isShiftIndex) {
                for (Mouvement mouvement : sequenceTouche.get("shift")) {
                    res.add(new SequenceTouche(mouvement, newTouche));
                }
            } else {
                res.add(new SequenceTouche(newTouche));
            }
        } else {
            for (Mouvement mouvement : mouvementPreliminaire) {
                if (isShiftIndex) {
                    for (Mouvement mouv : sequenceTouche.get("shift")) {
                        Mouvement newMouv = new SequenceTouche(mouvement, mouv);
                        res.add(new SequenceTouche(newMouv, newTouche));
                    }
                } else {
                    res.add(new SequenceTouche(mouvement, newTouche));
                }
            }
        }
        return res;
    }

    @Override
    public List<Mouvement> getsequenceTouche(char caractere) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTouche'");
    }

    @Override
    public String getGeometrie() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGeometrie'");
    }

    private boolean isDebutTouche(String[] lignes, int i, int j) {
        if (lignes[i].charAt(j) != '┆' && lignes[i].charAt(j) != '│' && lignes[i].charAt(j) != '┃'
                && lignes[i].charAt(j) != '·') {
            return false;

        }
        if (i > 0 && lignes[i].charAt(j) == lignes[i - 1].charAt(j)) {
            return true;
        }
        if (i < 11 && lignes[i].charAt(j) == lignes[i + 1].charAt(j)) {
            return true;
        }
        return false;
    }
}
