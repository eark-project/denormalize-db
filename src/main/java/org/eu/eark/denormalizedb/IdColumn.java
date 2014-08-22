package org.eu.eark.denormalizedb;

import java.util.List;

public class IdColumn {

    private static final String SEP = "/";

    private final String tableName;
    private final List<Object[]> rows;
    private final int[] uniqueColumnOrder;

    public IdColumn(String tableName, List<Object[]> rows, int[] uniqueColumnOrder) {
        this.tableName = tableName;
        this.rows = rows;
        this.uniqueColumnOrder = uniqueColumnOrder;
    }

    public String value(int rowIndex) {
        Object[] row = rows.get(rowIndex);

        StringBuilder buf = new StringBuilder();
        buf.append(tableName);
        for (int i = 0; i < uniqueColumnOrder.length; i++) {
            buf.append(SEP);
            buf.append(row[uniqueColumnOrder[i]]);
        }

        return buf.toString();
    }

}
