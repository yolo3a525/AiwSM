package com.aiw.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 


/**
 * 从EXCEL导入到数据库
 * 创建人：FH Q313596790
 * 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead {
    
    
    public static void wirteExcel(String filepath, String filename, List<String> list) {
         try {
              //工作簿
              XSSFWorkbook hssfworkbook = new XSSFWorkbook();
             //创建sheet页
               Sheet hssfsheet = hssfworkbook.createSheet("phonelist");
                 
                 
                 
                 
                 
                 
     
                 
                 
                 
             int i= 0;
             for (String str : list) {
                 //取得第一行
                 Row  hssfrow = hssfsheet.createRow(i);
               //创建第一个单元格    并处理乱码
                 Cell  hssfcell_0 = hssfrow.createCell(0);
                 //对第一个单元格赋值
                 hssfcell_0.setCellValue(str);
                 i++;
             }
             //输出
             FileOutputStream fileoutputstream = new FileOutputStream(filepath + filename);
             hssfworkbook.write(fileoutputstream);
             fileoutputstream.close();
             hssfworkbook.close();
         }catch(Exception e) {
             e.printStackTrace();
         }
    
    }

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			Workbook  wb =  WorkbookFactory.create(fi);
			Sheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始
				
				Map varpd = new LinkedHashMap<>();
				Row row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始
					
					Cell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case 0:
							cellValue = String.valueOf((double) cell.getNumericCellValue());
							break;
						case 1:
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							cellValue = cell.getNumericCellValue() + "";
							// cellValue = String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							cellValue = "";
							break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}
					
					varpd.put("var"+j, cellValue);
					
				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return varList;
	}
}
