package org.eu.eark.denormalizedb.model.id;

import org.eu.eark.denormalizedb.model.TableColumns;
import org.eu.eark.denormalizedb.model.TableMetaData;

public abstract class IdColumnSelector {

    protected TableMetaData metaData;
    protected TableColumns columns;

    public void set(TableMetaData metaData, TableColumns columns) {
        this.metaData = metaData;
        this.columns = columns;
    }

    public abstract int[] columnOrder();

    public abstract boolean ignore(int colIndex);

}
