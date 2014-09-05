package org.eu.eark.denormalizedb.model.id;

import org.eu.eark.denormalizedb.model.TableColumns;
import org.eu.eark.denormalizedb.model.TableMetaData;

public class AllSelector extends IdColumnSelector {

    private int[] originalColumnOrder;

    @Override
    public void set(TableMetaData metaData, TableColumns columns) {
        super.set(metaData, columns);

        setOriginalColumnOrder(columns);
    }

    private void setOriginalColumnOrder(TableColumns columns) {
        originalColumnOrder = new int[columns.numColumns()];
        for (int i = 0; i < originalColumnOrder.length; i++) {
            originalColumnOrder[i] = i;
        }
    }

    @Override
    public int[] columnOrder() {
        return originalColumnOrder;
    }

    @Override
    public boolean ignore(@SuppressWarnings("unused") int colIndex) {
        return false;
    }

}
