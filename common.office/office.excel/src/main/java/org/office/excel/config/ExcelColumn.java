package org.office.excel.config;

import org.office.excel.handler.DataHandler;
import org.office.excel.handler.DataTypeHandler;
import org.office.excel.handler.StyleHandler;
import org.office.excel.handler.ValidateHandler;

public class ExcelColumn {
	
	
	/**
	 * 标题
	 * @return
	 */
	private String fieldTitle;
	
	
	private String fieldName;

	/**
	 * 所在的列索引
	 * @return
	 */
	private int columnIndex;
	private String format;
	
	

	private String dataHandlerName;
	private DataHandler<?> dataHandler;
	
	
	
	
	private String fieldStyle;
	private String styleHandlerName;
	private StyleHandler styleHandler;
	
	
	
	
	private String dataTypeHandlerName;
	private DataTypeHandler<?> dataTypeHandler;
	
	
	
	private String validate;
	private ValidateHandler  validateHandler;
	
	
	
	
	public String getFieldTitle() {
		return fieldTitle;
	}
	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getDataHandlerName() {
		return dataHandlerName;
	}
	public void setDataHandlerName(String dataHandlerName) {
		this.dataHandlerName = dataHandlerName;
	}
	public DataHandler<?> getDataHandler() {
		return dataHandler;
	}
	public void setDataHandler(DataHandler<?> dataHandler) {
		this.dataHandler = dataHandler;
	}
	public String getFieldStyle() {
		return fieldStyle;
	}
	public void setFieldStyle(String fieldStyle) {
		this.fieldStyle = fieldStyle;
	}
	public String getStyleHandlerName() {
		return styleHandlerName;
	}
	public void setStyleHandlerName(String styleHandlerName) {
		this.styleHandlerName = styleHandlerName;
	}
	public StyleHandler getStyleHandler() {
		return styleHandler;
	}
	public void setStyleHandler(StyleHandler styleHandler) {
		this.styleHandler = styleHandler;
	}
	public String getDataTypeHandlerName() {
		return dataTypeHandlerName;
	}
	public void setDataTypeHandlerName(String dataTypeHandlerName) {
		this.dataTypeHandlerName = dataTypeHandlerName;
	}
	public DataTypeHandler<?> getDataTypeHandler() {
		return dataTypeHandler;
	}
	public void setDataTypeHandler(DataTypeHandler<?> dataTypeHandler) {
		this.dataTypeHandler = dataTypeHandler;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public ValidateHandler getValidateHandler() {
		return validateHandler;
	}
	public void setValidateHandler(ValidateHandler validateHandler) {
		this.validateHandler = validateHandler;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	
	
}
