package org.eu.eark.denormalizedb.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Encapsulated Container for table column meta-data.
 */
public class MetaDataColumns implements Iterable<ColumnMetaData> {

    private static final ColumnMetaData DEFAULT_COLUMN_META_DATA = new ColumnMetaData();

    private final List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

    public void add(ColumnMetaData columnMetaData) {
        columns.add(columnMetaData);
    }

    public ColumnMetaData metaDataColumn(int colIndex) {
        boolean metaDataNotSetForColumn = colIndex >= columns.size();
        if (metaDataNotSetForColumn) {
            return DEFAULT_COLUMN_META_DATA;
        }
        return columns.get(colIndex);
    }

    @Override
    public Iterator<ColumnMetaData> iterator() {
        return columns.iterator();
    }

    public int size() {
        return columns.size();
    }

    // de-normalisation

    public void copyColumnsMetaDataTo(TableMetaData target) {
        for (ColumnMetaData cmd : columns) {
            target.addColumn(cmd);
        }
    }

    public void copyReferencedDataTo(Table target) {
        for (ColumnMetaData cmd : columns) {
            cmd.copyReferencedDataTo(target);
        }
    }

}
