package com.zhaopin.uitest.base;

public class Config {

    public static int objectWaitTime;   //智能等待元素时长
    public static int pageLoadTimeout;  //页面加载时长
    public static int retryTimes;      //失败重跑次数
    public static String objectRepository;  //对象库路径

    static {
        XmlParser xmlParser = new XmlParser("config.xml");
        objectWaitTime = Integer.parseInt(xmlParser.getElementText("/config/objectWaitTime"));
        pageLoadTimeout = Integer.parseInt(xmlParser.getElementText("/config/pageLoadTimeout"));
        retryTimes = Integer.parseInt(xmlParser.getElementText("/config/retryTimes"));
        objectRepository = xmlParser.getElementText("/config/objectRepository");
    }
}
