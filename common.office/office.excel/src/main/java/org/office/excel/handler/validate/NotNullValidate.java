package org.office.excel.handler.validate;

import org.apache.commons.lang3.StringUtils;
import org.office.excel.handler.ValidateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotNullValidate implements ValidateHandler{
	private static final Logger logger = LoggerFactory.getLogger( NotNullValidate.class );
	
	@Override
	public String getfNandlerName() {
		return "notNull";
	}
	@Override
	public Boolean parse(Object cellValue) {
		logger.info(String.format("NotNullValidate.parseValidate({}) NullValidate value[%s] ....",String.valueOf(cellValue)),cellValue);
		Boolean validate=true;
		if(cellValue instanceof String){
			if(StringUtils.isBlank(String.valueOf(cellValue))){
				validate=false;
			}
		}else if(cellValue == null){
			validate=false;
		}
		return validate;
	}

	@Override
	public Boolean export(Object fieldValue) {
		logger.info("NotNullValidate.exportValidate( {})",fieldValue);
		Boolean validate=true;
		if(fieldValue instanceof String){
			if(StringUtils.isBlank(String.valueOf(fieldValue))){
				validate=false;
			}
		}else if(fieldValue == null){
			validate=false;
		}
		return validate;
	}

}
