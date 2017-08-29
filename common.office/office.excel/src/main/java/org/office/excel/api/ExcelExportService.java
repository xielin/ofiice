package org.office.excel.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.office.excel.config.ExcelField;
import org.office.excel.exception.ExcelCellException;
import org.office.excel.exception.ExcelDataHandlerException;
import org.office.excel.exception.ExcelDataTypeHandlerException;
import org.office.excel.exception.ExcelException;
import org.office.excel.exception.ExcelIoException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelStyleException;
import org.office.excel.exception.ExcelStyleHandlerException;
import org.office.excel.exception.ExcelValidateException;

public interface ExcelExportService {
	/**
	 * 导出Excel
	 * @param outputStream 导出的文件流
	 * @param configs 列表配置信息
	 * @param list 数据信息
	 * @param tempalteInputStream 模板文件的输入流
	 * @throws ExcelException
	 */
	public  void export(OutputStream outputStream, List<ExcelField> configs,  Map<String,Object>[] maps,InputStream tempalteInputStream) throws ExcelStyleHandlerException, ExcelStyleException, ExcelDataTypeHandlerException, ExcelDataHandlerException, ExcelCellException, ExcelIoException , ExcelNotFoundHandlerException , ExcelValidateException ;

	
}
