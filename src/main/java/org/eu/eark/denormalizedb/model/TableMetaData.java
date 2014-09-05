package org.eu.eark.denormalizedb.model;

/**
 * Meta data for a table like its name and the list of column meta data.
 */
public class TableMetaData {

    private static final String DEFAULT_TABLE_NAME = null;

    private String tableName = DEFAULT_TABLE_NAME;
    private final MetaDataColumns columns = new MetaDataColumns();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return tableName;
    }

    // delegate to columnMetaDatas

    public void addColumn(ColumnMetaData columnMetaData) {
        columns.add(columnMetaData);
    }

    public ColumnMetaData column(int colIndex) {
        return columns.metaDataColumn(colIndex);
    }

    public Iterable<ColumnMetaData> columns() {
        return columns;
    }

    public int numColumns() {
        return columns.size();
    }

}
