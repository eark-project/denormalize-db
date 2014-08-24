package org.eu.eark.denormalizedb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The actual data contained in a table, that is the values of all the rows.
 */
public class TableData implements Iterable<Object[]> {

    private final List<Object[]> rows = new ArrayList<Object[]>();
    // TODO RowData = Object[]
    
    public void add(Object... values) {
        rows.add(values);
    }

    public Object[] get(int rowIndex) {
        return rows.get(rowIndex);
    }

    public int size() {
        return rows.size();
    }

    @Override
    public Iterator<Object[]> iterator() {
        return rows.iterator();
    }

}
