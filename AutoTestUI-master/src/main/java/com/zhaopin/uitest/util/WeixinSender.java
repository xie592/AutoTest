package com.zhaopin.uitest.util;

public class WeixinSender {
	public static final String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx39d5b6e7cc28c612&corpsecret=D87DV5Zr96dqPm_TBAzFQXkDchLvFdoUE1M0tRDgP2mqhQSKlDUL822KXQjEx9br";
	public static final String sendUrl_q = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
	public static String touser = "";
	public static String toparty = "";
	public static String totag = "";
	
	public WeixinSender(String touser, String toparty){
		this.touser = touser;
		this.toparty = toparty;
	}
	
	@SuppressWarnings("static-access")
	public void sendNews(String title,String picurl){
		String result = new HttpRequest().sendGet(url);
		String access_token = JsonHelper.getValue(result,new String[]{"access_token"});
		String sendUrl = sendUrl_q+access_token;
		String param = "{\r\n" + 
				"   \"touser\" : \""+touser+"\",\r\n" + 
				"   \"toparty\" : "+toparty+",\r\n" + 
				"   \"totag\" : \""+totag+"\",\r\n" + 
				"   \"msgtype\" : \"news\",\r\n" + 
				"   \"agentid\" : 1,\r\n" + 
				"   \"news\" : {\r\n" + 
				"       \"articles\" : [\r\n" + 
				"           {\r\n" + 
				"               \"title\" : \""+title+"\",\r\n" + 
				"               \"description\" : \"loginError\",\r\n" + 
				"               \"url\" : \"https://www.zhaopin.com/\",\r\n" + 
				"               \"picurl\" : \""+picurl+"\",\r\n" + 
				"               \"btntxt\":\"losginError\"\r\n" + 
				"           }\r\n" + 
				"        ]\r\n" + 
				"   }\r\n" + 
				"}";
		String result_msg =  new HttpRequest().SendPost_json(sendUrl, param);
		System.out.println(param);
		System.out.println(result_msg); 
	}

}
