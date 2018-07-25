package com.zhaopin.uitest.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.zhaopin.uitest.util.DateHandler;

public class ExtentManager {
    
    private static ExtentReports extent;
    
    public static ExtentReports getInstance() {
    	if (extent == null)
    		createInstance("report/extent"+ DateHandler.getTimeStamp() +".html");  	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("UI自动化测试报告");
        htmlReporter.config().setEncoding("GBK");
        htmlReporter.config().setReportName("UI自动化测试报告");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}
