package org.office.excel.handler.datahandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.lang3.StringUtils;
import org.office.excel.exception.ExcelDataHandlerException;
import org.office.excel.exception.ExcelException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.handler.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DataHandlerServiceImpl implements DataHandlerService {
	private static final Logger logger = LoggerFactory.getLogger(DataHandlerServiceImpl.class);
	@SuppressWarnings("rawtypes")
	private Map<String, DataHandler> handlers;

	public DataHandler<?> findHandler(String fieldName) throws ExcelNotFoundHandlerException {
		DataHandler<?> dataHandler = null;
		if (handlers != null) {
			dataHandler = handlers.get(fieldName);
		}
		
		if(dataHandler==null&&StringUtils.isNotBlank(fieldName)){
			dataHandler = initDataHandlerBuFullName(fieldName);
		}
		return dataHandler;
	}

	public void addHandler(DataHandler<?> handler){
		if (handlers != null && handler != null) {
			String fieldName = handler.getHandlerName();
			if (StringUtils.isNotBlank(fieldName)) {
				handlers.put(fieldName.trim(), handler);
			}else{

				fieldName=handler.getClass().getName();
				handlers.put(fieldName.trim(), handler);
			}
		}

	}

	@SuppressWarnings({ "rawtypes" })
	private void init() throws  ExcelException{
		if (handlers == null) {
			handlers = new HashMap<String, DataHandler>();
		}
		ServiceLoader<DataHandler> dataTypeServiceLoader = ServiceLoader
				.load(DataHandler.class);
		for (DataHandler handler : dataTypeServiceLoader) {
			if (handler != null) {
				addHandler(handler);
			}
		}
	}

	public DataHandlerServiceImpl() throws ExcelDataHandlerException {
		try {
			init();
		} catch (ExcelException e) {
			logger.error("DataHandlerServiceImpl.DataHandlerServiceImpl() handler init fail，message：",e.getErrorMsg());
			throw new ExcelDataHandlerException("DataHandlerServiceImpl","handler init fail",e);
		}
	}

	public DataHandler<?> initDataHandlerBuFullName(String fullName) throws ExcelNotFoundHandlerException {
		if(StringUtils.isEmpty(fullName)){
			String msg="the param fullName of the method of DataHandlerServiceImpl.initDataHandlerBuFullName(String fullName) is empty ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		logger.info("DataHandlerServiceImpl.initDataHandlerBuFullName(String fullName={})",fullName);
		DataHandler<?> handler=null;
		Class<?> handlerClass=null;
		try {
			handlerClass = Class.forName(fullName);
		} catch (ClassNotFoundException e) {
			String msg=String.format("loading of %s  error . ", fullName);
			logger.error(msg);
			throw new ExcelNotFoundHandlerException(fullName,msg,null);
		}
		if(handlerClass!=null){
			try {
				handler=(DataHandler<?>)handlerClass.newInstance();
			} catch (InstantiationException e) {
				String msg=String.format("Instantiation of %s. it has not default Access construst function witd zero parm", fullName);
				logger.error(msg);
				throw new ExcelNotFoundHandlerException(fullName,msg,null);
			} catch (IllegalAccessException e) {
				String msg=String.format("Illegal Access of %s,don't have permision to  Access  construst function.", fullName);
				logger.error(msg);
				throw new ExcelNotFoundHandlerException(fullName,msg,null);
			}
		}
		if(handler!=null){
			this.addHandler(handler);
		}
		return handler;
	}

}