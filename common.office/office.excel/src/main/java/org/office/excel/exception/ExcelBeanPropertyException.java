package org.office.excel.exception;

public class ExcelBeanPropertyException extends ExcelBeanException{

	private static final long serialVersionUID = 1L;
	/**
	 * 要操作的对象或者类的字段属性
	 */
    private String property;

	
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	public ExcelBeanPropertyException(String errorMsg, Exception exm) {
		this(null,"",errorMsg, exm);
	}
	
	public ExcelBeanPropertyException(Object target,String property,String errorMsg, Exception exm) {
		super(errorMsg, exm);
		this.property=property;
		super.setTarget(target);
		this.setCode(EXCEL_BEAN_PROPERTY_EXCEPTION_CODE);
	}

}
