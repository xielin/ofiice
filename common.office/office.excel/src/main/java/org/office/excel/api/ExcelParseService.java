package org.office.excel.api;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.office.excel.config.ExcelField;
import org.office.excel.exception.ExcelCellException;
import org.office.excel.exception.ExcelDataTypeHandlerException;
import org.office.excel.exception.ExcelException;
import org.office.excel.exception.ExcelIoInputException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelValidateException;

public interface ExcelParseService {
	/**
	 * 解释数据
	 * @param inputStream
	 * @param configs
	 * @param targetType
	 * @return
	 * @throws ExcelException
	 */
	public  Map<String,Object>[] parse(InputStream inputStream, List<ExcelField> configs) throws ExcelIoInputException, ExcelDataTypeHandlerException, ExcelCellException, ExcelNotFoundHandlerException , ExcelValidateException ;
	
}
