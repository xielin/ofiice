package org.office.excel.exception;

import org.office.excel.handler.ValidateHandler;


public class ExcelValidateException  extends ExcelException{
	
	private static final long serialVersionUID = 1L;
	public ExcelValidateException(String errorMsg, Exception exception) {
		this(null,null,errorMsg, exception);
	}
	
	public ExcelValidateException(ValidateHandler handler,Object target,String errorMsg, Exception exception) {
		super(errorMsg, exception);
		this.target=target;
		this.validateHandler=handler;
	}
	
	
	public ExcelValidateException(ValidateHandler handler,Object target,int row ,int col,String errorMsg, Exception exception) {
		super(errorMsg, exception);
		this.target = target;
		this.validateHandler = handler;
		this.rowIndex = row;
		this.colIndex = col;
	}
	
	private Integer rowIndex;
	private Integer colIndex;
	
	
	
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

	private ValidateHandler validateHandler;

	public ValidateHandler getValidateHandler() {
		return validateHandler;
	}

	public void setValidateHandler(ValidateHandler validateHandler) {
		this.validateHandler = validateHandler;
	}
	/**
	 * 被校验目标
	 */
	private Object target;
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	

}
