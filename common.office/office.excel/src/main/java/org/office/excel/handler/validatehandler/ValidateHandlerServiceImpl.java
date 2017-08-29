package org.office.excel.handler.validatehandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.lang3.StringUtils;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.handler.ValidateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateHandlerServiceImpl implements ValidateHandlerService {
	private static final Logger logger = LoggerFactory
			.getLogger(ValidateHandlerServiceImpl.class);

	private Map<String, ValidateHandler> handlers = null;

	@Override
	public ValidateHandler find(String name)
			throws ExcelNotFoundHandlerException {
		if (StringUtils.isEmpty(name)) {
			String msg = "the param name of the method of ValidateServiceImpl.find(String name) is empty ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		logger.info("ValidateServiceImpl.find(String name={})", name);
		if (handlers == null) {
			handlers = new HashMap<String, ValidateHandler>();
		}
		ValidateHandler validateHandler = null;
		validateHandler = handlers.get("name");
		if (validateHandler == null) {
			validateHandler = initHandlerByName(name);
		}
		return validateHandler;
	}

	@Override
	public void init() {
		logger.info("ValidateServiceImpl.init()");
		ServiceLoader<ValidateHandler> validateServiceLoader = ServiceLoader
				.load(ValidateHandler.class);
		for (ValidateHandler validate : validateServiceLoader) {
			addHandler(validate);
		}
	}

	@Override
	public void addHandler(ValidateHandler validateHandler) {
		logger.info("ValidateServiceImpl.init()");
		if (handlers == null) {
			handlers = new HashMap<String, ValidateHandler>();
		}
		if (StringUtils.isNotEmpty(validateHandler.getfNandlerName())) {
			handlers.put(validateHandler.getfNandlerName().trim(),
					validateHandler);
		}
		handlers.put(validateHandler.getClass().getName(), validateHandler);
	}

	@Override
	public ValidateHandler initHandlerByName(String fullName)
			throws ExcelNotFoundHandlerException {
		if (StringUtils.isEmpty(fullName)) {
			String msg = "the param fullName of the method of ValidateServiceImpl.initHandlerByName(String fullName) is empty ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		logger.info(
				"ValidateServiceImpl.initHandlerByName(String fullName={})",
				fullName);
		ValidateHandler handler = null;
		Class<?> handlerClass = null;
		try {
			handlerClass = Class.forName(fullName);
		} catch (ClassNotFoundException e) {
			String msg = String.format("loading of %s  error . ", fullName);
			logger.error(msg);
			throw new ExcelNotFoundHandlerException(fullName, msg, null);
		}
		if (handlerClass != null) {
			try {
				handler = (ValidateHandler) handlerClass.newInstance();
			} catch (InstantiationException e) {
				String msg = String
						.format("Instantiation of %s. it has not default Access construst function witd zero parm",
								fullName);
				logger.error(msg);
				throw new ExcelNotFoundHandlerException(fullName, msg, null);
			} catch (IllegalAccessException e) {
				String msg = String
						.format("Illegal Access of %s,don't have permision to  Access  construst function.",
								fullName);
				logger.error(msg);
				throw new ExcelNotFoundHandlerException(fullName, msg, null);
			}
		}
		if (handler != null) {
			this.addHandler(handler);
		}
		return handler;
	}

}
