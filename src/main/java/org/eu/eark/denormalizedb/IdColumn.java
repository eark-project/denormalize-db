package org.eu.eark.denormalizedb;

import java.util.List;

/**
 * A special, derived column that generates String IDs for each table row.
 * The IDs are the values concatenated and sorted by a given order.
 * This is used to create HBase primary IDs that support aggregation by only using part of the key for a scan.
 */
public class IdColumn {

    private static final String SEP = "/";

    private final TableMetaData tableMetaData;
    private final List<Object[]> rows;
    private final int[] uniqueColumnOrder;

    public IdColumn(TableMetaData tableMetaData, List<Object[]> rows, int[] uniqueColumnOrder) {
        this.tableMetaData = tableMetaData;
        this.rows = rows;
        this.uniqueColumnOrder = uniqueColumnOrder;
    }

    public String value(int rowIndex) {
        Object[] row = rows.get(rowIndex);

        StringBuilder buf = new StringBuilder();
        buf.append(tableMetaData.getTableName());
        for (int i = 0; i < uniqueColumnOrder.length; i++) {
            buf.append(SEP);
            buf.append(row[uniqueColumnOrder[i]]);
        }

        return buf.toString();
    }

}
