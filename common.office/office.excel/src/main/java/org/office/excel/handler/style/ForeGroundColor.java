package org.office.excel.handler.style;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForeGroundColor implements StyleHandler {
	private static final Logger logger = LoggerFactory.getLogger(ForeGroundColor.class);

	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {
		logger.info("StyleServiceImpl.fillForegroundColor(cell={},styleValue={},setBorder={})",cell,style,cellStyle);
		logger.info(String.format(" running fillForegroundColor(cell[%d,%d],styleValue['%d'])", cell.getRowIndex(),cell.getColumnIndex(),style));
		if(cellStyle==null){
			cellStyle=cell.getSheet().getWorkbook().createCellStyle();
		}
		cellStyle.setFillForegroundColor(Short.valueOf(StringUtils.isNoneBlank(style)?style:"0"));// 设置背景色
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}
	public String getStyleName() {
		return Style.ForeGroundColor.name;
	}

}
