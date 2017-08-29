package org.office.excel.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
public abstract interface StyleHandler {
	/**
	 * 样式设置出来
	 * @param cell 单元格
	 * @param style 样式值
	 * @param cellStyle
	 * @return
	 */
	public CellStyle handler(Cell cell,String style,CellStyle cellStyle);
	/**
	 * 样式设置器名称，唯一值
	 * @return
	 */
	public String getStyleName();
}
