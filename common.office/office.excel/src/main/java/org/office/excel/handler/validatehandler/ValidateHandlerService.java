package org.office.excel.handler.validatehandler;

import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.handler.ValidateHandler;


public interface ValidateHandlerService {
	public ValidateHandler find(String name)throws ExcelNotFoundHandlerException;
	public void init();
	public void addHandler(ValidateHandler validateHandler);
	public ValidateHandler initHandlerByName(String fullName)throws ExcelNotFoundHandlerException;
}
