package org.office.excel.config.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.office.excel.annotation.ColumnDataHandler;
import org.office.excel.annotation.ColumnDataTypeHandler;
import org.office.excel.annotation.ColumnFormat;
import org.office.excel.annotation.ColumnStyle;
import org.office.excel.annotation.ColumnValidate;
import org.office.excel.annotation.ExcelColumn;
import org.office.excel.annotation.ExcelSheet;
import org.office.excel.config.ExcelField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationUtil {

	private static final Logger logger = LoggerFactory.getLogger( AnnotationUtil.class );
	
	public static  List<ExcelField>  loadAnnotationConfig(Class<?> beanConfigClass){
		if(beanConfigClass==null){
			 String msg=String.format("the value param of  AnnotationUtil.loadAnnotationConfig(Class<?> beanConfigClass) is null. ");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		 }
		logger.info("AnnotationUtil.loadAnnotationConfig(beanConfigClass:{})",beanConfigClass);

		ExcelSheet excelSheet = beanConfigClass.getAnnotation(ExcelSheet.class);
		
		if(excelSheet == null){
			String msg=String.format("the value param of  AnnotationUtil.loadAnnotationConfig(Class<?> beanConfigClass = %s) has not excelSheet Annotation.",beanConfigClass.getName());
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		 List<ExcelField> list=new ArrayList<ExcelField>();
		Field fields[] = beanConfigClass.getDeclaredFields();
		
		if(fields == null ||  fields.length<=0){

			String msg=String.format("the value param of  AnnotationUtil.loadAnnotationConfig(Class<?> beanConfigClass = %s) does not have DeclaredField.",beanConfigClass.getName());
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		ExcelField excelField = null;
		int fieldAnnotationCount=0;
		for (Field field : fields) {
			
			if(field == null){
				continue;
			}
			ExcelColumn column = loadExcelColumn(field);
			if(column==null){
				continue;
			}
			fieldAnnotationCount++;
			
			excelField = new ExcelField();
			excelField.setAbandonStyleCount(excelSheet.abandonStyleCount());
			excelField.setSheetIndex(excelSheet.sheetIndex());
			excelField.setSheetName(excelSheet.sheetName());
			excelField.setDataIndex(excelSheet.dataIndex());
			excelField.setTitleIndex(excelSheet.titleIndex());
			
			
			excelField.setColumnIndex(column.columnIndex());
			excelField.setFieldTitle(column.fieldTitle());
			excelField.setFieldName(field.getName());
			

			ColumnDataTypeHandler columnDataTypeHandler= loadColumnDataTypeHandler( field);
			if(columnDataTypeHandler != null){
				excelField.setDataTypeHandlerName(columnDataTypeHandler.value());
			}
			
			ColumnDataHandler columnDataHandler=loadColumnDataHandler(field);
			if(columnDataHandler!=null){
				excelField.setDataHandlerName(columnDataHandler.value());
			}

			ColumnFormat columnFormat=loadColumnFormat( field);
			if(columnFormat!=null){
				excelField.setFormat(columnFormat.value());
			}

			ColumnValidate columnValidate=loadColumnValidate( field);
			if(columnValidate!=null){
				excelField.setValidate(columnValidate.value());
			}
			

			ColumnStyle columnStyle=loadColumnStyle( field);
			if(columnStyle!=null){
				excelField.setFieldStyle(columnStyle.style());
				excelField.setStyleHandlerName(columnStyle.handlers());
			}
			list.add(excelField);
		}
		if(fieldAnnotationCount == 0){

			String msg=String.format("the value param of  AnnotationUtil.loadAnnotationConfig(Class<?> beanConfigClass = %s) does not have Annotation DeclaredField.",beanConfigClass.getName());
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		return list;
	}
	
	public static ColumnDataHandler loadColumnDataHandler(Field field){
		logger.info("AnnotationUtil.loadColumnDataHandler(field:{})",field);
		if (field.isAnnotationPresent(ColumnDataHandler.class)) {
			ColumnDataHandler columnDataHandler = field.getAnnotation(ColumnDataHandler.class);
			return columnDataHandler;
		}
		return null;
	}
	
	
	public static ExcelColumn loadExcelColumn(Field field){
		logger.info("AnnotationUtil.loadExcelColumn(field:{})",field);
		if (field.isAnnotationPresent(ExcelColumn.class)) {
			ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
			return excelColumn;
		}
		return null;
	}
	
	
	
	public static ColumnDataTypeHandler loadColumnDataTypeHandler(Field field){
		logger.info("AnnotationUtil.loadColumnDataTypeHandler(field:{})",field);
		if (field.isAnnotationPresent(ColumnDataTypeHandler.class)) {
			ColumnDataTypeHandler columnDataTypeHandler = field.getAnnotation(ColumnDataTypeHandler.class);
			return columnDataTypeHandler;
		}
		return null;
	}
	
	
	public static ColumnFormat loadColumnFormat(Field field){
		logger.info("AnnotationUtil.loadColumnFormat(field:{})",field);
		if (field.isAnnotationPresent(ColumnFormat.class)) {
			ColumnFormat columnFormat = field.getAnnotation(ColumnFormat.class);
			return columnFormat;
		}
		return null;
	}
	
	
	public static ColumnStyle loadColumnStyle(Field field){
		logger.info("AnnotationUtil.loadColumnStyle(field:{})",field);
		if (field.isAnnotationPresent(ColumnStyle.class)) {
			ColumnStyle columnStyle = field.getAnnotation(ColumnStyle.class);
			return columnStyle;
		}
		return null;
	}
	
	
	
	public static ColumnValidate loadColumnValidate(Field field){
		logger.info("AnnotationUtil.loadColumnValidate(field:{})",field);
		if (field.isAnnotationPresent(ColumnValidate.class)) {
			ColumnValidate columnValidate = field.getAnnotation(ColumnValidate.class);
			return columnValidate;
		}
		return null;
	}

}
