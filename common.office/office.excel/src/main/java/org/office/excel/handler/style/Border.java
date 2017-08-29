package org.office.excel.handler.style;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Border implements StyleHandler {
	private static final Logger logger = LoggerFactory.getLogger(Border.class);
	public final static String split=",";

	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {
		String [] styles=style.split(Border.split);
		if(styles==null||styles.length<=0){
			throw new RuntimeException("");
		}

		String location="";
		String styleValue="";
		if(styles.length==1&&StringUtils.isNotBlank(styles[0])){
			styleValue=styles[0];
		}else
		if(styles.length==2&&StringUtils.isNotBlank(styles[1])){
			location=styles[0];
			styleValue=styles[1];
		}
		
		if(StringUtils.isBlank(styleValue)){
			throw new RuntimeException("");
		}
		
		logger.info("StyleServiceImpl.border(cell={},location={},styleValue={},cellStyle={})",cell,location,styleValue,cellStyle);
		logger.info(String.format(" running border(cell[%d,%d],location[%s],styleValue['%s'])", cell.getRowIndex(),cell.getColumnIndex(),location,styleValue));
		if(cellStyle==null){
			cellStyle=cell.getSheet().getWorkbook().createCellStyle();
		}
		
		short shortStyle=Short.valueOf(styleValue);
		switch (location) {
		case "BOTTOM":
			cellStyle.setBorderBottom(shortStyle); // 下边框
			break;

		case "LEFT":
			cellStyle.setBorderLeft(shortStyle);// 左边框
			break;

		case "TOP":
			cellStyle.setBorderTop(shortStyle);// 上边框
			break;

		case "RIGHT":
			cellStyle.setBorderRight(shortStyle);// 右边框
			break;
		default:
			cellStyle.setBorderRight(shortStyle);// 右边框
			cellStyle.setBorderTop(shortStyle);// 上边框
			cellStyle.setBorderBottom(shortStyle); // 下边框
			cellStyle.setBorderLeft(shortStyle);// 左边框EE
		}
		return cellStyle;
	}

	public String getStyleName() {
		return Style.Border.name;
	}

}
