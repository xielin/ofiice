package org.office.excel.api.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.office.excel.api.ExcelParseService;
import org.office.excel.config.ExcelField;
import org.office.excel.exception.ExcelCellException;
import org.office.excel.exception.ExcelDataTypeHandlerException;
import org.office.excel.exception.ExcelIoInputException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelValidateException;
import org.office.excel.handler.DataHandler;
import org.office.excel.handler.DataTypeHandler;
import org.office.excel.handler.ValidateHandler;
import org.office.excel.handler.datahandler.DataHandlerService;
import org.office.excel.handler.datatypehandler.DataTypeHandlerService;
import org.office.excel.handler.validatehandler.ValidateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelParseServiceImpl implements ExcelParseService {

	private static final Logger logger = LoggerFactory
			.getLogger(ExcelParseServiceImpl.class);

	public final static int PUSHBACKREADER_BUFFER_SIZE = 8;
	public final static int ROW_ACCESS_WINDOW_SIZE = 100;

	private DataTypeHandlerService dataTypeService;

	private DataTypeHandlerService getDataTypeService() {
		if (dataTypeService != null) {
			return dataTypeService;
		}
		ServiceLoader<DataTypeHandlerService> dataTypeServiceLoader = ServiceLoader
				.load(DataTypeHandlerService.class);
		for (DataTypeHandlerService dataTypeService : dataTypeServiceLoader) {
			if (dataTypeService != null) {
				this.dataTypeService = dataTypeService;
				break;
			}
		}
		return dataTypeService;
	}

	// 数据转换
	private DataHandlerService dataHandlerService;

	private DataHandlerService getDataHandlerService() {
		logger.info("ExcelExportServiceImpl.getDataHandlerService()");
		if (dataHandlerService != null) {
			logger.info("return the has exists dataHandlerService");
			return dataHandlerService;
		}
		ServiceLoader<DataHandlerService> dataTypeServiceLoader = ServiceLoader
				.load(DataHandlerService.class);
		for (DataHandlerService dataHandlerService : dataTypeServiceLoader) {
			logger.info("using ServiceLoader to load  DataHandlerService ");
			if (dataHandlerService != null) {
				this.dataHandlerService = dataHandlerService;
				break;
			}
		}
		return dataHandlerService;
	}

	private ValidateHandlerService validateService;

	private ValidateHandlerService getValidateService() {
		logger.info("ExcelExportServiceImpl.getValidateService()");
		if (validateService != null) {
			logger.info("return the has exists validateService");
			return validateService;
		}

		ServiceLoader<ValidateHandlerService> validateServiceLoader = ServiceLoader
				.load(ValidateHandlerService.class);
		for (ValidateHandlerService validateService : validateServiceLoader) {
			logger.info("using ServiceLoader to load  validateService ");
			if (validateService != null) {
				this.validateService = validateService;
				break;
			}
		}
		return validateService;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object>[] parse(InputStream inputStream,
			List<ExcelField> configs) throws ExcelIoInputException,
			ExcelDataTypeHandlerException, ExcelCellException,
			ExcelNotFoundHandlerException, ExcelValidateException {

		logger.info("ExExcelServiceImpl.parse(inputStream={},configs={})",
				inputStream, configs);
		Map[] dataMap = null;
		if (inputStream == null) {
			logger.error("the param inputStream of the method of ExExcelServiceImpl.parse(inputStream,configs) is null ");
			throw new IllegalArgumentException(
					"the param inputStream of the method of ExExcelServiceImpl.parse(inputStream,configs) is null ");
			// throw new ExcelException("the parameter inputStream is null");
		}
		if (configs == null) {
			logger.error("the param configs of the method of ExExcelServiceImpl.parse(inputStream,configs) is null ");
			throw new IllegalArgumentException(
					"the param configs of the method of ExExcelServiceImpl.parse(inputStream,configs) is null ");

		}
		if (configs.size() <= 0) {
			logger.error("the param configs of the method of ExExcelServiceImpl.parse(inputStream,configs)  less then zero ");
			throw new IllegalArgumentException(
					"the param configs of the method of ExExcelServiceImpl.parse(inputStream,configs)  less then zero ");

		}
		ExcelField config = configs.get(0);
		Workbook workBook = readWorkbookByInputStream(inputStream);
		Sheet sheet = readSheetByWorkbook(workBook, config.getSheetIndex());
		int rowNum = sheet.getLastRowNum();
		int dataSize = rowNum - config.getDataIndex();
		dataMap = new Map[dataSize];// 定义数据空间大小
		Map<Object, Object> bean = null;
		Row row = null;
		for (int rowIndex = config.getDataIndex(); rowIndex < rowNum; rowIndex++) {
			row = sheet.getRow(rowIndex);
			bean = readRowHandler(row, configs);
			int dataRowIndex = rowIndex - config.getDataIndex();
			dataMap[dataRowIndex] = bean;
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			throw new ExcelIoInputException("closing inputStream error", e);
		}
		return dataMap;
	}

	private Workbook readWorkbookByInputStream(InputStream inputStream)
			throws ExcelIoInputException {
		logger.info(
				"ExExcelServiceImpl.readWorkbookByInputStream(inputStream={})",
				inputStream);
		logger.info("read Workbook By InputStream");
		if (inputStream == null) {
			String msg = "the param inputStream of the method of ExExcelServiceImpl.readWorkbookByInputStream(inputStream)   is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (!inputStream.markSupported()) {
			logger.info("nputStream = new PushbackInputStream(inputStream, 8)");
			inputStream = new PushbackInputStream(inputStream,
					PUSHBACKREADER_BUFFER_SIZE);
		}
		StringBuilder sb = new StringBuilder();
		try {
			if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
				logger.info("POIFSFileSystem.hasPOIFSHeader(inputStream)");
				return new HSSFWorkbook(inputStream);
			}

		} catch (Exception e) {
			sb.append(e.getMessage());
			e.printStackTrace();
		}
		try {
			if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
				logger.info("POIXMLDocument.hasOOXMLHeader(inputStream)");
				return new XSSFWorkbook(OPCPackage.open(inputStream));
			}

		} catch (Exception e) {
			if (!"".equals(sb.toString().trim())) {
				sb.append(" or ");
			}
			sb.append(e.getMessage());
			e.printStackTrace();
		}

		logger.info("the version of excel file can't analyse,reason:"
				+ sb.toString());
		throw new ExcelIoInputException(
				"the version of excel file can't analyse,reason:"
						+ sb.toString());

	}

	private Sheet readSheetByWorkbook(Workbook workbook, int sheetIndex) {
		logger.info("read Sheet By Workbook");
		if (workbook == null) {
			String msg = "the param workbook of the method of ExExcelServiceImpl.readSheetByWorkbook(workbook,sheetIndex)   is null. ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (sheetIndex < 0) {
			String msg = "the param sheetIndex of the method of ExExcelServiceImpl.readSheetByWorkbook(workbook,sheetIndex)   is less then zero. ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		Sheet sheet;
		sheet = workbook.getSheetAt(sheetIndex);
		return sheet;
	}

	private Map<Object, Object> readRowHandler(Row row, List<ExcelField> configs)
			throws ExcelDataTypeHandlerException, ExcelCellException,
			ExcelNotFoundHandlerException, ExcelValidateException {
		logger.info("ExExcelServiceImpl.readRowHandler(row={},configs={})",
				row, configs);
		Map<Object, Object> bean = new HashMap<Object, Object>();
		int colNum = row.getPhysicalNumberOfCells();
		ExcelField config = null;
		Map<Integer, ExcelField> mapConf = convertToColMap(configs);
		Cell cell = null;
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell((short) i);
			logger.info(String.format(
					"reading data  the column['%s'] of excel .", i));
			config = mapConf.get(i);
			if (config == null) {
				logger.info(String
						.format("don't read data  the column['%s'] of excel,because it is not config the column  .",
								i));
				continue;
			}
			Object value = null;
			value = readCellValue(cell, config, i);
			bean.put(config.getFieldName(), value);
		}
		return bean;
	}

	/**
	 * 转换成列索引值和配置对应的hashmap
	 * 
	 * @param configs
	 * @return
	 */
	private Map<Integer, ExcelField> convertToColMap(List<ExcelField> configs) {
		logger.info("ExExcelServiceImpl.convertToColMap(ColumnConfig={})",
				configs);
		Map<Integer, ExcelField> mapConf = new HashMap<Integer, ExcelField>();
		for (ExcelField conf : configs) {
			mapConf.put(conf.getColumnIndex(), conf);
		}
		return mapConf;
	}

	/**
	 * 读取单元格的数据
	 * 
	 * @param cell
	 *            单元格
	 * @param config
	 *            配置
	 * @return 返回单元格的数据
	 * @throws Exception
	 *             异常
	 */
	@SuppressWarnings("rawtypes")
	private Object readCellValue(Cell cell, ExcelField config, int row)
			throws ExcelCellException, ExcelDataTypeHandlerException,
			ExcelNotFoundHandlerException, ExcelValidateException {
		logger.info(
				"ExExcelServiceImpl.readCellValue(cell={},ColumnConfig={})",
				cell, config);
		Object value = null;
		Object cellValue = null;
		if (cell != null) {
			logger.info("three is reading excel cell[" + cell.getRowIndex()
					+ "," + cell.getColumnIndex() + "] value ");
			FormulaEvaluator formulaEval = cell.getRow().getSheet()
					.getWorkbook().getCreationHelper().createFormulaEvaluator();
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				try {
					String str = cell.getRichStringCellValue().getString();
					cellValue = str;
				} catch (Exception e) {
					throw new ExcelCellException(cell.getRowIndex(),
							cell.getColumnIndex(), e.getMessage(), e);
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:
				try {
					if (DateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						cellValue = date;
					} else {
						Double doubleValue = cell.getNumericCellValue();
						cellValue = doubleValue;
					}
				} catch (Exception e) {
					throw new ExcelCellException(cell.getRowIndex(),
							cell.getColumnIndex(), e.getMessage(), e);
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:

				try {
					Boolean booleVlaue = cell.getBooleanCellValue();
					cellValue = booleVlaue;
				} catch (Exception e) {
					throw new ExcelCellException(cell.getRowIndex(),
							cell.getColumnIndex(), e.getMessage(), e);
				}
				break;
			case Cell.CELL_TYPE_FORMULA:

				try {
					Double doubleValue = formulaEval.evaluate(cell)
							.getNumberValue();
					cellValue = doubleValue;
				} catch (Exception e) {
					throw new ExcelCellException(cell.getRowIndex(),
							cell.getColumnIndex(), e.getMessage(), e);
				}
				break;

			case Cell.CELL_TYPE_ERROR:

				try {
					String error = cell.getRichStringCellValue().getString();
					cellValue = error;
				} catch (Exception e) {
					throw new ExcelCellException(cell.getRowIndex(),
							cell.getColumnIndex(), e.getMessage(), e);
				}
				break;

			case Cell.CELL_TYPE_BLANK:

				try {
					cellValue = "";
				} catch (Exception e) {
					throw new ExcelCellException(cell.getRowIndex(),
							cell.getColumnIndex(), e.getMessage(), e);
				}
				break;
			default:
				return null;
			}
			value = cellValue;
		}

		/**
		 * 数据校验
		 */
		ValidateHandlerService validateService = null;
		ValidateHandler validateHandler = null;
		if (config.getValidateHandler() != null) {
			validateHandler = config.getValidateHandler();
		} else if (StringUtils.isNotBlank(config.getValidate())
				&& getValidateService() != null) {
			validateService = getValidateService();
			validateHandler = validateService.find(config.getValidate());
		}
		if (validateHandler != null) {
			boolean validate = validateHandler.parse(value);
			if (!validate) {
				String msg = String
						.format("the value[%s] of cell[%d,%d] can't pass the validate handler [%s].  ",
								value, row, config.getColumnIndex(),
								validateHandler.getClass().getName());
				logger.error(msg);
				throw new ExcelValidateException(validateHandler, value, row,
						config.getColumnIndex(), msg, null);
			}
		}

		// 字段翻译
		DataHandler dataHandler = null;
		if (config.getDataHandler() != null) {
			dataHandler = config.getDataHandler();
		} else if (getDataHandlerService() != null
				&& StringUtils.isNotBlank(config.getDataHandlerName())) {
			logger.info(String.format("DataHandlerService(%s),argtype=%s",
					String.valueOf(value), value.getClass().getName()));
			dataHandler = getDataHandlerService().findHandler(
					config.getDataHandlerName());
		}
		if (dataHandler != null) {
			Object translateValue = dataHandler.parse(String.valueOf(value));
			logger.info(String.format("%s  k=%s,v=%s", config.getFieldName(),
					String.valueOf(translateValue), value));
			value = translateValue;
		}

		// 数据类型转换
		DataTypeHandler hander = null;
		if (config.getDataTypeHandler() != null) {
			hander = config.getDataTypeHandler();
		} else if (getDataTypeService() != null
				&& StringUtils.isNotBlank(config.getDataTypeHandlerName())) {
			logger.info(String.format("DataTypeService(%s),argtype=%s",
					String.valueOf(value), value.getClass().getName()));
			hander = getDataTypeService().find(config.getDataTypeHandlerName());
		}
		if (hander != null) {
			Class<?> paramType = getMethodParamTypes(hander, "converter");
			if (value.getClass() == paramType
					|| paramType.getName().equals("java.lang.Object")) {
				value = hander.parse(String.valueOf(value));
				logger.info(String.format("%s  k=%s,v=%s",
						config.getFieldName(), String.valueOf(value), value));
			} else {
				String message = String
						.format("the DataTypeHandler[%s]'s param of method[disConverter] is not match the field[%s] type",
								hander.getClass().getName(),
								config.getFieldName());
				logger.error(
						"ExExcelServiceImpl.readCellValue(cell={},ColumnConfig={}),%s",
						cell, config, message);
				throw new ExcelDataTypeHandlerException(hander.getClass()
						.getName(), value, row, config.getColumnIndex(),
						message, null);
			}
		}
		return value;
	}

	@SuppressWarnings("rawtypes")
	public Class getMethodParamTypes(Object classInstance, String methodName) {
		Class paramTypes = null;
		Class classInstanceClass = classInstance.getClass();
		Method[] methods = classInstanceClass.getMethods();// 全部方法
		for (int i = 0; i < methods.length; i++) {
			if (methodName.equals(methods[i].getName())) {// 和传入方法名匹配
				Class<?>[] params = methods[i].getParameterTypes();
				paramTypes = params[0];
				break;
			}
		}
		return paramTypes;
	}

	@SuppressWarnings("rawtypes")
	public Class getMethodReturnType(Object classInstance, String methodName) {
		Class paramTypes = null;
		Class classInstanceClass = classInstance.getClass();
		Method[] methods = classInstanceClass.getMethods();// 全部方法
		for (int i = 0; i < methods.length; i++) {
			if (methodName.equals(methods[i].getName())) {// 和传入方法名匹配
				paramTypes = methods[i].getReturnType();
				break;
			}
		}
		return paramTypes;
	}

}
