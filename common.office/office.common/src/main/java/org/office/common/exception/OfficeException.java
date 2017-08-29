package org.office.common.exception;

public class OfficeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 详细信息
	 */
	private String detailMsg;
	public String getDetailMsg() {
		return detailMsg;
	}
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public OfficeException(String msg,Exception e){
		super(e);
		this.errorMsg = msg;
		
	}
	public OfficeException(String msg){
		this(msg,null);
		
	}

}
