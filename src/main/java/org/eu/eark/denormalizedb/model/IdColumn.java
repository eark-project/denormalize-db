package org.eu.eark.denormalizedb.model;

/**
 * A special, derived column that generates String IDs for each table row. The
 * IDs are the values concatenated and sorted by a given order. This is used to
 * create HBase primary IDs that support aggregation by only using part of the
 * key for a scan.
 * <ul>
 * <li>The column values are ordered by number of unique occurrences in the
 * columns descending. So the column with the least differentiation is the first
 * one in the key. This enables roll-up by scanning ranges of keys. See <a
 * href="http://hstack.org/hbasecon-low-latency-olap-with-hbase/">idea</a>.</li>
 * <li>The source columns of foreign keys are ignored because they are
 * duplicated.</li>
 * <li>If there are more unique columns identifying the row, only one is used.</li>
 * </ul>
 */
public class IdColumn {

    private static final String SEP = "/";

    private final TableMetaData metaData;
    private final TableData data;
    private final TableColumns columns;

    private final int[] uniqueColumnIndices;
    private final int[] uniqueColumnOrder;


    public IdColumn(TableMetaData tableMetaData, TableData rows, TableColumns columns) {
        this.metaData = tableMetaData;
        this.data = rows;
        this.columns = columns;

        this.uniqueColumnIndices = columns.uniqueColumnIndices();
        this.uniqueColumnOrder = columns.uniqueColumnOrder();
    }

    public String value(int rowIndex) {
        RowData row = data.get(rowIndex);

        StringBuilder buf = new StringBuilder();
        buf.append(metaData.getTableName());
        for (int i = 0; i < uniqueColumnOrder.length; i++) {
            int useColumn = uniqueColumnOrder[i];

            if (metaData.column(useColumn).hasFK()) {
                // do not add FK source because the target is the same
                continue;
            }

            buf.append(SEP);
            buf.append(row.get(useColumn));
        }

        return buf.toString();
    }

}
