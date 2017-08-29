package org.office.excel.handler;

/**
 * <p>主要是用在字典转换</p>
 * @author Dell
 *
 * @param <SOURSE> 类中的字段类型
 * @param <TARGET> Excel展示类型
 */
public interface DataHandler<TARGET> {
	/**
	 * 要数据转换的列的名称
	 * @return
	 */
	public String getHandlerName();
	/**
	 * 转换到Excel单元格的内容<p>导出Excel调用</p>
	 * @param s
	 * @return
	 */
	public String export(TARGET s);
	/**
	 * 转换到类对象中的字段内容<p>读取Excel调用</p>
	 * @param t
	 * @return
	 */
	public TARGET parse(String t);
}
