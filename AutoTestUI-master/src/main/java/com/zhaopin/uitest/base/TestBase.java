package com.zhaopin.uitest.base;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zhaopin.uitest.util.ExcelParser;

import org.dom4j.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;


public class TestBase {
	
	protected WebDriver driver = null;
	protected Actions action = null;
	//public static ExtentReports extent = null;
	public static Map<String, WebDriver> driverMap = new LinkedHashMap<String, WebDriver>();
	protected static CheckPoint checkPoint = null;
	protected static Locator locator = null;

	/**
	 * 初始化ExtentReports
	 */
	@BeforeSuite
	public void beforeSuite() {
		//extent = ExtentManager.getReport();
	}
	
	@Parameters({"broswerType"})
	@BeforeTest
	public void beforeTest(String broswerType) {
		WebDriver driver = DriverFactory.getDriver(broswerType);
		driverMap.put(Thread.currentThread().getId()+"", driver);		
	}
	
	@Parameters({"broswerType"})
	@BeforeClass
	public void beforeClass(String testName) {
		driver = driverMap.get(Thread.currentThread().getId()+"");
		action = new Actions(driver);
		checkPoint = new CheckPoint(testName,  this.getClass().getSimpleName());
		locator = new Locator(driver, action, Config.objectRepository, Config.objectWaitTime);		
	}
	
	@AfterTest
	public void afterTest() {
		driver.close();
		driver.quit();
	}

	/**
	 * 刷新报告流后关闭extent
	 */
	@AfterSuite
	public void afterSuite() {
		//extent.flush();
		//extent.close();
	}
	
	/**
	 * 数据驱动,读取xml
	 * @return
	 */
	@DataProvider(name="getXmlData")
	public Object[][] getXmlData(){
		XmlParser xmlParser = new XmlParser("data.xml");
		String caseName = this.getClass().getSimpleName();//查找当前正在跑用例的java文件的名字
		List<Element> list = xmlParser.getElements("//"+caseName);
		Map<String, String> common = xmlParser.getChildElementInfo("/data/common");
		Object[][] result = new Object[list.size()][];
		for(int i=1;i<list.size();i++) {
			Map<String, String> map = xmlParser.getChildElementInfo(list.get(i));			
			Object[] tempObject = new Object[]{xmlParser.mapMarge(common, map)};
			result[i] = tempObject;
		}
		return result;
	}
	
	/**
	 * 数据驱动,读取excel
	 * @return
	 */
	@DataProvider(name="getExcelData")
	public Object[][] getExcelData(){
		ExcelParser excelParser = new ExcelParser("d:/demo.xlxs");
		String caseName = this.getClass().getSimpleName();//查找当前正在跑用例的java文件的名字
		List<Map<String,String>> mapList = excelParser.getData(caseName);
		Object[][] result = new Object[mapList.size()][];
		for(int i=1;i<mapList.size();i++) {		
			Object[] tempObject = new Object[]{mapList.get(i)};
			result[i] = tempObject;
		}
		return result;
	}
	
}
