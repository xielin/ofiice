package org.office.excel.exception;

public class ExcelStyleHandlerException extends ExcelHandlerException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcelStyleHandlerException(String styleHandlerName, String errorMsg) {
		super(styleHandlerName, errorMsg);
		this.setCode(EXCEL_STYLE_HANDLER_EXCEPTION_CODE);
	}
	public ExcelStyleHandlerException(String styleHandlerName,String handlerStyle, String errorMsg) {
		super(styleHandlerName, errorMsg);
		this.handlerStyle=handlerStyle;
		this.setCode(EXCEL_STYLE_HANDLER_EXCEPTION_CODE);
	}
	public ExcelStyleHandlerException(String styleHandlerName,String handlerStyle, String errorMsg,Exception e) {
		super(styleHandlerName, errorMsg,e);
		this.handlerStyle=handlerStyle;
		this.setCode(EXCEL_STYLE_HANDLER_EXCEPTION_CODE);
	}
	private String handlerStyle;

	public String getHandlerStyle() {
		return handlerStyle;
	}

	public void setHandlerStyle(String handlerStyle) {
		this.handlerStyle = handlerStyle;
	}
	
}
