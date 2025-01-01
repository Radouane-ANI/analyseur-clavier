package projet.poo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.moandjiezana.toml.Toml;

public class LectureDispositionClavier implements Disposition {
    private final Map<String, List<Mouvement>> sequenceTouche;
    private final Geometry geometry;
    private final String full;
    private final String base;
    private final String altgr;
    private final Toml toml;

    private Map<String, List<Mouvement>> deadKey;
    private Touche customDeadKey;

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
        geometry = Geometry.getGeometry(toml.getString("geometry"));
        base = toml.getString("base");
        full = toml.getString("full");
        altgr = toml.getString("altgr");
        if (geometry == null || (base == null && full == null && altgr == null)) {
            throw new IllegalArgumentException("Le fichier TOML ne contient pas une disposition de clavier valide.");
        }
        deadKey = new HashMap<>();
        sequenceTouche = new HashMap<>();
        initToucheDeBase();
    }

    private void initToucheDeBase() {
        for (Touche shift : geometry.getPosShift()) {
            List<Mouvement> mouvements = new ArrayList<>();
            mouvements.add(SequenceToucheFactory.create(shift));
            ajouteSequenceTouche("shift", mouvements, false);
        }
        Touche altgr = geometry.getAltgr();
        List<Mouvement> mouvementsAltgr = new ArrayList<>();
        mouvementsAltgr.add(SequenceToucheFactory.create(altgr));
        ajouteSequenceTouche("altgr", mouvementsAltgr, false);

        Touche espc = geometry.getEsp();
        List<Mouvement> mouvementsEspc = new ArrayList<>();
        mouvementsEspc.add(SequenceToucheFactory.create(espc));
        sequenceTouche.put(" ", mouvementsEspc);

        Touche entr = geometry.getEntr();
        List<Mouvement> mouvementsEntr = new ArrayList<>();
        mouvementsEntr.add(SequenceToucheFactory.create(entr));
        sequenceTouche.put("\n", mouvementsEntr);
        if (toml.contains("spacebar")) {
            if (toml.contains("spacebar.shift")) {
                List<Mouvement> mv = creerMouvements(espc, 0, sequenceTouche.get("shift"));
                ajouteSequenceTouche(toml.getString("spacebar.shift"), mv, false);
            }
            if (toml.contains("spacebar.altgr")) {
                List<Mouvement> mv = creerMouvements(espc, 0, mouvementsAltgr);
                ajouteSequenceTouche(toml.getString("spacebar.altgr"), mv, false);
            }
            if (toml.contains("spacebar.altgr_shift")) {
                List<Mouvement> mv = creerMouvements(espc, 1, mouvementsAltgr);
                ajouteSequenceTouche(toml.getString("spacebar.altgr_shift"), mv, false);
            }
        }

    }

    public void analyseDisposition() {
        if (full != null) {
            analyseTouche(full, sequenceTouche.get("altgr"));
            return;
        }
        if (base != null) {
            analyseTouche(base, null);
            initCustomDeadKey(base);
            if (customDeadKey != null) {
                analyseTouche(base, List.of(SequenceToucheFactory.create(customDeadKey)));
            }
        }
        if (altgr != null) {
            analyseTouche(altgr, sequenceTouche.get("altgr"));
        }
        gereDeadKeys();
    }

    private void analyseTouche(String clavier, List<Mouvement> mouvementPreliminaire) {
        String[] lignes = clavier.split("\n");
        for (int i = 1; i < 12; i++) {
            if (i % 3 != 0) {
                analyseLigne(lignes, i, mouvementPreliminaire);
            }
        }
    }

    private void analyseLigne(String[] lignes, int i, List<Mouvement> mouvementPreliminaire) {
        int numLigne = (i / 3) + 1;
        int numColone = 0;

        for (int j = 0; j < lignes[i].length() - 5; j++) {
            if (!isDebutTouche(lignes, i, j))
                continue;
            numColone++;
            if (!posValide(lignes, i, j)) {
                continue;
            }
            String touche = lignes[i].substring(j, j + 5);
            if (touche.charAt(2) != ' ' && customDeadKey == null) {
                Touche t = ToucheClavierFactory.create(numLigne, numColone, geometry);
                List<Mouvement> mouvements = creerMouvements(t, i, null);
                String key = touche.charAt(2) + "";
                if (!(touche.charAt(1) == '*' && touche.charAt(2) == '*')) {
                    ajouteSequenceTouche(key, mouvements, touche.charAt(1) == '*');
                    if ((i - 1) % 3 == 0 && touche.charAt(1) != '*') {
                        gereMiniscule(lignes, i, j, mouvementPreliminaire, key, numLigne, numColone);
                    }
                }
            }

            if (touche.charAt(4) != ' ' && mouvementPreliminaire != null) {
                Touche t = ToucheClavierFactory.create(numLigne, numColone, geometry);
                List<Mouvement> mouvements = creerMouvements(t, i, mouvementPreliminaire);
                String key = touche.charAt(4) + "";
                ajouteSequenceTouche(key, mouvements, touche.charAt(3) == '*');
                if ((i - 1) % 3 == 0 && touche.charAt(3) != '*') {
                    gereMiniscule(lignes, i, j, mouvementPreliminaire, key, numLigne, numColone);
                }
            }
        }
    }

    private void initCustomDeadKey(String base) {
        if (!base.contains("**")) {
            return;
        }
        int numLigne = 0;
        int numColone = 0;
        String[] lignes = base.split("\n");

        for (int i = 0; i < lignes.length; i++) {
            if (i % 3 == 0) {
                numLigne++;
                continue;
            }
            numColone = 0;
            for (int j = 0; j < lignes[i].length() - 5; j++) {
                if (!isDebutTouche(lignes, i, j))
                    continue;
                numColone++;
                String touche = lignes[i].substring(j, j + 5);
                if (touche.contains("**")) {
                    customDeadKey = ToucheClavierFactory.create(numLigne, numColone, geometry);
                }
            }

        }
    }

    private void gereMiniscule(String[] lignes, int i, int j, List<Mouvement> mvPreliminaire, String key, int numLigne,
            int numColone) {
        char k = key.charAt(0);
        int decalage = (mvPreliminaire == null ? 2 : 4);
        if (k >= 'A' && k <= 'Z' && lignes[i + 1].charAt(j + decalage) == ' ') {
            Touche t = ToucheClavierFactory.create(numLigne, numColone, geometry);
            List<Mouvement> mouvements = creerMouvements(t, i + 1, mvPreliminaire);
            ajouteSequenceTouche(Character.toLowerCase(k) + "", mouvements, false);
        }
    }

    private void ajouteSequenceTouche(String key, List<Mouvement> res, boolean isDK) {
        if (isDK) {
            if (deadKey.containsKey("*" + key)) {
                deadKey.get("*" + key).addAll(res);
            } else {
                deadKey.put("*" + key, res);
            }
            return;
        }
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
                    res.add(SequenceToucheFactory.create(mouvement, newTouche));
                }
            } else {
                res.add(SequenceToucheFactory.create(newTouche));
            }
        } else {
            for (Mouvement mouvement : mouvementPreliminaire) {
                if (isShiftIndex) {
                    for (Mouvement mouv : sequenceTouche.get("shift")) {
                        Mouvement newMouv = SequenceToucheFactory.create(mouvement, mouv);
                        res.add(SequenceToucheFactory.create(newMouv, newTouche));
                    }
                } else {
                    res.add(SequenceToucheFactory.create(mouvement, newTouche));
                }
            }
        }
        return res;
    }

    @Override
    public List<Mouvement> getsequenceTouche(char caractere) {
        if (!sequenceTouche.containsKey(caractere + "")) {
            return new ArrayList<>();
        }
        return new ArrayList<>(sequenceTouche.get(caractere + ""));
    }

    @Override
    public Geometry getGeometrie() {
        return geometry;
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

    private boolean posValide(String[] lignes, int i, int j) {
        if (lignes[i].length() >= i + 6 && (lignes[i].charAt(j + 6) == '┆' || lignes[i].charAt(j + 6) == '│'
                || lignes[i].charAt(j + 6) == '┃'
                || lignes[i].charAt(j + 6) == '·')) {
            return true;
        }
        return false;
    }

    private void gereDeadKeys() {
        String yamlFilePath = "src/main/resources/dead_keys.yaml";

        // Créer une instance de Yaml
        Yaml yaml = new Yaml();

        // Charger le fichier YAML en tant que Map
        try (FileInputStream inputStream = new FileInputStream(yamlFilePath)) {
            List<Map<String, String>> data = yaml.load(inputStream);
            for (Map<String, String> map : data) {
                if (deadKey.containsKey(map.get("char"))) {
                    ajouteDeadKeys(map);
                }
            }
            if (customDeadKey != null) {
                ajouteCustomDeadKeys(data.get(0));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier YAML: " + e.getMessage());
            System.out.println("Certains caractères spéciaux ne seront pas pris en compte.");
        }
    }

    private void ajouteCustomDeadKeys(Map<String, String> mapDK) {
        List<Mouvement> mouvements = List.of(SequenceToucheFactory.create(customDeadKey));
        Touche esp = sequenceTouche.get(" ").get(0).get(0);
        if (!toml.contains("spacebar.1dk")) {
            String dk;
            dk = mapDK.get("alt_space");
            List<Mouvement> res = creerMouvements(esp, 0, mouvements);
            ajouteSequenceTouche(dk, res, false);
        }

        if (toml.contains("spacebar.1dk_shift")) {
            return;
        }
        String dk_shift;
        dk_shift = mapDK.get("alt_self");

        List<Mouvement> res2 = creerMouvements(esp, 1, mouvements);
        ajouteSequenceTouche(dk_shift, res2, false);
    }

    private void ajouteDeadKeys(Map<String, String> mapDK) {
        String base = mapDK.get("base");
        String alt = mapDK.get("alt");
        for (int i = 0; i < base.length(); i++) {
            if (sequenceTouche.containsKey(base.charAt(i) + "")) {
                List<Mouvement> mouvements = new ArrayList<>(deadKey.get(mapDK.get("char")));
                List<Mouvement> res = Mouvement.fusionneMouvements(mouvements, sequenceTouche.get(base.charAt(i) + ""));
                ajouteSequenceTouche(alt.charAt(i) + "", res, false);
            }
        }
        Touche espc = sequenceTouche.get(" ").get(0).get(0);
        List<Mouvement> alt_space = creerMouvements(espc, 0, deadKey.get(mapDK.get("char")));
        ajouteSequenceTouche(mapDK.get("alt_space") + "", alt_space, false);
    }

    @Override
    public Map<String, List<Mouvement>> getSequenceTouche() {
        return new HashMap<>(sequenceTouche);
    }

}
