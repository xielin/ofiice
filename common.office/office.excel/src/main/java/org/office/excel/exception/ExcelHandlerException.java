package org.office.excel.exception;

public class ExcelHandlerException  extends ExcelException{
	private static final long serialVersionUID = 1L;

	public ExcelHandlerException(String handlerName,String errorMsg) {
		this( handlerName, errorMsg, null); 
		this.setCode(EXCEL_HANDLER_EXCEPTION_CODE);
	}
	public ExcelHandlerException(String handlerName,String errorMsg,Exception e) {
		super(errorMsg,e);
		this.handlerName=handlerName;
		this.setCode(EXCEL_HANDLER_EXCEPTION_CODE);
	}
	/**
	 * 处理名称，全称
	 */
	private String handlerName;

	/**
	 * 处理器名称
	 * @return 全称
	 */
	public String getStyleHandlerName() {
		return handlerName;
	}
	/**
	 * 处理器名称
	 * @param handlerName 全称
	 */
	public void setStyleHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

}
