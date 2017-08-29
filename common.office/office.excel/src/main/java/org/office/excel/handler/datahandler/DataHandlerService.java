package org.office.excel.handler.datahandler;

import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.handler.DataHandler;


public interface DataHandlerService {
	public  DataHandler<?> findHandler(String fieldName)throws ExcelNotFoundHandlerException;
	public void addHandler(DataHandler<?> handler);
	public DataHandler<?> initDataHandlerBuFullName(String fullName)throws ExcelNotFoundHandlerException;
}
