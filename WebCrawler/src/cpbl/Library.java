package cpbl;

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
	
	private static void log(Object obj) {
		System.out.println(obj.toString());
	}
}
