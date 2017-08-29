package org.office.excel.handler.datatypehandler;

import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.handler.DataTypeHandler;


public interface DataTypeHandlerService {
	public DataTypeHandler<?> find(String handlerName)throws ExcelNotFoundHandlerException;
	public void add(DataTypeHandler<?> dataTypeHandler);
	public void initHandler();
	public DataTypeHandler<?> initHnadlerByFullName(String fullName)throws ExcelNotFoundHandlerException;
}
