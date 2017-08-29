package org.office.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class BeanUtil {

	private static final Logger logger = LoggerFactory.getLogger( BeanUtil.class );

	private Class<?> clasz;
	private Map<String, PropertyDescriptor> beanPropertyDescriptor;
	public Map<String, PropertyDescriptor> getBeanPropertyDescriptor() {
		return beanPropertyDescriptor;
	}

	
	public void setBeanPropertyDescriptor(
			Map<String, PropertyDescriptor> beanPropertyDescriptor) {
		this.beanPropertyDescriptor = beanPropertyDescriptor;
	}

	private Map<String, String> beanPropertyType;

	public BeanUtil(Class<?> classz) {
		logger.info("creating  BeanHelper");
		this.clasz = classz;
	}

	/**
	 * 初始化工作
	 * @ 
	 * 
	 * @throws ExcelException
	 */
	public void init(){
		logger.info("BeanHelper  init");
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(clasz);
		} catch (IntrospectionException e) {
			String msg="获取类" + clasz.getName() + "信息失败";
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		if (pds == null) {

			String msg="无法获取类字段属性";
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		if (pds.length <= 0) {

			String msg="类字段属性个数为0";
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		beanPropertyDescriptor = new HashMap<String, PropertyDescriptor>();
		beanPropertyType = new HashMap<String, String>();
		for (PropertyDescriptor pd : pds) {
			if (pd != null) {

				beanPropertyDescriptor.put(pd.getName(), pd);
				beanPropertyType.put(pd.getName(), pd.getPropertyType().getName());
			}
		}

		logger.info("created  BeanHelper");
	}

	public void init(Class<?> classz) {

		if (classz == null) {
			String msg="the param classz of the method of BeanHelper.init(classz) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		logger.info("BeanHelpers  init(classz)");
		this.clasz=classz;
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(clasz);
		} catch (IntrospectionException e) {

			String msg=String.format("the class[%s] Introspection Exception.", classz.getName());
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		if (pds == null) {
			String msg=String.format("未能获取类[%s]字段属性个数为0",classz.getName());
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		if (pds.length <= 0) {
			String msg=String.format("类[%s]字段属性个数为0",classz.getName());
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		beanPropertyDescriptor = new HashMap<String, PropertyDescriptor>();
		beanPropertyType = new HashMap<String, String>();
		for (PropertyDescriptor pd : pds) {
			if (pd != null) {
				beanPropertyDescriptor.put(pd.getName(), pd);
				beanPropertyType.put(pd.getName(), pd.getPropertyType().getName());
			}
		}

		logger.info("init(classz)初始化工作完成");
	}
	public void setPropertyValue(Object target, String propertyName,Object vlaue)  {

		logger.info(String.format("正在给%s属性的值设置为%s", propertyName,vlaue));
		if (target == null) {
			String msg="the param target of the method of BeanHelper.setPropertyValue(target,propertyName,vlaue) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (propertyName == null) {
			String msg="the param propertyName of the method of BeanHelper.setPropertyValue(target,propertyName,vlaue) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (beanPropertyDescriptor == null) {
			String msg=" don't run BeanHelper.init() yet ";
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		PropertyDescriptor pd = beanPropertyDescriptor.get(propertyName);
		Method methodGetX = pd.getWriteMethod();
		try {
				if(methodGetX!=null){
					methodGetX.invoke(target, vlaue);
			    }else{
					String msg=String.format("the property[%s] hash no write method of class[%s]", propertyName,target.getClass().getName());
					logger.error(msg);
					throw new RuntimeException(msg);
			    }
		}catch ( IllegalArgumentException e) {
			String msg="can't set the value of "+vlaue.getClass().getName()+" into the field of "+pd.getPropertyType().getName();
			logger.error(msg);
			throw new RuntimeException(msg);

		} catch (IllegalAccessException | InvocationTargetException e) {
			String msg=String.format(" Illegal Access or Invocation Target, set value to the property[%s] of class[%s]", propertyName,target.getClass().getName());
			logger.error(msg);
			throw  new RuntimeException(msg);

		}

	}

	public Object getPropertyValue(Object target, String propertyName)  {

		logger.info(String.format("正在获取%s的属性设置值", propertyName));
		if (target == null) {

			String msg="the param target of the method of BeanHelper.getPropertyValue(target,propertyName) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (propertyName == null) {
			String msg="the param propertyName of the method of BeanHelper.getPropertyValue(target,propertyName) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (beanPropertyDescriptor == null) {
			String msg="未进行初始化，先进行初始化，运行init方法";
			logger.error(msg);
			logger.error(msg);
			throw  new RuntimeException(msg);
		}
		PropertyDescriptor pd = beanPropertyDescriptor.get(propertyName);
		Method methodGetX = pd.getReadMethod();
		Object reValue = null;
		try {
			if(methodGetX!=null){
				reValue = methodGetX.invoke(target);
			}else{
				String msg=String.format("the property[%s] of class[%s] has not getMethod ", propertyName,target.getClass().getName());
				throw  new RuntimeException(msg);
			}
		} catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException e) {
			String msg=String.format("the property[%s] of class[%s] illegal Access , illegal Argument  or invocation target", propertyName,target.getClass().getName());
			throw  new RuntimeException(msg);
		}
		logger.info(String.format("%s属性的值值%s", propertyName,String.valueOf(reValue)));
		return reValue;
	}

	public String getPropertyType(String propertyName)  {

		logger.info(String.format("正在获取%s的属性的数据类型", propertyName));
		if (propertyName == null) {
			String msg="the param propertyName of the method of BeanHelper.getPropertyType(propertyName) is null ";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (beanPropertyDescriptor == null) {
			
			String msg=String.format("未进行初始化，先进行初始化，运行init方法");
			logger.error(msg);
			throw  new RuntimeException(msg);
		}
		String propertyType = null;
		if (beanPropertyDescriptor.containsKey(propertyName)) {
			propertyType = beanPropertyType.get(propertyName);
		} else {
			String msg=String.format("类" + clasz.getName() + "不存在"+ propertyName + "属性");
			logger.error(msg);
			throw  new RuntimeException(msg);
		}
		logger.info(String.format("%s的属性的数据类型是%s", propertyName,propertyType));
		return propertyType;
	}

	
	public static void main(String arg[]) throws ReflectiveOperationException {
		BeanUtil beanHelper = new BeanUtil(App.class);
		beanHelper.init();
		App app = new App();
		Object value = 22;
		beanHelper.setPropertyValue(app, "aa", value);
		
	}

}
