package org.office.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.office.excel.api.ExcelExportService;
import org.office.excel.api.ExcelParseService;
import org.office.excel.api.export.ExcelExportServiceImpl;
import org.office.excel.api.parse.ExcelParseServiceImpl;
import org.office.excel.config.ExcelField;
import org.office.excel.config.ExcelSheet;
import org.office.excel.exception.ExcelCellException;
import org.office.excel.exception.ExcelDataHandlerException;
import org.office.excel.exception.ExcelDataTypeHandlerException;
import org.office.excel.exception.ExcelIoException;
import org.office.excel.exception.ExcelIoInputException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelStyleException;
import org.office.excel.exception.ExcelStyleHandlerException;
import org.office.excel.exception.ExcelValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(ExcelUtil.class);
	private static ExcelParseService parse=new ExcelParseServiceImpl();
	private static ExcelExportService export = new ExcelExportServiceImpl();
	
	
	public  void export(OutputStream outputStream, List<ExcelField> configs,  Map<String,Object>[] maps,InputStream tempalteInputStream) throws ExcelStyleHandlerException, ExcelStyleException, ExcelDataTypeHandlerException, ExcelDataHandlerException, ExcelCellException, ExcelIoException , ExcelNotFoundHandlerException , ExcelValidateException {
		export.export(outputStream, configs, maps, tempalteInputStream);
	}
	
	public  void export(OutputStream outputStream, ExcelSheet config,  Map<String,Object>[] maps,InputStream tempalteInputStream) throws ExcelStyleHandlerException, ExcelStyleException, ExcelDataTypeHandlerException, ExcelDataHandlerException, ExcelCellException, ExcelIoException , ExcelNotFoundHandlerException , ExcelValidateException {
		if (config == null) {
			String msg="the param configs of the method of ExcelUtil.export(OutputStream outputStream, ExcelSheet config,  Map<String,Object>[] maps,InputStream tempalteInputStream) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		export(outputStream, config.toExcelField(), maps, tempalteInputStream);
	}
	
	public  Map<String,Object>[] parse(InputStream inputStream, List<ExcelField> configs) throws ExcelIoInputException, ExcelDataTypeHandlerException, ExcelCellException, ExcelNotFoundHandlerException , ExcelValidateException {
		return parse.parse(inputStream, configs);
	}
	public  Map<String,Object>[] parse(InputStream inputStream, ExcelSheet config) throws ExcelIoInputException, ExcelDataTypeHandlerException, ExcelCellException, ExcelNotFoundHandlerException , ExcelValidateException {
		return parse(inputStream, config.toExcelField());
	}
}
