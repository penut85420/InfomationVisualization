package cpbl;

import java.io.*;
import java.util.Arrays;

public class Library {
	public static void main(String[] args) {
		log(isNum("A123"));
	}
	
	public static boolean isNum(String s) {
		String ss = "09";
		int c0 = ss.codePointAt(0);
		int c9 = ss.codePointAt(1);
		for (int i = 0; i < s.length(); i++) {
			if (s.codePointAt(i) < c0) return false;
			if (s.codePointAt(i) > c9) return false;
		}
		return true;
	}
	
	public static String foo(String content, String beginTag, String endTag) {
		int t = content.indexOf(beginTag);
		int tagLen = beginTag.length();
		int begin = t + tagLen;
		int end = content.indexOf(endTag, begin);
		return content.substring(begin, end).replaceAll(" ", "");
	}
	
	public static void log(Object obj) {
		System.out.println(obj.toString());
	}
	
	public static void writeFile(String file, String content) {
		writeFile(new File(file), content);
	}
	
	public static void writeFile(File file, String content) {
		try {
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(new String("\uFEFF" + content).getBytes("UTF-8"));
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static String loadData(String f) {
		try {
			FileInputStream fin = new FileInputStream(f);
			byte ba[] = new byte[fin.available()];
			fin.read(ba);
			String content = new String(ba, "UTF-8");
			content = content.replace("\uFEFF", "");
			fin.close();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String spliteToTab(String str, String split) {
		String[] arr = str.split(split);
		String s = foo2(arr, "\t");
		return s;
	}
	
	public static String foo2(String[] arr, String re) {
		String s = Arrays.toString(arr);
		s = s.replaceAll(", ", re);
		s = s.substring(1, s.length() - 1);
		return s;
	}
}
