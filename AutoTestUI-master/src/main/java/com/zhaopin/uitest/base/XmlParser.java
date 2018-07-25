package com.zhaopin.uitest.base;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParser {
	
	private Document document;

	public XmlParser(String path) {
		loadFile(path);
	}
	
	/**
	 * 加载文件
	 * @param path 文件路径
	 */
	private void loadFile(String path) {
		SAXReader reader = new SAXReader();
		File file = new File(path);
		if(file.exists()) {
			try {
				this.document = reader.read(file);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}else {
			Log.error("待解析的XML"+ path +"文件不存在！");
		}
	}
	
	/**
	 * 获取单个节点
	 * @param xpathExpression xpath表达式
	 * @return element
	 */
	public Element getElement(String xpathExpression) {
		Element element = (Element) document.selectSingleNode(xpathExpression);
		if (element==null) {
			Log.error("查找对象"+ xpathExpression +"不存在！");
			return null;
		} else {
			return element;
		}
	}
	
	/**
	 * 获取单个节点的text
	 * @param xpathExpression xpath表达式
	 * @return
	 */
	public String getElementText(String xpathExpression) {
		return getElement(xpathExpression).getText();
	}
	
	/**
	 * 获取多个节点
	 * @param xpathExpression xpath表达式
	 * @return elements
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getElements(String xpathExpression) {
		List<Element> elements = document.selectNodes(xpathExpression);
		if (elements==null) {
			Log.error("查找多个对象"+ xpathExpression +"不存在！");
			return null;
		} else {
			return elements;
		}
	}	
	
	/**
	 * 获取多个节点的text
	 * @param xpathExpression xpath表达式
	 * @return resultList
	 */
	public List<String> getElementsText(String xpathExpression) {
		List<String> resultList = new ArrayList<>();
		List<Element> list = getElements(xpathExpression);
		for(Element element : list) {
			if(element!=null) {
				resultList.add(element.getText());
			}
		}
		return resultList;
	}	
	
	/**
	 * 获取父节点下面子节点的信息
	 * @param fatherXpathExpression 父节点的xpath表达式
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getChildElementInfo(String fatherXpathExpression) {
		Map<String, String> result = new LinkedHashMap<>();
		List<Element> list = getElement(fatherXpathExpression).elements();
		for(Element elementOne : list) {
			result.put(elementOne.getName(), elementOne.getText());
		}
		return result;
	}	
	
	/**
	 * 获取父节点下面子节点的信息
	 * @param element 父节点元素
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getChildElementInfo(Element element) {
		Map<String, String> result = new LinkedHashMap<>();
		List<Element> list = element.elements();
		for(Element elementTwo : list) {
			result.put(elementTwo.getName(), elementTwo.getText());
		}
		return result;
	}
	
	/**
	 * 合并Map表
	 * @param mapOne 第一个map
	 * @param mapTwo 第二个map
	 * @return mapTwo
	 */
	public Map<String, String> mapMarge(Map<String, String> mapOne, Map<String, String> mapTwo) {
		for (String key : mapOne.keySet()) {
			if(!mapTwo.containsKey(key)) {
				mapTwo.put(key, mapOne.get(key));
			}
		}
		return mapTwo;
	}

	public Boolean isExist(String xpathExpression){
		boolean flag = false;
		Element element = this.getElement(xpathExpression);
		if(element!=null){
			flag = true;
		}
		return flag;
	}
}
