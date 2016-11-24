package huawangxin.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableRow {
	private Map<String, TableColumn> row = new LinkedHashMap<String,TableColumn>();
	
	public List<TableColumn> getList(){
		return new ArrayList<TableColumn>(row.values());
	}
	
	public Map<String, TableColumn> getRow() {
		return row;
	}

	public void setRow(Map<String, TableColumn> row) {
		this.row = row;
	}
	
	public void addColumn (TableColumn column) {
		row.put(column.getColumnName(), column);
	}

	public TableColumn getTableColumn(String columnName) {
		 return row.get(columnName);
	}
}
