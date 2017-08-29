package org.office.excel.handler.validate;

import java.util.regex.Pattern;

import org.office.excel.handler.ValidateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneValidate implements ValidateHandler {
	private static final Logger logger = LoggerFactory.getLogger( PhoneValidate.class );
	
	private String patternMobileStr = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$";
	private String patternPhoneStr = "^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})$";

	@Override
	public String getfNandlerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean parse(Object cellValue) {
		logger.info("PhoneValidate.parseValidate({})",cellValue);
		String phoneNum = String.valueOf(cellValue);
		boolean flag = false;
		Pattern p1 = null;
		Pattern p2 = null;
		p1 = Pattern.compile(patternMobileStr);
		p2 = Pattern.compile(patternPhoneStr);
		if (!((phoneNum.length() == 11 && p1.matcher(phoneNum).matches()) || (phoneNum
				.length() < 16 && p2.matcher(phoneNum).matches()))) {
			flag= false;
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	public Boolean export(Object fieldValue) {
		logger.info("PhoneValidate.exportValidate({})",fieldValue);
		String phoneNum = String.valueOf(fieldValue);
		boolean flag = false;
		Pattern p1 = null;
		Pattern p2 = null;
		p1 = Pattern.compile(patternMobileStr);
		p2 = Pattern.compile(patternPhoneStr);
		if (!((phoneNum.length() == 11 && p1.matcher(phoneNum).matches()) || (phoneNum
				.length() < 16 && p2.matcher(phoneNum).matches()))) {
			flag= false;
		} else {
			flag = true;
		}
		return flag;
	}

}
