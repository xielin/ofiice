package org.office.excel.handler.stylehandler;

import org.office.excel.exception.ExcelException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelStyleHandlerException;
import org.office.excel.handler.StyleHandler;


public interface StyleHandlerService {
	
	/**
	 * 查找样式处理器
	 * @param styleName 样式名称
	 * @return
	 * @throws ExcelException 
	 */
	public StyleHandler find(String styleName) throws ExcelStyleHandlerException, ExcelNotFoundHandlerException;
	/**
	 * 加载所有样式处理器
	 * @throws ExcelException
	 */
	public void initHandler() throws ExcelException;
	/**
	 * 添加样式处理器到缓存
	 * @param styleHandler 样式处理器
	 * @throws ExcelException
	 */
	public void addHandler(StyleHandler styleHandler) throws ExcelException;
	/**
	 * 通过类全名来实现，未通spi配置的handler加载。
	 * @param fullName 类全名称
	 * @return
	 */
	public StyleHandler initStyleHandlerByName(String fullName)throws ExcelNotFoundHandlerException;
	
}
