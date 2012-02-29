// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * it is the Table Model which is used to create a property editor. it has t columns and multiple rows,
 * each row is a property, first column is the name of property and second column is the value.
 * the names are stored as Object.
 *
 * @author Azin Azadi
 *         Email :
 */

public class GPropertyTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = -1101017043084681545L;

    /**
     * constructor
     */
    public GPropertyTableModel() {
    }

    /**
     * the number of properties, stored
     */
    public int getRowCount() {
        return values.size();
    }

    public String getColumnName(int column) {
        return (column == 0 ? "Name" : "Value");
    }

    /*return 2**/
    public int getColumnCount() {
        return 2;
    }

    Vector values = new Vector();
    Vector names = new Vector();

    /**
     * clears all values ans names of the table
     */
    public void clear() {
        values.clear();
        names.clear();
        fireTableStructureChanged();
    }

    /**
     * adds a row to the table
     */
    public void addData(Object name, Object value) {
        names.add(name);
        values.add(value);
        super.fireTableDataChanged();
    }

    /**
     * sets the value of the given row(name). returns false if the given name does not exist in the properties
     */
    public boolean setValue(Object name, Object value) {
        int ri = names.indexOf(name);
        //System.out.println(name+" "+ri);
        if (ri != -1) {
            setValueAt(value, ri, ri);
            super.fireTableDataChanged();
            return true;
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //System.out.println(""+aValue);
        values.set(rowIndex, aValue);
        fireTableRowsUpdated(rowIndex, rowIndex);
//        super.fireTableDataChanged();
    }

    /**
     * the second column cells(values) are editable, and the first are not
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //super.getColumnClass(columnIndex);
        if (columnIndex == 0) {
            return String.class;
        } else {
            return GCellEditor.class;
        }
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            return values.get(rowIndex);
        } else {
            return names.get(rowIndex);
        }
    }

}