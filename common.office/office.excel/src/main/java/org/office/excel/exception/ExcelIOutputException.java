package org.office.excel.exception;

public class ExcelIOutputException  extends ExcelIoException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcelIOutputException(String errorMsg, Exception exm) {
		super(errorMsg, exm);
		super.setCode(EXCEL_IO_OUTPUT_EXCEPTION_CODE);
	}

	public ExcelIOutputException(String fileName,String errorMsg, Exception exm) {
		super(fileName,errorMsg, exm);
		super.setCode(EXCEL_IO_OUTPUT_EXCEPTION_CODE);
	}
}
