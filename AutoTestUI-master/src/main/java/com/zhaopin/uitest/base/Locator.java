package com.zhaopin.uitest.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Locator {

    private WebDriver driver;
    private Actions action;
    private XmlParser xp;
    private int objectWaitTime;//超时等待时长

    public Locator(WebDriver driver, Actions action, String path, int objectWaitTime){
        this.driver = driver;
        this.action = action;
        this.objectWaitTime = objectWaitTime;
        xp = new XmlParser(path);
    }

	/**
     * 跳转连接
     * @param url
     * @return
     */
    public Boolean linkTo(String url){
        try{
            driver.get(url);
            return true;
        }catch (TimeoutException e) {
            Log.error("页面:"+url+"加载超时");
            return false;
        }
    }
    
	/**
	 * 获取当前URL地址
	 * @return
	 */
	public String getCurrentUrl(){
		return driver.getCurrentUrl();
	}

    /**
     * 设置等待时间
     * @param time 时间(秒)
     */
    public void wait(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在输入框中输入文本
     * @param page
     * @param object
     * @param text
     */
    public void sendKeys(String page, String object, String text){
        WebElement element = getElement(page, object);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * 获取文本
     * @param page
     * @param object
     */
    public String getElementText(String page, String object){
        WebElement element = getElement(page, object);
        return element.getText();
    }

    /**
     * 再元素上进行点击操作
     * @param page
     * @param object
     */
    public void click(String page, String object){
        WebElement element = getElement(page, object);
        element.click();
    }
    
    /**
     * 清楚浏览器缓存
     */
	public void deleteCookie(){
		driver.manage().deleteAllCookies();
	}  
	
	/**
	 * 在一个元素上移到到指定的坐标点进行点击操作
	 * @param page
	 * @param object
	 * @param x 横坐标
	 * @param y	纵坐标
	 */
	public void moveToElementCoordinateClick(String page, String object,int x, int y) {
		WebElement element = getElement(page, object);
		action.moveToElement(element, x, y).click().build().perform();
	}

    /**
     * 根据value选择
     * @param page
     * @param object
     * @param value
     */
    public void selectByValue(String page, String object, String value){
        Select select = select(page, object);
        select.selectByValue(value);
    }

    /**
     * 根据索引选择
     * @param page
     * @param object
     * @param index
     */
    public void selectByIndex(String page,String object,int index){
        Select select = select(page, object);
        select.selectByIndex(index);
    }

    /**
     * 根据VisibleText选择
     * @param page
     * @param object
     * @param text
     */
    public void selectByVisibleText(String page,String object,String text){
        Select select = select(page, object);
        select.selectByVisibleText(text);
    }
    
	/**
	 * 选中select元素中最后一个对象
	 * @param by 查找元素对象
	 */
	public void selectTheLast(String page, String object){
		Select select = new Select(getElement(page, object));
		select.selectByIndex(select.getOptions().size()-1);
	}

    /**
     * 给定一个索引开始值，然后随机select元素中的对象
     * (这个方法主要针对，被随机的select元素中第一个对象为空或者无意义的对象，比如：请选择等)
     * @param start 设置索引开始值
     */
    public void selectTheRandom(String page, String object, int start){
        Select select = new Select(getElement(page, object));
        Random random = new Random();
        int index = start + random.nextInt(select.getOptions().size() - start);
        select.selectByIndex(index);
    }

    /**
     * 获取findElements方法找到的元素个数
     * @param page
     * @param object
     * @return
     */
    public int getElementsCount(String page,String object){

        return getElements(page, object).size();
    }

    /**
     * 跳转到下一个handle
     */
    public void switchNextWindow() {
        Set<String> handles = driver.getWindowHandles();
        if(handles.size() > 0) {
            Object[] arrayHandle = handles.toArray();
            String nextHandle = String.valueOf(arrayHandle[arrayHandle.length - 1]);
            driver.switchTo().window(nextHandle);
        }
    }

    /**
     * 根据窗口title切换句柄
     * @param title 窗口标题
     * @return
     */
    public boolean switchWindowByTitle(String title) {
        Set<String> handles = driver.getWindowHandles();
        for(String handle : handles) {
            driver.switchTo().window(handle);
            if(driver.getTitle().contains(title)) {
                return true;
            }
        }
        Log.error("没有找到匹配的title");
        return false;
    }

    /**
     * 设置等待时间，判断元素是否存在
     * @return
     */
    public boolean elementIsPresent(String page,String object) {
        final By by = getBy(page, object);
        Boolean flag = false;
        try {
            flag = new WebDriverWait(driver, objectWaitTime)
                        .until(new ExpectedCondition<Boolean>() {
                            @Override
                            public Boolean apply(WebDriver driver) {
                                driver.findElement(by);
                                return true;
                            }
                        });
        } catch (Exception e) {
            Log.error("超时元素不存在！");
        }
        return flag;
    }

    /**
     * 添加JS脚本
     * @param jsString JS表达式
     */
    public void addjS(String jsString) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript(jsString);
    }

    /**
     * 移除input元素的只读属性
     * @param id html中元素的id
     */
    public void removeReadOnly(String id) {
        addjS("document.getElementById('" + id + "').readOnly = false;");
    }

    /**
     * 滚屏操作
     * @param y 屏幕的纵坐标,比如scrollTo(0,10000)
     */
    public void scrollToBottom(int y) {
        addjS("scrollTo(0," + y + ");");
    }

    /**
     * 添加cookies
     * @param url 添加cookies的地址
     * @param cookie 要添加的cookies
     */
    public void addCookies(String url, Map<String, String> cookie) {
        driver.get(url);
        for(String key : cookie.keySet()) {
            driver.manage().addCookie(new Cookie(key, cookie.get(key)));
        }
        driver.get(url);
    }


/*------------------------------------------------------以下为私有方法------------------------------------------------------*/

    /**
     * 获取element单对象
     * @param page
     * @param object
     * @return
     */
    private WebElement getElement(String page, String object){
        By by = getBy(page,object);
        WebElement element = waitForElement(by);
        if(elementIsDisplay(element)){
            return element;
        }else {
            Log.error("对象存在但不可见");
        }
        return element;
    }

    /**
     * 获取element多对象
     * @param page
     * @param object
     * @return
     */
    private List<WebElement> getElements(String page, String object){
        By by = getBy(page,object);
        List<WebElement> elements = driver.findElements(by);
        if(elements==null){
            Log.error("对象:"+page+"-"+object+"查询失败");
            return null;
        }else {
            return elements;
        }
    }

    /**
     * 获取select对象
     * @param page
     * @param object
     * @return
     */
    private Select select(String page, String object) {
        return new Select(this.getElement(page, object));
    }

    /**
     * 智能等待元素出现
     * @param by 查找元素对象
     * @return
     */
    private WebElement waitForElement(final By by) {
        WebElement element = null;
        try {
            element = new WebDriverWait(driver, objectWaitTime)
                    .until(new ExpectedCondition<WebElement>() {
                        @Override
                        public WebElement apply(WebDriver input) {
                            return driver.findElement(by);
                        }
                    });
        }catch(Exception e) {
            Log.error(by + "等待" + objectWaitTime + "秒后未出现！");
        }
        return element;
    }

    /**
     * 设置等待时间，判断元素是否可见
     * @return
     */
    private boolean elementIsDisplay(final WebElement element) {
        Boolean flag = false;
        flag = new WebDriverWait(driver, objectWaitTime)
                    .until(new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return element.isDisplayed();
                        }
                    });
        return flag;
    }

    /**
     *  在对象库中获取type、value
     * @param page
     * @param object
     * @return
     */
    private By getBy(String page, String object){
        By by = null;
        String type = xp.getElementText("/对象/" + page + "/" + object + "/type").trim();
        String value = xp.getElementText("/对象/" + page + "/" + object + "/value").trim();
        if(xp.isExist("/对象/" + page + "/" + object)){
            switch (type.toLowerCase()) {
                case "id":
                    by = By.id(value);
                    break;
                case "name":
                    by = By.name(value);
                    break;
                case "classname":
                    by = By.className(value);
                    break;
                case "tagname":
                    by = By.tagName(value);
                    break;
                case "linktext":
                    by = By.linkText(value);
                    break;
                case "partiallinktext":
                    by = By.partialLinkText(value);
                    break;
                case "xpath":
                    by = By.xpath(value);
                    break;
                case "cssselector":
                    by = By.cssSelector(value);
                    break;
                default:
                    Log.error("元素定位错误! By "+type+"不存在此类型 ");
                    break;
            }
        }else {
            Log.error("对象:"+page+"-"+object+"在对象库中不存在");
        }
        return by;
    }

}
