/**
 * 判断格式是否正确的类
 */
package com.gdut.pet.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeFormat {
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	public static boolean isLogical(String name) {

		String str = "[^~?!@#$%\\/|&*()^]+";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public static boolean isPassword(String name) {
		String str = "^[a-zA-Z0-9_]{6,15}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(name);
		return m.matches();
	}

	
	public static boolean isQQNumber(String name) {
		String str = "^[0-9_]{1,12}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(name);
		return m.matches();
	}
}
