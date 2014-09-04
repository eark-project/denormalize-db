package org.eu.eark.denormalizedb.model;

/**
 * Meta data for a column like its name and constraints like unique.
 */
public class ColumnMetaData {

    private static final String DEFAULT_COLUMN_NAME = null;

    private Reference selfReference;
    private String columnName = DEFAULT_COLUMN_NAME;
    private boolean unique;
    private Reference reference;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Reference getSelfReference() {
        return selfReference;
    }

    public void setSelfReferenceOnce(Table table, int colIndex) {
        if (selfReference == null) {
            this.selfReference = new Reference(table, colIndex);
        }
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique() {
        unique = true;
    }

    public void references(Table table, int colIndex) {
        reference = new Reference(table, colIndex);
    }

    public Reference getReference() {
        return reference;
    }

}
