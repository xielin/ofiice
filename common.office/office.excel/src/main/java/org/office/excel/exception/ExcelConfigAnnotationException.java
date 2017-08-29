package org.office.excel.exception;

public class ExcelConfigAnnotationException extends ExcelConfigException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcelConfigAnnotationException(String errorMsg, Exception exm) {
		this(null,errorMsg, exm);
	}
	public ExcelConfigAnnotationException(Class<?> annotation, String errorMsg, Exception exm) {
		super(errorMsg, exm);
		this.annotation= annotation;
		super.setCode(EXCEL_CONFIG_ANNOTATION_EXCEPTION_CODE);
	}
	/**
	 * 分析的注解类
	 */
	private Class<?> annotation;

	public Class<?> getAnnotation() {
		return annotation;
	}
	public void setAnnotation(Class<?> annotation) {
		this.annotation = annotation;
	}
	

}
