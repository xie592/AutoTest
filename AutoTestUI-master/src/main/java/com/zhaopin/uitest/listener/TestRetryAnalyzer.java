package com.zhaopin.uitest.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.zhaopin.uitest.base.Config;
import com.zhaopin.uitest.base.Log;

public class TestRetryAnalyzer implements IRetryAnalyzer{
	
	//当前数
	private int retryCount = 1;
	
	//最多重跑次数
	private int maxRetryTimes = Config.retryTimes;
	
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount <= maxRetryTimes){			
			result.setAttribute("RETRY", retryCount);
			Log.info("用例："+ result.getName()+" 正在进行第"+retryCount+"次失败重跑");
			retryCount++;
			return true;
		}
		return false;
	}

}
