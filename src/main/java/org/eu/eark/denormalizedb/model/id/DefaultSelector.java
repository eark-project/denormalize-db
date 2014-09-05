package org.eu.eark.denormalizedb.model.id;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eu.eark.denormalizedb.model.TableColumns;
import org.eu.eark.denormalizedb.model.TableMetaData;

public class DefaultSelector extends IdColumnSelector {

    private int[] uniqueCountColumnOrder;

    private Set<Integer> columnsToIgnore = new HashSet<Integer>();
    private List<Integer> uniqueColumnIndices;

    @Override
    public void set(TableMetaData metaData, TableColumns columns) {
        super.set(metaData, columns);

        this.uniqueCountColumnOrder = columns.uniqueColumnOrder();

        ignoreAdditionalUniqueColumnValues();
    }

    private void ignoreAdditionalUniqueColumnValues() {
        this.uniqueColumnIndices = columns.uniqueColumnIndices();
        if (uniqueColumnIndices.size() > 1) {
            // has more unique. look for best, ignore others
            addIgnoredColumnsToList();
        }
    }

    private void addIgnoredColumnsToList() {
        for (int colIndex : uniqueColumnIndices) {
            if (columnIsOk(colIndex)) {
                useColumn(colIndex);
                return;
            }
        }
        int firstUniqueColIndex = uniqueColumnIndices.get(0);
        useColumn(firstUniqueColIndex);
    }

    private boolean columnIsOk(int index) {
        boolean hasText = columns.column(index).getAnalytics().averageLength() >= 6;
        boolean notTooLong = columns.column(index).getAnalytics().maxLength() <= 40;
        return hasText && notTooLong;
    }

    private void useColumn(Integer idx) {
        columnsToIgnore.addAll(uniqueColumnIndices);
        columnsToIgnore.remove(idx);
    }

    @Override
    public int[] columnOrder() {
        return uniqueCountColumnOrder;
    }

    @Override
    public boolean ignore(int colIndex) {
        // do not add FK source because the target is the same
        boolean isReferenceSource = metaData.column(colIndex).hasFK();
        boolean isIgnoredUniqueColumn = columnsToIgnore.contains(colIndex);

        return isReferenceSource || isIgnoredUniqueColumn;
    }

}
