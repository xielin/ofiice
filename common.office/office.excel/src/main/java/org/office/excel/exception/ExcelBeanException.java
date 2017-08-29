package org.office.excel.exception;

public class ExcelBeanException extends ExcelException{
	
	private static final long serialVersionUID = 1L;

	public ExcelBeanException(String errorMsg, Exception exm) {
		super(errorMsg, exm);
		
	}
	public ExcelBeanException(Object target,String errorMsg, Exception exm) {
		this(target==null?null:target.getClass(),target,errorMsg, exm);
		
	}
	public ExcelBeanException(Class<?> beanClass,Object target,String errorMsg, Exception exm) {
		super(errorMsg, exm);
		this.target = target;
		this.beanClass=beanClass;
		this.setCode(EXCEL_BEAN_EXCEPTION_CODE);
	}
	/**
	 * 转换目的对象
	 */
	private Object target;
	/**
	 * 目标类 
	 */
	private Class<?> beanClass;
	public Class<?> getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	
	

}
