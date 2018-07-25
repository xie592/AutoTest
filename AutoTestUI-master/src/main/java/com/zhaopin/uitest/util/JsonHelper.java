package com.zhaopin.uitest.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonHelper {
	/**
	 * 解析json某个key的value
	 * @param json
	 * @param key
	 * @return
	 */
	public static String parseJson(String json, String dataKey, String key) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject object = jsonObject.getJSONObject(dataKey);
        Object value = object.get(key);
		return value.toString();
	}

    /**
     * 解析json Array key 的value
     * @param json
     * @param dataKey
     * @param key
     * @return
     */
    public static String parseJsonArray(String json, String dataKey, String key) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray array = jsonObject.getJSONArray(dataKey);
        JSONObject row = array.getJSONObject(0);
        Object value = row.get(key);

        return value.toString();
    }
    
    /**
     * 解析json Array key 的value
     * @param json
     * @param dataKey
     * @param key
     * @return
     */
    public static String parseJsonArray_num(String json, String dataKey,int num,String key) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray array = jsonObject.getJSONArray(dataKey);
        JSONObject row = array.getJSONObject(num);
        Object value = row.get(key);

        return value.toString();
    }
    /**
     * 将JSONArray里面的数据解析出来
     * @param json
     * @param dataKey
     * @return
     */
    public static String parseJsonData(String json, String dataKey, int num) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject object = jsonObject.getJSONObject("Data");
        JSONArray array = object.getJSONArray(dataKey);
        JSONObject row = array.getJSONObject(num);

        return row.toString();
    }
    
    /**
	 * 解析json某个key的value
	 * @param json
	 * @param key
	 * @return
	 */
	public static String parseJsonObj_arr(String json, String dataKey, String key) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject object = jsonObject.getJSONObject("Data");
        JSONArray array = object.getJSONArray(dataKey);
        JSONObject row = array.getJSONObject(0);
        Object value = row.get(key);
		return value.toString();
	}


	/**
	 * 从json中取出指定key的value
	 * @param data json字符串
	 * @param keys  指定key前面的所有容器和指定key 
	 * @return value
	 */
	public static String getValue(String data, String[] keys){
		JSONObject jo = JSONObject.fromObject(data);
		if(keys[0].equals("") || keys[0] == null){
			return data;
		}
		for(int i=0; i<keys.length-1; i++){
			if(jo.getString(keys[i]).charAt(0) == '['){
				JSONArray ja = jo.getJSONArray(keys[i]);
				try{
					if(i<keys.length-1 && StrHelper.isNum(keys[i+1])){
						jo = ja.getJSONObject(Integer.parseInt(keys[++i]));
					}else{
						jo = ja.getJSONObject(0);
					}
				}catch(IndexOutOfBoundsException e){
					String notHave = "";
					for(int j=0; j<=i; j++){
						notHave += keys[j] + ":{ ";
					}
					throw new RuntimeException("json中不包含"+notHave+"字段 \n json:" + data + "\n keys:" + notHave);
				}
			}else{
				jo = jo.getJSONObject(keys[i]);
			}
		}
		String ret = null;
		try{
			ret = jo.getString(keys[keys.length-1]); 
		}catch(JSONException e){
			throw new RuntimeException("从"+jo+"中取"+keys[keys.length-1]+"时发生异常\n" + "\n json:" + data);
		}
		return jo.getString(keys[keys.length-1]);
	}
	
	
}
