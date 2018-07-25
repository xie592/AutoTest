package com.zhaopin.uitest.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * driver工厂
 * @author xuyao
 *
 */
public class DriverFactory {
	
	/**
	 * 得到一个没有参数配置的driver
	 * @param type
	 * @return
	 */
	public static WebDriver getDriver(String type){
		return createDriver(type, null);
	}
	
	/**
	 * 得到一个有参数配置的driver
	 * @param type
	 * @param parameter
	 * @return
	 */
	public static WebDriver getDriver(String type,Object parameter){
		return createDriver(type, parameter);
	}
	
	/**
	 * 创建Driver
	 * @param type
	 * @param parameter
	 * @return
	 */
	private static WebDriver createDriver(String type, Object parameter) {
		WebDriver driver = null;
		switch (type.toLowerCase()) {
		case "firefox":
			driver = createFireFoxDriver(parameter);
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
			driver = createChromeDriver(parameter);
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "resources/IEDriverServer.exe");
			driver = createIEDriver();
			break;
		default:
			Log.error("Error:Invalid Browser Type");
			break;
		}
		return driver;
	}
	
	/**
	 * 创建firefoxDriver
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static WebDriver createFireFoxDriver(Object parameter){
		WebDriver driver = null;
		if(parameter == null){
			driver = new FirefoxDriver();
		}else{
			driver = new FirefoxDriver((FirefoxProfile)parameter);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Config.pageLoadTimeout, TimeUnit.SECONDS);
		return driver;
	}
	
	/**
	 * 创建chromeDriver
	 * @param parameter
	 * @return
	 */
	private static WebDriver createChromeDriver(Object parameter){
		WebDriver driver = null;
		if(parameter == null){
			driver = new ChromeDriver();
		}else{
			driver = new ChromeDriver((ChromeOptions)parameter);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Config.pageLoadTimeout, TimeUnit.SECONDS);
		return driver;
	}
	
	/**
	 * 创建IEdriver
	 * @return
	 */
	private static WebDriver createIEDriver(){
		return new InternetExplorerDriver();
	}
}
