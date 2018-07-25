package com.zhaopin.uitest.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 封装业务逻辑操作的函数
 */
public class Functions{

    private WebDriver driver;
    private Locator locator;
    private Actions action;

    public Functions(WebDriver driver ,Actions action){
        this.driver = driver;
        this.locator = new Locator(driver, action, Config.objectRepository, Config.objectWaitTime);
    }
}
