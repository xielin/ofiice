package org.office.excel.exception;

import java.util.List;

import org.office.excel.config.ExcelField;


public class ExcelConfigException  extends ExcelException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExcelConfigException(String errorMsg, Exception exm) {
		this(null,null,errorMsg, exm);
	}
	public ExcelConfigException(ExcelField config,String errorMsg, Exception exm) {
		this(null,config,errorMsg, exm);
	}
	public ExcelConfigException(List<ExcelField> configsList,ExcelField config,String errorMsg, Exception exm) {
		super(errorMsg, exm);
        this.setCode(EXCEL_CONFIG_EXCEPTION_CODE);
        this.config=config;
        this.configsList=configsList;
	}
	/**
	 * 单列配置
	 */
	private ExcelField config;
	public ExcelField getConfig() {
		return config;
	}
	public void setConfig(ExcelField config) {
		this.config = config;
	}
	
	

	private List<ExcelField> configsList;
	public List<ExcelField> getConfigsList() {
		return configsList;
	}
	public void setConfigsList(List<ExcelField> configsList) {
		this.configsList = configsList;
	}
	
	
	
	

}
