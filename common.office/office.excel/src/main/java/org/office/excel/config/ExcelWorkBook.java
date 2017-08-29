package org.office.excel.config;

import java.util.List;

public class ExcelWorkBook {
	private List<ExcelSheet> sheets;
	private String name;
	public List<ExcelSheet> getSheets() {
		return sheets;
	}
	public void setSheets(List<ExcelSheet> sheets) {
		this.sheets = sheets;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
