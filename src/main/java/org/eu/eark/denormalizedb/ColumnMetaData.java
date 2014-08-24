package org.eu.eark.denormalizedb;

/**
 * Meta data for a column like its name and constraints like unique.
 */
public class ColumnMetaData {

    private boolean unique;

    public boolean isUnique() {
        return unique;
    }

    public void setUnique() {
        unique = true;
    }

}
