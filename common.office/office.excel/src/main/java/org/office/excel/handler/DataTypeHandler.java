package org.office.excel.handler;

public interface DataTypeHandler <K>{
	
	
	/**
	 * 将字符串转换为复杂类型，可以是类，枚举等等，<p>读取Excel调用</p>
	 * @param str 字符串
	 * @return 复杂类型
	 */
	public K parse(String v	);
	/**
	 * 将复杂类型转换为字符串<p>导出Excel调用</p>
	 * @param t 复杂类型,可以是类，枚举等等
	 * @return 字符串
	 */
	public String export(K k);
	
	/**
	 * 配置的转换器名称
	 * @return
	 */
	public String getHandlerName();
}
