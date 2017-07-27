package lib;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataConfig {

	static XSSFWorkbook wb;
	
	XSSFSheet sheet1;
	
	public ExcelDataConfig(String excelPath)
	{
		try 
		{
			File src = new File(excelPath);
			
				FileInputStream fis = new FileInputStream(src);
					
				wb = new XSSFWorkbook(fis);
							
		
		} catch (Exception e) {
		
			System.out.println(e.getMessage());			
		}
	}
	
	
	public String getData(int sheetNumber, int row, int column)
	{
		sheet1 = wb.getSheetAt(sheetNumber);
		
		String data = sheet1.getRow(row).getCell(column).getStringCellValue();
		
		return data;
	}
	
	public String setData(int sheetNumber, int row, int column, String result)
	{
		sheet1 = wb.getSheetAt(sheetNumber);
		
		 sheet1.getRow(row).createCell(2).setCellValue(result);;
		
		 return result;
		
		
	}
	
	
	public int getRowCount(int sheetIndex)
	{
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		
		row = row+1;
		
		return row;
	}
	
	
	
	
	
}
