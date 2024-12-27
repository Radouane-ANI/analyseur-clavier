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
    }

    public void analyseDisposition() {
        // if (full != null) {
        // analyseVariable(full, "");
        // return;
        // }
        if (base != null) {
            analyseVariable(base, "");
        }
        // if (altgr != null) {
        // analyseVariable(altgr, "altgr");
        // }
    }

    private void analyseVariable(String clavier, String touchePreliminaire) {
        String[] lignes = clavier.split("\n");
        int numLigne = 0;
        for (int i = 0; i < 12; i++) {
            if (i % 3 == 0) {
                numLigne++;
                continue;
            }
            int numTouche = 0;
            for (int j = 0; j < lignes[i].length(); j++) {
                if (isDebutTouche(lignes, i, j) && j + 5 < lignes[i].length() - 1) {
                    numTouche++;
                    String touche = lignes[i].substring(j, j + 5);
                    if (touche.charAt(1) == '*' && touche.charAt(2) != ' ' || touche.charAt(2) == ' ') {
                        continue;
                    }

                    Touche t = new ToucheClavier(numLigne, numTouche, geometry);
                    List<Mouvement> res = creerMouvements(t, i);

                    String key = touche.charAt(2) + "";
                    if ((i - 1) % 3 == 0) {
                        char k = key.charAt(0);
                        if (k >= 'A' && k <= 'Z' && lignes[i + 1].charAt(j + 2) == ' ') {
                            List<Mouvement> minuscule = creerMouvements(t, i + 1);
                            ajouteSequenceTouche(((char) (k + 32)) + "", minuscule);
                        }
                    }
                    ajouteSequenceTouche(key, res);
                }
            }
            System.out.println(lignes[i]);
        }
        System.out.println(sequenceTouche);
    }

    private void ajouteSequenceTouche(String key, List<Mouvement> res) {
        if (sequenceTouche.containsKey(key)) {
            sequenceTouche.get(key).addAll(res);
        } else {
            sequenceTouche.put(key, res);
        }
    }

    private List<Mouvement> creerMouvements(Touche t, int i) {
        List<Mouvement> res = new ArrayList<>();
        if ((i - 1) % 3 == 0) {
            List<Mouvement> shift = sequenceTouche.get("shift");
            for (Mouvement mouvement : shift) {
                List<Touche> mouvementTouche = mouvement.getMouvement();
                mouvementTouche.add(t);
                res.add(new SequenceTouche(mouvementTouche));
            }
        } else {
            Mouvement sequence = new SequenceTouche(t);
            res.add(sequence);
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
