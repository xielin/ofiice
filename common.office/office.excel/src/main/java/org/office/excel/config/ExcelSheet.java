package org.office.excel.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel电表表格配置信息
 * 
 * @author Dell
 *
 */
public class ExcelSheet {
	/**
	 * workbook的表格索引
	 * 
	 * @return
	 */
	private int sheetIndex;

	/**
	 * workbook的表格名称
	 * 
	 * @return
	 */
	private String sheetName;

	/**
	 * 表格的表头索引
	 * 
	 * @return
	 */
	private int titleIndex;

	/**
	 * 表格的数据索引
	 * 
	 * @return
	 */
	private int dataIndex;

	/**
	 * 列
	 */
	private List<ExcelColumn> columns;

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getTitleIndex() {
		return titleIndex;
	}

	public void setTitleIndex(int titleIndex) {
		this.titleIndex = titleIndex;
	}

	public int getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int dataIndex) {
		this.dataIndex = dataIndex;
	}

	public List<ExcelColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<ExcelColumn> columns) {
		this.columns = columns;
	}

	private int abandonStyleCount;

	public int getAbandonStyleCount() {
		return abandonStyleCount;
	}

	public void setAbandonStyleCount(int abandonStyleCount) {
		this.abandonStyleCount = abandonStyleCount;
	}

	public List<ExcelField> toExcelField() {
		List<ExcelField> list = new ArrayList<ExcelField>();
		if (columns != null && columns.size() > 0) {
			ExcelField excelField = null;
			for (ExcelColumn column : columns) {
				excelField = new ExcelField();
				excelField.setAbandonStyleCount(abandonStyleCount);
				excelField.setColumnIndex(column.getColumnIndex());
				excelField.setDataHandler(column.getDataHandler());
				excelField.setDataHandlerName(column.getDataHandlerName());
				excelField.setDataIndex(dataIndex);
				excelField.setDataTypeHandler(column.getDataTypeHandler());
				excelField.setDataTypeHandlerName(column.getDataTypeHandlerName());
				excelField.setFieldName(column.getFieldName());
				excelField.setFieldStyle(column.getFieldStyle());
				excelField.setFieldTitle(column.getFieldTitle());
				excelField.setFormat(column.getFormat());
				excelField.setSheetIndex(sheetIndex);
				excelField.setSheetName(sheetName);
				excelField.setStyleHandler(column.getStyleHandler());
				excelField.setStyleHandlerName(column.getStyleHandlerName());
				excelField.setTitleIndex(titleIndex);
				excelField.setValidate(column.getValidate());
				excelField.setValidateHandler(column.getValidateHandler());
				list.add(excelField);
			}
		} else {
			return null;
		}
		return list;
	}

}
