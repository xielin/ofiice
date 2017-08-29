package org.office.excel.handler.style;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Format implements StyleHandler {
	private static final Logger logger = LoggerFactory.getLogger(Format.class);

	@Override
	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {
		logger.info("FormatServiceImpl.writeHandler(String formate={}, Cell cell={},CellStyle cellStyle={})",style,cell,cellStyle);
		logger.info(String.format("formate style[%s] into cell[%d,%d] ", style,cell.getRowIndex(),cell.getColumnIndex()));
		if(cellStyle==null){
			cellStyle = cell.getSheet().getWorkbook().createCellStyle();
		}
		DataFormat format = cell.getSheet().getWorkbook().createDataFormat();
		if (StringUtils.isNoneBlank(style)) {
			cellStyle.setDataFormat(format.getFormat(style.trim()));
		}
		return cellStyle;
	}

	@Override
	public String getStyleName() {
		return Style.Format.name;
	}

}
