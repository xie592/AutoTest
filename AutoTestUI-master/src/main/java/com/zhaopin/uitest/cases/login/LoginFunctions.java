package com.zhaopin.uitest.cases.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.zhaopin.uitest.base.Config;
import com.zhaopin.uitest.base.Locator;

/**
 * 封装业务逻辑操作的函数
 */
public class LoginFunctions{

    private WebDriver driver;
    private Locator locator;
    private Actions action;

    public LoginFunctions(WebDriver driver ,Actions action){
        this.driver = driver;
        this.locator = new Locator(driver, action, Config.objectRepository, Config.objectWaitTime);
    }
    
    
}
