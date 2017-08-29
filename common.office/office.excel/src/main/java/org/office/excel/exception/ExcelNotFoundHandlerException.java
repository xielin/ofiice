package org.office.excel.exception;

public class ExcelNotFoundHandlerException extends ExcelHandlerException{

	private static final long serialVersionUID = 1L;

	public ExcelNotFoundHandlerException(String handlerName,String errorMsg,Exception e) {
		super(handlerName, errorMsg,e);
		super.setCode(EXCEL_NOTFOUND_HANDLER_EXCEPTION_CODE);;
	}

}
