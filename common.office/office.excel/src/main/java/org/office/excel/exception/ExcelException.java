package org.office.excel.exception;

import org.office.common.exception.OfficeException;

public  class ExcelException extends OfficeException{
	

	/**
	 * 基础异常代码
	 */
	public final static String EXCEL_EXCEPTION_CODE="000";
	/**
	 * IO异常代码
	 */
	public final static String EXCEL_IO_EXCEPTION_CODE="100";
	/**
	 * 文件输入异常代码
	 */
	public final static String EXCEL_IO_INPUT_EXCEPTION_CODE="110";
	/**
	 * 文件输出代码异常
	 */
	public final static String EXCEL_IO_OUTPUT_EXCEPTION_CODE="120";
	
	/**
	 * 样式设置异常
	 */
	public final static String EXCEL_STYLE_EXCEPTION_CODE="400";
	
	/**
	 * 数据变换异常代码
	 */
	public final static String EXCEL_HANDLER_EXCEPTION_CODE="200";
	/**
	 * 数据变换异常代码
	 */
	public final static String EXCEL_DATA_HANDLER_EXCEPTION_CODE="210";
	/**
	 * 数据类型变换异常代码
	 */
	public final static String EXCEL_DATATYPE_HANDLER_EXCEPTION_CODE="220";

	/**
	 * 数据变换异常代码
	 */
	public final static String EXCEL_NOTFOUND_HANDLER_EXCEPTION_CODE="250";
	/**
	 * 样式处理器设置异常代码
	 */
	public final static String EXCEL_STYLE_HANDLER_EXCEPTION_CODE="230";
	/**
	 * 校验器处理器设置异常代码
	 */
	public final static String EXCEL_VALIDATE_HANDLER_EXCEPTION_CODE="240";
	/**
	 * 读写单元格常代码
	 */
	public final static String EXCEL_CELL_EXCEPTION_CODE="300";
	
	/**
	 * bean类解释异常代码
	 */
	public final static String EXCEL_BEAN_EXCEPTION_CODE="400";
	
	/**
	 * bean类的属性读写异常代码
	 */
	public final static String EXCEL_BEAN_PROPERTY_EXCEPTION_CODE="410";
	
	/**
	 * 配置异常代码
	 */
	public final static String EXCEL_CONFIG_EXCEPTION_CODE="500";
	
	/**
	 * 注解配置异常
	 */
	public final static String EXCEL_CONFIG_ANNOTATION_EXCEPTION_CODE="510";
	
	
	
	/**
	 * 异常代码
	 */
	private String code;
	/**
	 * 上层异常
	 */
	private Exception exception;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	private static final long serialVersionUID = 1L;
	public ExcelException(String errorMsg,Exception exception){
		super(String.format("%s;%s",errorMsg,exception!=null?exception.getMessage():""),exception);
		this.exception=exception;
	}
	public ExcelException(String errorMsg){
		super(errorMsg);
	}
	public ExcelException(Exception exception){
		super(exception.getMessage(),exception);
		this.exception=exception;
		this.setStackTrace(exception.getStackTrace());
	}
	
	
	

}
