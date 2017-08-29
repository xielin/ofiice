package org.office.excel.handler.style;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alignment implements StyleHandler {
	private static final Logger logger = LoggerFactory.getLogger(Alignment.class);
	public final static String split=",";
	@Override
	public CellStyle handler(Cell cell, String style, CellStyle cellStyle) {
		
		String [] styles=style.split(Alignment.split);
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
		
		
		
		logger.info("StyleServiceImpl.alignment(cell={},location={},cellStyle={})",cell,location,cellStyle);
		logger.info(String.format(" running border(cell[%d,%d],location[%s])", cell.getRowIndex(),cell.getColumnIndex(),location));
		
		if(cellStyle==null){
			cellStyle=cell.getSheet().getWorkbook().createCellStyle();
		}
		if ("ALIGN_CENTER".equals(location)) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		}
		switch (location) {
		case "ALIGN_RIGHT":
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			break;
		case "ALIGN_CENTER":
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			break;
		case "ALIGN_LEFT":
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			break;
		default:
			cellStyle.setAlignment(CellStyle.ALIGN_GENERAL);
			break;
		}
		return cellStyle;
	}

	@Override
	public String getStyleName() {
		return Style.Alignment.name;
	}

}
