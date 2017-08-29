package org.office.excel.exception;

public class ExcelIoInputException  extends ExcelIoException{
	private static final long serialVersionUID = 1L;

	
	public ExcelIoInputException(String errorMsg, Exception exm) {
		this(null,errorMsg,exm);
	}
	
	public ExcelIoInputException(String fileName,String errorMsg, Exception exm) {
		super(fileName,errorMsg, exm);
		super.setCode(EXCEL_IO_INPUT_EXCEPTION_CODE);
	}
	
	public ExcelIoInputException(String errorMsg ) {
		this(errorMsg,null);
	}
}
