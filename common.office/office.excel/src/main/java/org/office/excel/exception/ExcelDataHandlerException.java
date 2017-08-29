package org.office.excel.exception;

public class ExcelDataHandlerException  extends ExcelHandlerException{
	
	
	public ExcelDataHandlerException(String handlerName, String errorMsg,
			Exception e) {
		super(handlerName, errorMsg, e);
		this.setCode(EXCEL_DATA_HANDLER_EXCEPTION_CODE);
	}
	private static final long serialVersionUID = 1L;

}
