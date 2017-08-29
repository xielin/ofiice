package org.office.excel.api.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.office.excel.api.ExcelExportService;
import org.office.excel.config.ExcelField;
import org.office.excel.exception.ExcelCellException;
import org.office.excel.exception.ExcelDataHandlerException;
import org.office.excel.exception.ExcelDataTypeHandlerException;
import org.office.excel.exception.ExcelIOutputException;
import org.office.excel.exception.ExcelIoException;
import org.office.excel.exception.ExcelIoInputException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelStyleException;
import org.office.excel.exception.ExcelStyleHandlerException;
import org.office.excel.exception.ExcelValidateException;
import org.office.excel.handler.DataHandler;
import org.office.excel.handler.DataTypeHandler;
import org.office.excel.handler.ValidateHandler;
import org.office.excel.handler.datahandler.DataHandlerService;
import org.office.excel.handler.datatypehandler.DataTypeHandlerService;
import org.office.excel.handler.style.Style;
import org.office.excel.handler.stylehandler.StyleHandlerFactory;
import org.office.excel.handler.validatehandler.ValidateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelExportServiceImpl implements ExcelExportService{
	public final static int PUSHBACKREADER_BUFFER_SIZE = 8;
	public final static int ROW_ACCESS_WINDOW_SIZE = 100;
	private static final Logger logger = LoggerFactory
			.getLogger(ExcelExportServiceImpl.class);
	
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
	public void export(OutputStream outputStream, List<ExcelField> configs,
			Map<String, Object>[] maps, InputStream tempalteInputStream)
			throws ExcelStyleHandlerException, ExcelStyleException,
			ExcelDataTypeHandlerException, ExcelDataHandlerException,
			ExcelCellException, ExcelIoException,
			ExcelNotFoundHandlerException, ExcelValidateException {
		logger.info(
				"ExcelExportServiceImpl.export(outputStream={}，configs={},maps={},tempalteInputStream={})",
				outputStream, configs, maps, tempalteInputStream);
		if (outputStream == null) {
			String msg = "the param outputStream of the method of ExcelExportServiceImpl.export(outputStream,configs,maps,tempalteInputStream)  is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (configs == null) {
			String msg = "the param configs of the method of ExcelExportServiceImpl.export(outputStream,configs,maps,tempalteInputStream)  is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (configs.size() <= 0) {
			String msg = "the param configs of the method of ExcelExportServiceImpl.export(outputStream,configs,maps,tempalteInputStream)   less than zero ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		;
		if (maps == null || maps.length < 0) {
			String msg = "the param maps of the method of ExcelExportServiceImpl.export(outputStream,configs,maps,tempalteInputStream)   less than zero ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}

		ExcelField config = configs.get(0);
		int dataSize = maps.length;
		Workbook workbook = null;
		Sheet sheet = null;
		int i = 0;
		try {
			if (tempalteInputStream != null) {
				logger.info("here  is running  exportExcel  going tempalteInputStream line");
				workbook = readWorkbookByTemplateInputStream(
						tempalteInputStream, dataSize, config);
				sheet = readSheetByWorkbook(workbook, config.getSheetIndex());
				i = config.getDataIndex();
			} else {
				logger.info("here  is running  exportExcel  going not  tempalteInputStream line");
				workbook = writeWorkbook(dataSize, config);
				sheet = writeSheetByWorkbook(workbook);
				i = writeTitle(sheet, configs);
			}
			Map<String, ExcelField> nameMap = convertToNameMap(configs);
			Boolean hashStyle = true;
			int abandonStyleCount = config.getAbandonStyleCount();
			if (dataSize > abandonStyleCount) {
				hashStyle = false;
			}
			for (Map<String, Object> map : maps) {
				logger.info("handler the bean foreach in jsonArray ");
				Row row = null;
				try {
					row = sheet.getRow(i);
					if (row == null) {
						row = sheet.createRow(i);
					}
				} catch (Exception e) {
					throw new ExcelIoException(
							String.format("create Excel row error ，the template Excel file hash error,change to other template"),
							e);
				}
				writeRow(row, nameMap, map, hashStyle);
				i++;
			}
			try {
				logger.info("taking the excel file write to the  outputStream ");
				workbook.write(outputStream);
			} catch (IOException e) {
				throw new ExcelIoException(
						"taking the excel file to write to the  outputStream  is error ",
						e);
			}
			try {
				logger.info("flush the outputStream ");
				outputStream.flush();
			} catch (IOException e) {
				throw new ExcelIoException(
						"flushing the outputStream  is error ", e);
			}
		} catch (ExcelStyleHandlerException e) {
			throw e;
		} catch (ExcelStyleException e) {
			throw e;
		} catch (ExcelDataTypeHandlerException e) {
			throw e;
		} catch (ExcelDataHandlerException e) {
			throw e;
		} catch (ExcelCellException e) {
			throw e;
		} catch (ExcelIoException e) {
			throw e;
		} catch (ExcelNotFoundHandlerException e) {
			throw e;
		} catch (ExcelValidateException e) {
			throw e;
		} finally {
			try {
				logger.info("close the outputStream ");
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				logger.error("close the tempalteInputStream is error :{}", e);
				throw new ExcelIoException(
						"closing the outputStream  is error ", e);
			}
			try {
				if (tempalteInputStream != null) {
					logger.info("close the tempalteInputStream ");
					tempalteInputStream.close();
				}
			} catch (IOException e) {

				logger.error("close the tempalteInputStream :{}", e);
				throw new ExcelIoException(
						"closing  the tempalteInputStream is error ", e);
			}
			try {
				if (workbook != null) {
					if (workbook instanceof SXSSFWorkbook) {
						SXSSFWorkbook workbook1 = (SXSSFWorkbook) workbook;
						workbook1.dispose();
					} else {
						workbook.close();
					}
				}

			} catch (Exception e) {
				logger.error("dispose or close workbook error:{}", e);
				throw new ExcelIoException("dispose or close workbook error", e);
			}
		}
		
	}
	

	private Workbook readWorkbookByTemplateInputStream(InputStream inputStream,
			int dataCount, ExcelField config) throws ExcelIoInputException {
		logger.info("read Workbook By InputStream");
		if (inputStream == null) {
			String msg = "the param inputStream of the method of ExcelExportServiceImpl.readWorkbookByTemplateInputStream(inputStream,dataCount,config)   is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (!inputStream.markSupported()) {
			logger.info("nputStream = new PushbackInputStream(inputStream, 8)");
			inputStream = new PushbackInputStream(inputStream, PUSHBACKREADER_BUFFER_SIZE);
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
				XSSFWorkbook xssfwork = new XSSFWorkbook(
						OPCPackage.open(inputStream));
				if (dataCount > config.getAbandonStyleCount()) {
					SXSSFWorkbook workbooak = new SXSSFWorkbook(xssfwork,
							ROW_ACCESS_WINDOW_SIZE);
					return workbooak;
				} else {

					return xssfwork;
				}
			}

		} catch (Exception e) {
			if (!"".equals(sb.toString().trim())) {
				sb.append(" or ");
			}
			sb.append(e.getMessage());
			e.printStackTrace();
		}

		logger.error("the version of excel file can't analyse,reason:"
				+ sb.toString());
		throw new ExcelIoInputException(
				"the version of excel file can't analyse,reason:"
						+ sb.toString());
	}
	
	/**
	 * 转换成名字和配置对应的hashmap
	 * 
	 * @param configs
	 * @return
	 */
	private Map<String, ExcelField> convertToNameMap(
			List<ExcelField> configs) {
		logger.info("ExcelExportServiceImpl.convertToNameMap(ColumnConfig={})",
				configs);
		Map<String, ExcelField> mapConf = new HashMap<String, ExcelField>();
		for (ExcelField conf : configs) {
			mapConf.put(conf.getFieldName(), conf);
		}
		return mapConf;
	}
	private Sheet readSheetByWorkbook(Workbook workbook, int sheetIndex) {
		logger.info("read Sheet By Workbook");
		if (workbook == null) {
			String msg = "the param workbook of the method of ExcelExportServiceImpl.readSheetByWorkbook(workbook,sheetIndex)   is null. ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (sheetIndex < 0) {
			String msg = "the param sheetIndex of the method of ExcelExportServiceImpl.readSheetByWorkbook(workbook,sheetIndex)   is less then zero. ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		Sheet sheet;
		sheet = workbook.getSheetAt(sheetIndex);
		return sheet;
	}
	private Sheet writeSheetByWorkbook(Workbook workbook) {
		if (workbook == null) {
			String msg = "the param workbook of the method of ExcelExportServiceImpl.writeSheetByWorkbook(workbook)   is null. ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		Sheet sheet = null;
		sheet = workbook.createSheet();
		return sheet;
	}
	private Workbook writeWorkbook(int dataCount, ExcelField config)
			throws ExcelIOutputException {
		Workbook workbook = null;
		StringBuilder message = new StringBuilder();

		try {
			if (dataCount > config.getAbandonStyleCount()) {
				workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
			}
		} catch (Exception e) {
			message.append(e.getMessage());
		}

		if (workbook == null) {
			try {
				workbook = new XSSFWorkbook();
			} catch (Exception e) {
				message.append(e.getMessage());
			}
		}

		if (workbook == null) {
			try {
				workbook = new HSSFWorkbook();
			} catch (Exception e) {
				message.append(e.getMessage());
			}
		}
		if (workbook == null) {
			throw new ExcelIOutputException(
					"creating the excel file operator fail. the reason is :"
							+ message.toString(), null);
		}
		return workbook;
	}
	
	private int writeTitle(Sheet sheet, List<ExcelField> configs)
			throws ExcelStyleHandlerException, ExcelStyleException {
		logger.info(
				"ExcelExportServiceImpl.writeTitle(sheet={}，configs={},config={})",
				sheet, configs);

		ExcelField config = configs.get(0);
		String style="";
		String formatStyle="";
		Row row = sheet.createRow(config.getTitleIndex());
		for (ExcelField conf : configs) {
			Cell cell = row.createCell(conf.getColumnIndex());
			CellStyle cellStyle = null;
			style = conf.getFieldStyle();
			if(StringUtils.isBlank(style)&&StringUtils.isNoneBlank(config.getFormat())){
				style=String.format("%s:%s", Style.Format.name,config.getFormat().trim());
			}else if(StringUtils.isNoneBlank(style)&&StringUtils.isNoneBlank(config.getFormat())){
				formatStyle = String.format("%s:%s", Style.Format.name,config.getFormat().trim());
				style=String.format("%s;%s", formatStyle,style);
			}
			cellStyle = StyleHandlerFactory.factory().handler(cell, style, cellStyle);
			
			
			if (cellStyle != null) {
				cell.setCellStyle(cellStyle);
			}
			cell.setCellValue(conf.getFieldTitle());
		}
		int i = config.getDataIndex();
		return i;
	}
	private void writeRow(Row row, Map<String, ExcelField> nameMap,
			Map<String, Object> map, Boolean style)
			throws ExcelDataTypeHandlerException, ExcelDataHandlerException,
			ExcelStyleHandlerException, ExcelCellException,
			ExcelStyleException, ExcelNotFoundHandlerException,
			ExcelValidateException {
		logger.info(
				"ExcelExportServiceImpl.writeRow(row={}，nameMap={},map={},style={})",
				row, nameMap, map, style);

		if (map == null) {

			String msg = "the param map of the method of ExcelExportServiceImpl.writeRow(Row row,Map<String, ColumnConfig> nameMap, Map<Object,Object> map,Boolean style)  is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		Set<String> keySet = nameMap.keySet();
		for (String name : keySet) {
			ExcelField config = nameMap.get(name);
			Cell cell = row.createCell(config.getColumnIndex());
			Object value = map.get(name);
			writeCell(cell, value, config, style);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void writeCell(Cell cell, Object value, ExcelField config,
			Boolean style) throws ExcelCellException,
			ExcelDataTypeHandlerException, ExcelDataHandlerException,
			ExcelStyleHandlerException, ExcelStyleException,
			ExcelNotFoundHandlerException, ExcelValidateException {
		logger.info(
				"ExcelExportServiceImpl.writeCell(cell={}，value={},config={},style={})",
				cell, value, config, style);
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
			boolean validate = validateHandler.export(value);
			if (!validate) {
				String msg = String
						.format("the cell[%d,%d]'s value[%s] can't pass the exportValidate handler [%s].  ",
								cell.getRowIndex(), cell.getColumnIndex(),
								value, validateHandler.getClass().getName());
				logger.error(msg);
				throw new ExcelValidateException(validateHandler, value,
						cell.getRowIndex(), cell.getColumnIndex(), msg, null);
			}
		}
		

		String dataType = "";
		if(value==null){
			dataType="string";
		}else{

			dataType = value.getClass().getName();
		}
		String formatStr = config.getFormat();
		CellStyle cellStyle = null;
		// 类型转换
		DataTypeHandler hander = null;
		if (config.getDataTypeHandler() != null) {
			hander = config.getDataTypeHandler();
		} else if (StringUtils.isNotBlank(config.getDataTypeHandlerName())
				&& getDataTypeService() != null) {
			hander = getDataTypeService().find(config.getDataTypeHandlerName());
		}
		if (hander != null) {
			Class<?> paramType = getMethodParamTypes(hander, "disConverter");
			if ((value!=null&&value.getClass() == paramType
					)|| paramType.getName().equals("java.lang.Object")) {
				value = hander.export(value);
				dataType = getMethodReturnType(hander, "disConverter")
						.getName();
				logger.info(String.format("%s  k=%s,v=%s",
						config.getFieldName(), String.valueOf(value), value));
			} else {
				String message = String
						.format("the DataTypeHandler[%s]'s param of method[disConverter] is not match the field[%s] type",
								hander.getClass().getName(),
								config.getFieldName());
				logger.error(message);
				throw new ExcelDataTypeHandlerException(hander.getClass()
						.getName(), message, null);
			}
		}
		// 字段翻译
		DataHandler dataHandler = null;
		if (config.getDataHandler() != null) {
			dataHandler = config.getDataHandler();
		} else if (StringUtils.isNotBlank(config.getDataHandlerName())
				&& getDataHandlerService() != null) {
			dataHandler = getDataHandlerService().findHandler(config.getDataHandlerName());
		}
		if (dataHandler != null) {
			Class<?> paramType = getMethodParamTypes(dataHandler, "translate");
			if ((value!=null&&value.getClass() == paramType)
					|| paramType.getName().equals("java.lang.Object")) {
				Object translateValue = dataHandler.export(value);
				dataType = getMethodReturnType(dataHandler, "translate")
						.getName();// value1.getClass().getName();
				logger.info(String.format("%s  k=%s,v=%s",
						config.getFieldName(), String.valueOf(translateValue),
						value));
				value = translateValue;
			} else {
				String message = String
						.format("the dataHandler[%s]'s param of method[translate] is not match the field[%s] type",
								dataHandler.getClass().getName(),
								config.getFieldName());
				if (hander != null) {
					message = String
							.format("the dataHandler[%s]'s param is not match the return type of dataTypeHandler[%s] after it run",
									dataHandler.getClass().getName(), hander
											.getClass().getName());
				}
				throw new ExcelDataHandlerException(
						dataHandler.getHandlerName(), message, null);
			}
		}

		switch (dataType) {
		case "java.lang.String":
		case "string":
			String string = String.valueOf(value);
			cell.setCellValue(string);
			break;
		case "java.math.BigDecimal":
		case "BigDecimal":
		case "Decimal":
		case "bigDecimal":
		case "decimal":
			BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(String
					.valueOf(value)));
			cell.setCellValue(bigDecimal.doubleValue());
			break;
		case "java.lang.Double":
		case "double":
			Double double1 = Double.valueOf(String.valueOf(value));
			cell.setCellValue(double1);
			break;
		case "java.lang.Float":
		case "float":
			Float float1 = Float.valueOf(String.valueOf(value));
			cell.setCellValue(float1);
			break;
		case "java.lang.Long":
		case "long":
			Long long1 = Long.valueOf(String.valueOf(value));
			cell.setCellValue(long1);
			break;
		case "java.lang.Integer":
		case "int":
			Integer integer = Integer.valueOf(Double.valueOf(
					String.valueOf(value)).intValue());
			cell.setCellValue(integer);
			break;
		case "java.lang.Boolean":
		case "boolean":
			Boolean booleanValue = Boolean.valueOf(String.valueOf(value));
			cell.setCellValue(booleanValue);
			break;
		case "java.util.Date":
			Date date = (Date) value;
			if (StringUtils.isBlank(formatStr)) {
				formatStr = Style.Format.Value.DefaultDateFormt;
			}
			cell.setCellValue(date);
			break;
		default:
			throw new ExcelCellException(
					cell.getRowIndex(),
					cell.getColumnIndex(),
					String.format(
							"can not handler the data type of %s, please implements the interface of AbstractDataTypeHandler change to base java type.",
							dataType), null);
		}
		if (style) {
			
			String fieldStyle=config.getFieldStyle();
			String formatStyle="";
			if(StringUtils.isBlank(fieldStyle)&&StringUtils.isNoneBlank(config.getFormat())){
				fieldStyle=String.format("%s:%s", Style.Format.name,config.getFormat().trim());
			}else if(StringUtils.isNoneBlank(fieldStyle)&&StringUtils.isNoneBlank(config.getFormat())){
				formatStyle = String.format("%s:%s", Style.Format.name,config.getFormat().trim());
				fieldStyle=String.format("%s;%s", formatStyle,fieldStyle);
			}
			cellStyle = StyleHandlerFactory.factory().handler(cell, fieldStyle, cellStyle);
			
			if (cellStyle != null) {
				cell.setCellStyle(cellStyle);
			}
		}

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
