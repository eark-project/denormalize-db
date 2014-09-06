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

    public void copyTableNameTo(TableMetaData metaData) {
        metaData.setTableName(tableName);
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

    public MetaDataColumns columns() {
        return columns;
    }

    public void copyColumnsMetaDataTo(TableMetaData target) {
        columns.copyColumnsMetaDataTo(target);
    }

    public void copyReferencedDataTo(Table target) {
        columns.copyReferencedDataTo(target);
    }
    
    public int numColumns() {
        return columns.size();
    }

}
