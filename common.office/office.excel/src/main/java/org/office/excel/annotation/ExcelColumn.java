package org.office.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
	/**
	 * 标题
	 * @return
	 */
	public String fieldTitle();

	/**
	 * 所在的列索引
	 * @return
	 */
	public int columnIndex();
}
