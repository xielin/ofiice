package org.office.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.office.excel.bean.Student;
import org.office.excel.config.ExcelField;
import org.office.excel.config.util.AnnotationUtil;
import org.office.excel.exception.ExcelCellException;
import org.office.excel.exception.ExcelDataHandlerException;
import org.office.excel.exception.ExcelDataTypeHandlerException;
import org.office.excel.exception.ExcelIoException;
import org.office.excel.exception.ExcelNotFoundHandlerException;
import org.office.excel.exception.ExcelStyleException;
import org.office.excel.exception.ExcelStyleHandlerException;
import org.office.excel.exception.ExcelValidateException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * @throws ExcelValidateException 
	 * @throws ExcelIoException 
	 * @throws ExcelCellException 
	 * @throws ExcelStyleException 
	 * @throws ExcelNotFoundHandlerException 
	 * @throws ExcelDataHandlerException 
	 * @throws ExcelDataTypeHandlerException 
	 * @throws ExcelStyleHandlerException 
	 * @throws FileNotFoundException 
	 */
	public void testApp() throws ExcelStyleHandlerException, ExcelDataTypeHandlerException, ExcelDataHandlerException, ExcelNotFoundHandlerException, ExcelStyleException, ExcelCellException, ExcelIoException, ExcelValidateException, FileNotFoundException {
		ExcelUtil excelUtil = new ExcelUtil();
		String path = this.getClass().getResource("/").getPath();
		File file = new File(String.format("%s%s", path, "text.xlsx"));
		OutputStream os = new FileOutputStream(file);

		int size = 100;
		Student sd = null;
		Map[] maps = new Map[size];
		Map<String,Object> dataList = null;
		for (int i = 0; i < size; i++) {
			dataList=new HashMap<String,Object>();
			dataList.put("id", String.valueOf(i));
			dataList.put("name", String.format("NAME_%d", i));
			dataList.put("sex", String.valueOf(i%2==0?"男":"女"));
			dataList.put("age", i%100);
			dataList.put("borthDate", new Date());
			maps[i] = dataList;
		}
		List<ExcelField> list=AnnotationUtil.loadAnnotationConfig(Student.class);
		excelUtil.export(os, list, maps, null);
		assertTrue(true);
	}
}
