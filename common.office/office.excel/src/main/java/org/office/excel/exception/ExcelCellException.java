package org.office.excel.exception;

public class ExcelCellException extends ExcelException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExcelCellException(String errorMsg, Exception exm) {
		this(0,0,errorMsg, exm);
	}

	/**
	 * 行索引
	 */
	private Integer rowIndex;
	/**
	 * 列索引
	 */
	private Integer colIndex;
	public ExcelCellException(int row,int col,String message,Exception exception) {
		super(message,exception);
		this.rowIndex=row;
		this.colIndex=col;
		this.setCode(EXCEL_CELL_EXCEPTION_CODE);
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
	
	

}
