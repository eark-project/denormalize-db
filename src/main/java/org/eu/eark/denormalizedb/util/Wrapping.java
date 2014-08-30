package org.eu.eark.denormalizedb.util;

public class Wrapping {

    public static int toPrimitive(Integer row, String exceptionIfImpossible) {
        if (row == null) {
            throw new IllegalArgumentException(exceptionIfImpossible);
        }
        return row.intValue();
    }

    public static int[] toPrimitive(Integer[] wrappers) {
        int[] primitives = new int[wrappers.length];
        for (int i = 0; i < wrappers.length; i++) {
            primitives[i] = toPrimitive(wrappers[i], "null");
        }
        return primitives;
    }

}
