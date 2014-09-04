package org.eu.eark.denormalizedb.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Meta data for a table like its name.
 */
public class TableMetaData {

    private static final String DEFAULT_TABLE_NAME = null;
    private static final ColumnMetaData DEFAULT_COLUMN_META_DATA = new ColumnMetaData();

    private final Table table;
    private String tableName = DEFAULT_TABLE_NAME;
    private final List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

    public TableMetaData(Table table) {
        this.table = table;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addColumn(ColumnMetaData columnMetaData) {
        columnMetaData.setSelfReferenceOnce(table, columns.size());
        columns.add(columnMetaData);
    }

    ColumnMetaData column(int colIndex) {
        boolean metaDataNotSetForColumn = colIndex >= columns.size();
        if (metaDataNotSetForColumn) {
            return DEFAULT_COLUMN_META_DATA;
        }
        return columns.get(colIndex);
    }

    public Iterable<ColumnMetaData> columns() {
        return new Iterable<ColumnMetaData>() {
            @Override
            public Iterator<ColumnMetaData> iterator() {
                return columns.iterator();
            }
        };
    }

    @Override
    public String toString() {
        return tableName;
    }
}
