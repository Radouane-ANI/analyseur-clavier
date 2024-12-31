package projet.poo;

import java.util.ArrayList;
import java.util.List;

public enum Geometry {
    ANSI,
    ISO,
    ABNT,
    JIS,
    ALT,
    ERGO;

    List<Touche> getPosShift() {
        List<Touche> posShift = new ArrayList<>();
        if (this != ERGO)
            posShift.add(ToucheClavierFactory.create(4, 1, this));
        switch (this) {
            case ERGO:
                posShift.add(ToucheClavierFactory.create(3, 1, this));
                posShift.add(ToucheClavierFactory.create(3, 14, this));
                break;
            case ANSI:
                posShift.add(ToucheClavierFactory.create(4, 12, this));
                break;
            case ABNT:
                posShift.add(ToucheClavierFactory.create(4, 14, this));
                break;
            case ALT:
                posShift.add(ToucheClavierFactory.create(4, 12, this));
                break;

            case ISO:
                posShift.add(ToucheClavierFactory.create(4, 13, this));
                break;
            case JIS:
                posShift.add(ToucheClavierFactory.create(4, 13, this));
                break;
        }
        return posShift;
    }

    Touche getAltgr() {
        if (this == JIS) {
            return ToucheClavierFactory.create(5, 8, this);
        }
        return ToucheClavierFactory.create(5, 5, this);
    }

    Touche getEsp() {
        if (this == JIS) {
            return ToucheClavierFactory.create(5, 5, this);
        }
        return ToucheClavierFactory.create(5, 4, this);
    }

    Touche getEntr() {
        if (this == ANSI || this == ALT) {
            return ToucheClavierFactory.create(4, 13, this);

        }
        return ToucheClavierFactory.create(3, 14, this);
    }

    static Geometry getGeometry(String geometry) {
        switch (geometry) {
            case "ANSI":
                return ANSI;
            case "ISO":
                return ISO;
            case "ABNT":
                return ABNT;
            case "JIS":
                return JIS;
            case "ALT":
                return ALT;
            case "ERGO":
                return ERGO;
            default:
                throw new IllegalArgumentException("La géométrie du clavier n'est pas reconnue.");
        }
    }

    int[] getMillieu() {
        int[] limites = new int[6];

        switch (this) {
            case ABNT:
                limites = new int[] { 0, 7, 6, 7, 7, 4 };
                break;
            case ALT:
            case ANSI:
                limites = new int[] { 0, 7, 6, 6, 6, 4 };
                break;
            case ERGO:
                limites = new int[] { 0, 6, 6, 6, 6, 4 };
                break;
            case ISO:
                limites = new int[] { 0, 7, 6, 7, 6, 4 };
                break;
            case JIS:
                limites = new int[] { 0, 7, 6, 7, 6, 5 };
                break;
        }
        return limites;
    }

}
