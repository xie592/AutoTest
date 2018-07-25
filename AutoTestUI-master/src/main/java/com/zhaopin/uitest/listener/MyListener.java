package com.zhaopin.uitest.listener;

import java.io.IOException;
import java.util.Iterator;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.zhaopin.uitest.base.Log;
import com.zhaopin.uitest.base.Screenshot;
import com.zhaopin.uitest.base.TestBase;
import com.zhaopin.uitest.util.DateHandler;
import com.zhaopin.uitest.util.WeixinSender;

import org.testng.*;


public class MyListener implements ITestListener{

	private static ExtentReports extent = ExtentManager.createInstance("report/extent"+ DateHandler.getTimeStamp() +".html");
	private static ThreadLocal test = new ThreadLocal();
    
	//剔除失败重跑的用例数
	@Override
	public void onFinish(ITestContext context) {
		Iterator<ITestResult> listOfFailedTests = context.getFailedTests().getAllResults().iterator();
		while(listOfFailedTests.hasNext()){
			ITestResult failedTest = listOfFailedTests.next();
			ITestNGMethod method = failedTest.getMethod();

			 if(context.getFailedTests().getResults(method).size()>1){
				 listOfFailedTests.remove();
			 }
		}
		
		
		extent.flush();
		//Mailer.sendMail("");
	}

	//给所有用例添加失败重跑机制
	@Override
	public synchronized void onStart(ITestContext context) {
		Log.info("正在为所有测试方法添加失败重跑机制");
		for(ITestNGMethod method: context.getAllTestMethods()){
			method.setRetryAnalyzer(new TestRetryAnalyzer());
		}

	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public synchronized void onTestSuccess(ITestResult arg0) {
		((ExtentTest) test.get()).pass("Test passed");
		//截图
		String imagePath = Screenshot.takeScreenshot(TestBase.driverMap.get(Thread.currentThread().getId()+""));
		try {
			((ExtentTest)test.get()).addScreenCaptureFromPath("/" + imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void onTestFailure(ITestResult arg0) {
		((ExtentTest) test.get()).fail(arg0.getThrowable());
		//截图
		String imagePath = Screenshot.takeScreenshot(TestBase.driverMap.get(Thread.currentThread().getId()+""));
		try {
			((ExtentTest)test.get()).addScreenCaptureFromPath("/" + imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//失败发送微信
		if(!arg0.isSuccess()){
			new WeixinSender("", "4").sendNews(arg0.getTestName()+"报错", "http://www.photophoto.cn/m5/008/018/0080180048.jpg");	
	    }
	}

	@Override
	public synchronized void onTestSkipped(ITestResult arg0) {
		((ExtentTest) test.get()).skip(arg0.getThrowable());
		//extentTest.log(LogStatus.SKIP,Reporter.getOutput(arg0).get(0));
		//TestBase.extent.endTest(extentTest);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void onTestStart(ITestResult arg0) {
		test.set(extent.createTest(arg0.getMethod().getMethodName()));
	}

}
