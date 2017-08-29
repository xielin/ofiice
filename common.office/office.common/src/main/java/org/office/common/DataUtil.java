package org.office.common;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class DataUtil {

	private static final Logger logger = LoggerFactory.getLogger( DataUtil.class );
	
	public static <A, B> B convertAtoB(A a, Class<B> bclass) throws ReflectiveOperationException {
		logger.info("DataUtils.convertAtoB : a={},bclass={}",a,bclass);
		if (a == null) {
			 String msg=String.format("the a param of  DataUtils.convertAtoB(A a, Class<B> bclass)is null . ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (bclass == null) {
			 String msg=String.format("the bclass param of  DataUtils.convertAtoB(A a, Class<B> bclass)is null . ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		BeanUtil beanA = new BeanUtil(a.getClass());
		BeanUtil beanB = new BeanUtil(bclass);
		B b = null;
		try {
			b = bclass.newInstance();
		} catch (InstantiationException e) {
			String msg=String.format("class=%s Instantiation Exception:%s",bclass.getName(),String.valueOf(e));
			logger.error(msg);
			throw new  RuntimeException(msg);
		} catch (IllegalAccessException e) {
			String msg=String.format("class=%s illegalAccess  Exception:%s",bclass.getName(),String.valueOf(e));
			logger.error(msg);
			throw new  RuntimeException(msg);
		}
		if (b != null) {
			Set<String> propertys = beanA.getBeanPropertyDescriptor().keySet();
			for (String pro : propertys) {
				beanB.setPropertyValue(b, pro,beanA.getPropertyValue(a, pro));
			}
		}
		return b;

	}

	public static <T>T convert(Map<Object,Object> map,Class<T> tclass){
		logger.info("DataUtils.convert : map={},bclass={}",map,tclass);
		
		if (map == null) {
			 String msg=String.format("the map param of  DataUtils.convert(Map<Object,Object> map,Class<T> tclass) is null . ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (tclass == null) {
			 String msg=String.format("the tclass param of  DataUtils.convert(Map<Object,Object> map,Class<T> tclass) is null . ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		T t=null;
		BeanUtil BeanUtil=null;
		Set<String> propertys=null;
		Object value=null;
		if(map!=null){
			try {
				t=(T)tclass.newInstance();
				BeanUtil = new BeanUtil(tclass);
				BeanUtil.init(); 
				propertys = BeanUtil.getBeanPropertyDescriptor().keySet();
				for (String pro : propertys) {
					if(map.containsKey(pro)){
						value=changeToMatchType(BeanUtil,pro,map.get(pro));
						BeanUtil.setPropertyValue(t, pro,value);
					}
				}
			} catch (InstantiationException e) {
				String msg=String.format("public static <T>T DataUtils.convert(Map<Object,Object> map,Class<T> tclass),class=%s illegalAccess  Exception:%s",tclass.getName(),String.valueOf(e));
				logger.error(msg);
				throw new  RuntimeException(msg);
			} catch (IllegalAccessException e) {
				String msg = String.format("DataUtils.convert(Map<Object,Object> map,Class<T> tclass) Illegal Access class[%s] field ", tclass.getName());
				logger.error(msg);
				throw new  RuntimeException(msg);
			}
			
		}
		return t;
	}
	
	public static <T> JSONObject convert(T t) {
		logger.info("DataUtils.convert : t={}",t);
		JSONObject json = null;
		if (t == null) {
			 String msg=String.format("the t param of  DataUtils.convert(T t)  is null . ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		BeanUtil bean = new BeanUtil(t.getClass());
		bean.init();
		json = new JSONObject();
		Set<String> propertys = bean.getBeanPropertyDescriptor().keySet();
		for (String pro : propertys) {
			json.put(pro, bean.getPropertyValue(t, pro));
		}
		return json;
	}
	
	public static <T> Map<Object,Object> convertToMap(T t) {
		logger.info("DataUtils.convertToMap : t={}",t);
		Map<Object,Object>  json = null;
		if (t == null) {
			 String msg=String.format("the t param of  DataUtils.convertToMap(T t)  is null . ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		BeanUtil bean = new BeanUtil(t.getClass());
		
			bean.init();
			json = new HashMap<Object,Object> ();
			Set<String> propertys = bean.getBeanPropertyDescriptor().keySet();
			for (String pro : propertys) {
				json.put(pro, bean.getPropertyValue(t, pro));
			}
		
		return json;
	}

	public static<T> JSONArray convert(List<T> list) {
		logger.info("DataUtils.convert : list={}",list);
		JSONArray jsonList = null;
		if (list == null || list.size() <= 0) {
			 String msg=String.format("the list param of  DataUtils.convert(List<T> list) is null or less then zero ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		JSONObject json = null;
		jsonList = new JSONArray();
		for (T t : list) {
			json = convert(t);
			jsonList.add(json);
		}
		return jsonList;
	}

	public static JSONObject convert(Map<Object, Object> map) {
		if(map==null){
			 String msg=String.format("the map param of  DataUtils.convert(Map<Object, Object> map) is null  ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }
		logger.info("DataUtils.convert : map={}",map);
		JSONObject json = null;
		
		json=new JSONObject();
		Set<Object> propertys = map.keySet();
		for (Object o : propertys) {
			json.put(String.valueOf(o), map.get(o));
		}
		return json;
	}
	
	
	public static Map<Object, Object>  convert( JSONObject json) {
		if(json==null){
			 String msg=String.format("the json param of  DataUtils.convert(Map<Object, Object>[] map) is null  ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }
		logger.info("DataUtils.convert : json={}",json);
		Set<String>  iterator = json.keySet();
		Map<Object, Object>  map=new HashMap<Object, Object> ();
		for(String name:iterator){
			map.put(name, json.get(name));
		}
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<Object, Object>[]convert(JSONArray  jsonArray) {
		logger.info("DataUtils.convert : jsonArray={}",jsonArray);
		Map<Object, Object> map = null;
		if (jsonArray == null || jsonArray.size() <= 0) {
			String msg=String.format("the jsonArray param of  DataUtils.convert(JSONArray  jsonArray) is null or less then zero ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		
		int i=0;
		int size=jsonArray.size();
		Map<Object, Object>[] result=new Map[size];
		for(Object json:jsonArray){
			JSONObject j=(JSONObject) json;
			map=convert(j);
			result[i]=map;
			i++;
		}
		return result;
	}

	public static JSONArray convert(Map<Object, Object>[] map) {
		logger.info("DataUtils.convert : map");
		JSONArray jsonList = null;
		JSONObject json = null;
		if(map==null|| map.length <= 0){
			 
			 String msg=String.format("the value param of  DataUtils.convert(Map<Object, Object>[] map) is null or less then zero ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }
		jsonList = new JSONArray();
		for (Map<Object, Object> m : map) {
			json = convert(m);
			jsonList.add(json);
		}
		return jsonList;
	}
	
	public static Object changeToMatchType(BeanUtil bean,String fieldName,Object value){
		logger.info("DataUtils.changeToMatchType : bean={},fieldName={},value=",bean,fieldName,value);
		 /*if(value==null){
			 String msg=String.format("the value param  DataUtils.changeToMatchType(BeanUtil bean,String fieldName,Object value) ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }*/
		 if(StringUtils.isEmpty(fieldName)){
			 
			 String msg=String.format("the fieldName param  DataUtils.changeToMatchType(BeanUtil bean,String fieldName,Object value) ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }
		 if(bean== null){
			 
			 String msg=String.format("the bean param  DataUtils.changeToMatchType(BeanUtil bean,String fieldName,Object value) ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }
		Object returnValue=null;
		String dataType=bean.getPropertyType(fieldName);
		switch (dataType) {
		case "java.lang.String":
		case "string":
		case "String":
			String string = String.valueOf(value==null?"":value);
			returnValue=string;
			break;
		case "java.math.BigDecimal":
		case "java.math.Decimal":
		case "Decimal":
		case "decimal":
		case "bigDecimal":
			BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(String.valueOf(value==null?0d:value)));
			returnValue=bigDecimal;
			break;
		case "java.lang.Double":
		case "double":
		case "Double":
			Double double1 =  Double.valueOf(String.valueOf(value==null?"0d":value));
			returnValue=double1;
			break;
		case "java.lang.Float":
		case "float":
		case "Float":
			Float float1 = Float.valueOf(String.valueOf(value==null?"0f":value));
			returnValue=float1;
			break;
		case "java.lang.Long":
		case "long":
		case "Long":
			Long long1 =Long.valueOf(String.valueOf(value==null?"0l":value));
			returnValue=long1;
			break;
		case "java.lang.Integer":
		case "int":
		case "Integer":
		case "integer":
			Integer integer = Integer.valueOf(Double.valueOf(String.valueOf(value==null?"0d":value)).intValue());
			returnValue=integer;
			break;
		case "java.lang.Boolean":
		case "boolean":
		case "Boolean":
		case "Bool":
		case "bool":
            Boolean booleanValue = Boolean.valueOf(String.valueOf(value==null?"false":value));
			returnValue=booleanValue;
			break;
		case "java.util.Date":
		case "date":
		case "Date":
			Date date=null;
			date = (Date) value;
			returnValue=date;
			break;
		case "java.util.Calendar":
		case "calendar":
		case "Calendar":
			Calendar calendar = (Calendar) value;
			returnValue=calendar;
			break;
			default:
				returnValue=value;
		}
		return returnValue;
		
	}
}
