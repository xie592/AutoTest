package com.zhaopin.uitest.cases.login;

import org.testng.annotations.Test;
import com.zhaopin.uitest.base.TestBase;

public class LoginIHR extends TestBase{
	
	@Test(testName=".NET版本PASSPORT登陆IHR")
	public void loginIhr() {
		locator.linkTo("https://passport-old.zhaopin.com/org/login");
		locator.sendKeys("智联登录页", "用户名输入框", "lin_gao1");
		locator.sendKeys("智联登录页", "密码输入框", "*lin123*");
        locator.click("智联登录页", "验证码按钮");
        locator.moveToElementCoordinateClick("智联登录页", "验证码区域", 10, 10);
        locator.click("智联登录页", "验证按钮");
        locator.click("智联登录页", "登录按钮");
		checkPoint.equals(locator.getElementText("IHR首页","发布职位按钮"), "发布职位", "发布职位不存在");
		checkPoint.equals(locator.getCurrentUrl(), "https://ihr.zhaopin.com/", "IHR首页URL地址对比失败");
		checkPoint.result("登录成功");
	}
	
}
