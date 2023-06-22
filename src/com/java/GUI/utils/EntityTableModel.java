package com.java.GUI.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.table.AbstractTableModel;

public class EntityTableModel<E> extends AbstractTableModel {

    private LinkedList<Object[]> rows = new LinkedList<>();
	private int columnCount;
	private String[] columnNames;
	
	public EntityTableModel(String[] colsNames, List<E> dbObjects) {
		 this.columnNames = Arrays.stream(colsNames)
				    .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
				    .toArray(String[]::new);
		 this.columnCount = columnNames.length;
		 
		 for(E t : dbObjects) {
			 Object[] row = new Object[columnCount];
			 for(int i = 0; i < columnCount; i++) {
				 String actualColumn = "get" + colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
				 List<Method> methods = Arrays.asList(t.getClass().getMethods());
				 Optional<Method> actualColMethod = methods.stream().filter(m -> m.getName().equalsIgnoreCase(actualColumn)).findFirst();
				 try {
					 final Object r = actualColMethod.get().invoke(t);
					 row[i] = r;
				 } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					 e.printStackTrace();
				 }
			 }
			 rows.add(row);
				 
		 }
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
        return rows.get(rowIndex)[columnIndex];
	}
	
	public void sortByIdAsc() {
        Collections.sort(rows, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] row1, Object[] row2) {
                try {
                    Date date1 = (Date) Date.valueOf(((Timestamp) row1[6]).toLocalDateTime().toLocalDate()); // Assuming birth date is in the third column
                    Date date2 = (Date) Date.valueOf(((Timestamp) row2[6]).toLocalDateTime().toLocalDate());
                    return date1.compareTo(date2);
                } catch (ClassCastException e) {
                    // Handle ClassCastException if the birth date is not a Date object
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        fireTableDataChanged();
    }
    
    public void sortByIdDesc() {
    	Collections.sort(rows, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] row1, Object[] row2) {
                try {
                    Date date1 = (Date) Date.valueOf(((Timestamp) row1[6]).toLocalDateTime().toLocalDate()); // Assuming birth date is in the third column
                    Date date2 = (Date) Date.valueOf(((Timestamp) row2[6]).toLocalDateTime().toLocalDate());
                    return date2.compareTo(date1);
                } catch (ClassCastException e) {
                    // Handle ClassCastException if the birth date is not a Date object
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        fireTableDataChanged();
    }

}
