package com.zhaopin.uitest.base;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class Screenshot {

	public static void takeScreenshot(WebDriver driver, String screenPath) {
		try{
			File srcFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(screenPath));
		}catch(IOException e){
			Log.error("截图异常");
		}
	}
	
	public static String takeScreenshot(WebDriver driver) {
		String filePath = "test-output/snapshot";
		String screenname = String.valueOf(new Date().getTime()) + ".jpg";
		File dir = new File(filePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String screenpath = dir.getAbsolutePath() + "/" + screenname;
		takeScreenshot(driver, screenpath);
		Log.debug(screenpath);
		return screenpath;
	}
	

	public static void main(String[] args){
		WebDriver driver = DriverFactory.getDriver("chrome");
		driver.get("http://www.baidu.com");
		Screenshot.takeScreenshot(driver);
	}
	
}
