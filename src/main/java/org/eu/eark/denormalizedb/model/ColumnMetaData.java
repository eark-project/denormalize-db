package org.eu.eark.denormalizedb.model;

/**
 * Meta data for a column like its name and constraints like unique.
 */
public class ColumnMetaData {

    private static final String DEFAULT_COLUMN_NAME = null;

    private Reference selfReference;
    private String columnName = DEFAULT_COLUMN_NAME;
    private ColumnDataType type = ColumnDataType.UNKNOWN;
    private Reference reference;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColumnDataType getType() {
        return type;
    }

    public void setType(ColumnDataType type) {
        this.type = type;
    }

    public Reference getSelfReference() {
        return selfReference;
    }

    public void setSelfReferenceOnce(Table table, int colIndex) {
        if (selfReference == null) {
            this.selfReference = new Reference(table, colIndex);
        }
    }

    public void references(Table table, int colIndex) {
        reference = new Reference(table, colIndex);
    }

    public Reference getReference() {
        return reference;
    }

    public boolean hasFK() {
        return getReference() != null;
    }

    @Override
    public String toString() {
        return columnName;
    }

}
