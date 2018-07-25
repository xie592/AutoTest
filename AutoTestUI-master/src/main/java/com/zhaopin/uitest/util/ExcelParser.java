package com.zhaopin.uitest.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
	
	Workbook workbook = null;

	public ExcelParser(String fileName){				
		try {
			workbook = new HSSFWorkbook(new FileInputStream(fileName));
		} catch (Exception e) {
			try {
				workbook = new XSSFWorkbook(new FileInputStream(fileName));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据sheetName,row,cell获取值
	 * @param sheetName sheet名
	 * @param row	行
	 * @param cell	列
	 * @return
	 */
	public String getValue(String sheetName, int row, int cell) {
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getRow(row).getCell(cell).getStringCellValue();
	}

	/**
	 * 根据sheetName获取excel测试数据
	 * @param sheetName
	 * @return
	 */
	public List<Map<String,String>> getData(String sheetName){
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		Sheet sheet = workbook.getSheet(sheetName);
		int rowNumber = sheet.getLastRowNum();
		for (int i = 1; i < rowNumber+1; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			Row row = sheet.getRow(i);
			int Colnum = row.getPhysicalNumberOfCells();
			for(int j = 0; j < Colnum; j++) {
				Cell KeyCell = sheet.getRow(0).getCell(j);
				Cell vauleCell = row.getCell(j);
				KeyCell.setCellType(CellType.STRING);
				vauleCell.setCellType(CellType.STRING);
				map.put(KeyCell.getStringCellValue(), vauleCell.getStringCellValue());
			}
			data.add(map);
		}
		return data;
	}
	
	public static void main(String[] args) {
		List<Map<String,String>> mapList = new ExcelParser("data/demo.xlsx").getData("Sheet1");
		for (Map<String,String> map : mapList) {
			for (String key : map.keySet()) {
				System.out.println(key+":"+map.get(key));
			}
			System.out.println("------------------------------------");
		}		
	}


}
