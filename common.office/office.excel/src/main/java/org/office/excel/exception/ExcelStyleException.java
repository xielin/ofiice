package org.office.excel.exception;

public class ExcelStyleException extends ExcelException{

	private static final long serialVersionUID = 1L;

	public ExcelStyleException(Exception exception) {
		super(exception);
		this.setCode(ExcelException.EXCEL_STYLE_EXCEPTION_CODE);
	}
	public ExcelStyleException(String message) {
		super(message);
		this.setCode(ExcelException.EXCEL_STYLE_EXCEPTION_CODE);
	}
	public ExcelStyleException(int row,int col,String errorStyle,String message) {
		this(message);
		this.rowIndex=row;
		this.colIndex=col;
		this.errorStyle=errorStyle;
	}
	
	public ExcelStyleException(int row,int col,String errorStyle,String message,Exception exception) {
		super(message,exception);
		this.setCode(ExcelException.EXCEL_STYLE_EXCEPTION_CODE);
		this.rowIndex=row;
		this.colIndex=col;
		this.errorStyle=errorStyle;
	}
	private String errorStyle;
	private Integer rowIndex;
	private Integer colIndex;
	private Integer sheetIndex;

	public String getErrorStyle() {
		return errorStyle;
	}
	public void setErrorStyle(String errorStyle) {
		this.errorStyle = errorStyle;
	}
	public Integer getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	public Integer getColIndex() {
		return colIndex;
	}
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}
	public Integer getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(Integer sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	
}
