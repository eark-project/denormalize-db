package org.eu.eark.denormalizedb;

/**
 * A special, derived column that generates String IDs for each table row. The IDs are the values concatenated and sorted by a given order.
 * This is used to create HBase primary IDs that support aggregation by only using part of the key for a scan.
 */
public class IdColumn {

    private static final String SEP = "/";

    private final TableMetaData metaData;
    private final TableData data;
    private final int[] uniqueColumnOrder;

    public IdColumn(TableMetaData tableMetaData, TableData rows, int[] uniqueColumnOrder) {
        this.metaData = tableMetaData;
        this.data = rows;
        this.uniqueColumnOrder = uniqueColumnOrder;
    }

    public String value(int rowIndex) {
        RowData row = data.get(rowIndex);

        StringBuilder buf = new StringBuilder();
        buf.append(metaData.getTableName());
        for (int i = 0; i < uniqueColumnOrder.length; i++) {
            buf.append(SEP);
            buf.append(row.get(uniqueColumnOrder[i]));
        }

        return buf.toString();
    }

}
