package org.office.excel.handler.validate;

import java.util.regex.Pattern;

import org.office.excel.handler.ValidateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NumberValidate implements ValidateHandler{
	private static final Logger logger = LoggerFactory.getLogger( NumberValidate.class );
	
	private String patternStr="[0-9]*";

	@Override
	public String getfNandlerName() {
		return "number";
	}

	@Override
	public Boolean parse(Object cellValue) {
		logger.info("NumberValidate.parseValidate({})",cellValue);
		
		String str=String.valueOf(cellValue);
		Pattern pattern = Pattern.compile(patternStr);
	    return pattern.matcher(str).matches();   
	}

	@Override
	public Boolean export(Object fieldValue) {
		logger.info("NumberValidate.exportValidate({})",fieldValue);
		String str=String.valueOf(fieldValue);
		Pattern pattern = Pattern.compile(patternStr);
	    return pattern.matcher(str).matches(); 
	}

}
