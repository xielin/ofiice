package org.office.excel.handler.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.office.excel.handler.ValidateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MailValidate implements ValidateHandler{
	private static final Logger logger = LoggerFactory.getLogger( MailValidate.class );
	
	private String patternStr="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	@Override
	public String getfNandlerName() {
		// TODO Auto-generated method stub
		return "email";
	}

	@Override
	public Boolean parse(Object cellValue) {
		logger.info("MailValidate.parseValidate({})",cellValue);
		String value=String.valueOf(cellValue);
		boolean flag=false;
		Pattern p1 = null;
		Matcher m = null;
		p1 = Pattern.compile(patternStr);
		m = p1.matcher(value);
		flag = m.matches();
		return flag;
	}

	@Override
	public Boolean export(Object fieldValue) {
		logger.info("MailValidate.exportValidate({})",fieldValue);
		String value=String.valueOf(fieldValue);
		boolean flag=false;
		Pattern p1 = null;
		Matcher m = null;
		p1 = Pattern.compile(patternStr);
		m = p1.matcher(value);
		flag = m.matches();
		return flag;
	}

}
