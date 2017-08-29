package org.office.excel.handler.stylehandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.office.excel.exception.ExcelException;
import org.office.excel.exception.ExcelStyleException;
import org.office.excel.exception.ExcelStyleHandlerException;
import org.office.excel.handler.StyleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class StyleHandlerFactory {
	static StyleHandlerFactory  factory=new StyleHandlerFactory();
	public static StyleHandlerFactory factory(){
		return factory;
	}
	private StyleHandlerFactory(){}

	/**
	 * 多个样式分离标志符
	 */
	private final static String splits = ";";
	/**
	 * 样式的key和值的分离标识符
	 */
	private final static String split = ":";

	private static final Logger logger = LoggerFactory.getLogger(StyleHandlerFactory.class);
	
	
	private StyleHandlerService styleHandlerService;
	private StyleHandlerService getStyleHandlerService() {
		logger.info("ExExcelServiceImpl.getDataHandlerService()");
		if (styleHandlerService != null) {
			logger.info("return the has exists dataHandlerService");
			return styleHandlerService;
		}
		ServiceLoader<StyleHandlerService> styleHandlerServiceLoader = ServiceLoader
				.load(StyleHandlerService.class);
		for (StyleHandlerService styleHandlerService : styleHandlerServiceLoader) {
			logger.info("using ServiceLoader to load  DataHandlerService ");
			if (styleHandlerService != null) {
				this.styleHandlerService = styleHandlerService;
				break;
			}
		}
		return styleHandlerService;
	}
	
	public   CellStyle handler(Cell cell,String style, CellStyle cellStyle) throws ExcelStyleException, ExcelStyleHandlerException {
		logger.info("StyleServiceImpl.handler(cell={},style={},cellStyle={})",cell,style,cellStyle);
		if(cellStyle==null){
			cellStyle=cell.getSheet().getWorkbook().createCellStyle();
		}
		logger.info(String.format("setting style[%s] into cell[%d,%d] ", style,cell.getRowIndex(), cell.getColumnIndex()));
		if (StringUtils.isNotBlank(style)) {
			String[] stylesKeyAndValue = null;
			String[] styleKeyValue     = null;
			String styleValue          = null;
			StyleHandler handler       = null;
			Set<String> keySet         = null;
			stylesKeyAndValue = style.split(splits);//分离出每个样式
			Map<String, String> styleKeyAndValueMap = new HashMap<String, String>();//样式存储
			
			for (String styleKeyValueStr : stylesKeyAndValue) {//分离所有样式数据
				logger.info(String.format("parsing style[%s] into cell[%d,%d] ", styleKeyValueStr,cell.getRowIndex(), cell.getColumnIndex()));
				styleKeyValue = styleKeyValueStr.split(split);//分离样式的key和value
				if(styleKeyValue==null||styleKeyValue.length!=2){
					logger.error(String.format("cell[%d,%d]'s style[%s] is illegal,style is the style's [key:value]",cell.getRowIndex(),cell.getColumnIndex(),styleKeyValueStr));
					throw new ExcelStyleException(cell.getRowIndex(),cell.getColumnIndex(),styleKeyValueStr,String.format("cell[%d,%d]'s style[%s] is illegal,style must be the style's [key:value]",cell.getRowIndex(),cell.getColumnIndex(),styleKeyValueStr));
				}
				styleKeyAndValueMap.put(styleKeyValue[0], styleKeyValue[1]);
			}
			
			keySet = styleKeyAndValueMap.keySet();//获取样式的key的set
			
			for (String styleKey : keySet) {//分别处理每个样式
				styleValue = styleKeyAndValueMap.get(styleKey);
				logger.info(String.format("setting style[key=%s,value=%s] into cell[%d,%d] ", styleKey,styleValue,cell.getRowIndex(), cell.getColumnIndex()));
				logger.info(String.format("setting user-defined style[key=%s,value=%s] into cell[%d,%d] ", styleKey,styleValue,cell.getRowIndex(), cell.getColumnIndex()));
				try {
					handler = getStyleHandlerService().find(styleKey);
					if(handler!=null){
						cellStyle =	handler.handler(cell, styleValue, cellStyle);
					}
				} catch (ExcelException e) {
					String msg=String.format("call the name[%s] cell[%d,%d]'s style[key=%s,value=%s] handler error, ", styleKey,cell.getRowIndex(),cell.getColumnIndex(),styleKey,styleValue,e.getDetailMsg());
					logger.error(msg);
					ExcelStyleHandlerException ex = null;
					if(handler!=null){
						ex=new ExcelStyleHandlerException(handler.getClass().getName(),String.format("%s:%s", styleKey,styleValue),msg,e);
					}else{
						ex=new ExcelStyleHandlerException(null,String.format("%s:%s", styleKey,styleValue),msg,e);
					}
					throw ex; 
				}
			}
		}
		return cellStyle;
	}

}
