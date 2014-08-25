package org.eu.eark.denormalizedb;

/**
 * Meta data for a column like its name and constraints like unique.
 */
public class ColumnMetaData {

    private static final String DEFAULT_COLUMN_NAME = null;

    private String columnName = DEFAULT_COLUMN_NAME;
    private boolean unique;
    // TODO other meta data like PK, FK to Table x, type (enum?), etc.
    // look into Estonian database what is provided...

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique() {
        unique = true;
    }

}
