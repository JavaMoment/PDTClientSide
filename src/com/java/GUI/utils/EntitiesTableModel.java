package com.java.GUI.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.java.GUI.entities.CombinedEntities;


public class EntitiesTableModel<T, Z> extends AbstractTableModel {
	
	private List<Object[]> rows;
	private String[] columnNames;
	private int columnCount;
	
	public EntitiesTableModel(String[] colsNames, List<CombinedEntities> eventosAndEstudianteEvento) {
		if(rows == null) {
			rows = new ArrayList<>();
		}
		this.columnNames = Arrays.stream(colsNames).map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
				.toArray(String[]::new);
		this.columnCount = columnNames.length;

		for (CombinedEntities cE : eventosAndEstudianteEvento) {
			Object[] row = new Object[columnCount];
			for (int i = 0; i < columnCount; i++) {
				String actualColumn = "get" + columnNames[i].substring(0, 1).toUpperCase()
						+ columnNames[i].substring(1);
				row[i] = cE.callMethod(actualColumn);
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
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}

}
