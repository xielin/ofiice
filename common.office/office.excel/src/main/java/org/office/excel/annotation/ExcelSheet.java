package org.office.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSheet {
	/**
	 * workbook的表格索引
	 * @return
	 */
	public int sheetIndex() default 0;

	/**
	 * workbook的表格名称
	 * @return
	 */
	public String sheetName() default "";

	/**
	 * 表格的表头索引
	 * @return
	 */
	public int titleIndex() default 0;

	/**
	 * 表格的数据索引
	 * @return
	 */
	public int dataIndex() default 1;

	/**
	 * 导出时当实际数据大于此值，丢弃样式
	 * @return
	 */
	public int abandonStyleCount() default 5000;
}
