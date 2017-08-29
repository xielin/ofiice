package org.office.excel.handler.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FontName implements StyleHandler {
	private static final Logger logger = LoggerFactory.getLogger(FontName.class);
	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {

		logger.info("StyleServiceImpl.fontName(cell={},name={},setBorder={})",cell,style,cellStyle);
		logger.info(String.format(" running fontName(cell[%d,%d],name[%s])", cell.getRowIndex(),cell.getColumnIndex(),cellStyle));
		
		if(cellStyle==null){
			cellStyle=cell.getSheet().getWorkbook().createCellStyle();
		}
		Font font = cell.getSheet().getWorkbook().createFont();
		font.setFontName(style);
		cellStyle.setFont(font);
		return cellStyle;
	}
	public String getStyleName() {
		return Style.FontName.name;
	}
}
