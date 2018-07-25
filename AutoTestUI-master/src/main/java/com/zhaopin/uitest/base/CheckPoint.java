package com.zhaopin.uitest.base;

import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class CheckPoint extends Assertion{

	private int flag = 0;//断言失败数儿记录

	private String caseName = "";

	private String testName = "";

	public CheckPoint(String testName, String caseName) {
		this.testName = testName;
		this.caseName = caseName;
	}

	/**
	 * 重写onAssertFailure方法,在断言失败时输出信息
	 */
	@Override
	public void onAssertFailure(@SuppressWarnings("rawtypes") IAssert assertCommand) {
		Log.error(testName+":"+caseName + " 断言失败：实际结果"+ assertCommand.getActual() +"预期结果"+ assertCommand.getExpected() +
				"--"+ assertCommand.getMessage());
		flag++;
	}

	/**
	 * 断言int类型
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public void equals(int actual, int expected, String message) {
		try {
			assertEquals(actual, expected, message);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断言float类型
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public void equals(float actual, float expected, String message) {
		try {
			assertEquals(actual, expected, message);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断言String类型
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public void equals(String actual, String expected, String message) {
		try {
			assertEquals(actual, expected, message);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断言double类型
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public void equals(double actual, double expected, String message) {
		try {
			assertEquals(actual, expected, message);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断言boolean类型
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public void equals(boolean actual, boolean expected, String message) {
		try {
			assertEquals(actual, expected, message);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最终断言结果（如果flag大于0,就说明整体用例失败）
	 */
	public void result(String message) {
		assertEquals(flag, 0);
		Reporter.log(testName+":"+caseName+":"+message);
		Log.info(testName+":"+caseName+":"+message);
	}

	/**
	 * 强制退出检查点
	 * @param message
	 */
	public void isFail(String message) {
		assertEquals(true, false,message);
	}
}
