package org.office.excel.handler.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FontBoldweight implements StyleHandler {
	private static final Logger logger = LoggerFactory.getLogger(FontBoldweight.class);

	@Override
	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {

		logger.info("StyleServiceImpl.fontBoldweight(cell={},type={},cellStyle={})",cell,style,cellStyle);
		logger.info(String.format(" running fontBoldweight(cell[%d,%d],type[%s])", cell.getRowIndex(),cell.getColumnIndex(),style));
		
		if(cellStyle==null){
			cellStyle=cell.getSheet().getWorkbook().createCellStyle();
		}
		Font font = cell.getSheet().getWorkbook().createFont();
		font.setBoldweight(Short.valueOf(style));// 粗体显示
		cellStyle.setFont(font);// 选择需要用到的字体格式
		return cellStyle;
	}

	@Override
	public String getStyleName() {
		return Style.FontBoldweight.name;
	}

}
