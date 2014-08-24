package org.eu.eark.denormalizedb;

import java.util.ArrayList;
import java.util.List;

/**
 * Meta data for a table like its name.
 */
public class TableMetaData {

    private static final String DEFAULT_TABLE_NAME = null;
    private static final ColumnMetaData DEFAULT_COLUMN_META_DATA = new ColumnMetaData();

    private String tableName = DEFAULT_TABLE_NAME;
    private final List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addColumn(ColumnMetaData columnMetaData) {
        columns.add(columnMetaData);
    }

    public ColumnMetaData getColumn(int colIndex) {
        boolean metaDataNotSetForColumn = colIndex >= columns.size();
        if (metaDataNotSetForColumn) {
            return DEFAULT_COLUMN_META_DATA;
        }
        return columns.get(colIndex);
    }

}
