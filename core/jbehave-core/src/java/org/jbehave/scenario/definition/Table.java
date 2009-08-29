package org.jbehave.scenario.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private final String string;

	public Table(String string) {
		this.string = string;
		parse(string);
	}

	private void parse(String string) {
		data = new ArrayList<Map<String, String>>();
		String[] rows = string.split("\n");
		List<String> headers = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			List<String> columns = columnsFor(rows[i]);
			if ( i == 0 ) {
				headers.addAll(columns);
			} else {
				Map<String, String> map = new HashMap<String, String>();
				for ( int j = 0; j < columns.size(); j++ ){
					map.put(headers.get(j), columns.get(j));
				}
				data.add(map);
			}
		}
	}

	private List<String> columnsFor(String row) {
		List<String> columns = new ArrayList<String>();
		for ( String column : row.split("\\|") ){
			columns.add(column.trim());
		}
		int size = columns.size();
		if  ( size > 0 ){
			columns.remove(0);		
		}
		return columns;
	}

	public Map<String, String> getRow(int row){
		return data.get(row);		
	}
	
	public int getRowCount(){
		return data.size();
	}

	public List<Map<String, String>> getRows() {
		return data;
	}
	
	@Override
	public String toString(){
		return string;
	}

}
