package org.office.excel.handler.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnWidth implements StyleHandler {

	private static final Logger logger = LoggerFactory.getLogger(ColumnWidth.class);
	@Override
	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {

		
		logger.info("StyleServiceImpl.fontBoldweight(cell={},col={},width={},cellStyle={})",cell,cell.getColumnIndex(),style,cellStyle);
		logger.info(String.format(" running columnWidth(cell[%d,%d],col[%d],width[%s])", cell.getRowIndex(),cell.getColumnIndex(),cell.getColumnIndex(),style));
		
		int width=Integer.valueOf(style);
		width=width*2*256;
		cell.getSheet().setColumnWidth(cell.getColumnIndex(), width);
		return cellStyle;
	}

	@Override
	public String getStyleName() {
		// TODO Auto-generated method stub
		return Style.ColumnWidth.name;
	}

}
