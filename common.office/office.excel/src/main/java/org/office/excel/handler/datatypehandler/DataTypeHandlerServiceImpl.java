package org.office.excel.handler.datatypehandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.lang3.StringUtils;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.handler.DataTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataTypeHandlerServiceImpl implements DataTypeHandlerService {
 private static final Logger logger = LoggerFactory.getLogger( DataTypeHandlerServiceImpl.class );
	

	private Map<String, DataTypeHandler<?>> handlers;

	public  DataTypeHandlerServiceImpl(){
		initHandler();
	}
	public DataTypeHandler<?> find(String handlerName) throws ExcelNotFoundHandlerException{
		logger.info("DataTypeServiceImpl.find(String handlerName={})",handlerName);
		DataTypeHandler<?> handler = null;
		if (handlers != null) {
			handler = handlers.get(handlerName);
		}
		logger.info("find dataTypeHandler over");
		if(handler==null&&StringUtils.isNotBlank(handlerName)){
			handler=initHnadlerByFullName(handlerName);
		}
		return handler;
	}

	public void add(DataTypeHandler<?> dataTypeHandler) {

		logger.info("DataTypeServiceImpl.add(DataTypeHandler dataTypeHandler={})",dataTypeHandler);
		logger.info("adding dataTypeHandler ...");
		if (handlers == null) {
			handlers = new HashMap<String, DataTypeHandler<?>>();
		}
		String handlerName="";
		if(StringUtils.isNotEmpty(dataTypeHandler.getHandlerName())){
			handlerName=dataTypeHandler.getClass().getName();
			handlers.put(handlerName.trim(),dataTypeHandler);
		}
		handlerName=dataTypeHandler.getClass().getName();
		handlers.put(handlerName,dataTypeHandler);
		logger.info("add dataTypeHandler over");
	}

	@SuppressWarnings("rawtypes")
	public void initHandler() {
		logger.info("DataTypeServiceImpl.initHandler()");
		logger.info("initing...");
		handlers = new HashMap<String, DataTypeHandler<?>>();
		ServiceLoader<DataTypeHandler> dataTypeServiceLoader = ServiceLoader.load(DataTypeHandler.class);
		for (DataTypeHandler<?> dataTypeHandler : dataTypeServiceLoader) {
			handlers.put(dataTypeHandler.getHandlerName(),dataTypeHandler);
		}
		logger.info("initing over");

	}
	public DataTypeHandler<?> initHnadlerByFullName(String fullName) throws ExcelNotFoundHandlerException {
		if(StringUtils.isEmpty(fullName)){
			String msg="the param fullName of the method of ValidateServiceImpl.initHandlerByName(String fullName) is empty ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		logger.info("ValidateServiceImpl.initHandlerByName(String fullName={})",fullName);
		DataTypeHandler<?> handler=null;
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
				handler=(DataTypeHandler<?>)handlerClass.newInstance();
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
			this.add(handler);
		}
		return handler;
		
	}
}
