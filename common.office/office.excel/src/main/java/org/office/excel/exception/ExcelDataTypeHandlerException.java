package org.office.excel.exception;

public class ExcelDataTypeHandlerException extends ExcelHandlerException{
	
	
	public ExcelDataTypeHandlerException(String handlerName, String errorMsg,
			Exception e) {
		super(handlerName, errorMsg, e);
		this.setCode(EXCEL_DATATYPE_HANDLER_EXCEPTION_CODE);
	}
	
	
	
	
	
	public ExcelDataTypeHandlerException(String handlerName,Object target, int row,int col, String errorMsg,
			Exception e) {
		this(handlerName, errorMsg, e);
		this.target = target;
		this.row = row;
		this.col = col;
	}
	
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private Object target;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	

	

}
