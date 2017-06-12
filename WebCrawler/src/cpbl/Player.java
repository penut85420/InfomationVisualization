package cpbl;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Player {
	final static String NameTag = "<div class=\"player_info_name\">";
	final static String PositionTag = "位置:";
	final static String PitchTableTag = "<!-- 投球 start -->";
	final static String HitTableTag = "<!-- 打擊 start -->";
	

	String mOrginData;
	String mPlayerName;
	int mPlayerNo;
	String mPosition;
	
	public Player(String orginData) {
		mOrginData = orginData;
		parseData();
	}
	
	private void parseData() {
		String s = Library.foo(mOrginData, NameTag, "<br />");
		for (int i = 0; i < s.length(); i++)
			if (Library.isNum(s.substring(i))) {
				mPlayerName = s.substring(0, i);
				mPlayerNo = Integer.parseInt(s.substring(i));
				break;
			}
		mPosition = Library.foo(mOrginData, PositionTag, "</td>");
		
		if (mPosition.equals("投手")) {
			getScroeTable(PitchTableTag);
		} else {
			getScroeTable(HitTableTag);
		}
	}
	
	private void getScroeTable(String tag) {
		Element table = Jsoup.parse(mOrginData.substring(mOrginData.indexOf(tag))).select("table").get(0); //select the first table.
		Elements title = table.select("th");
		System.out.println(title.text());
		Elements rows = table.select("tr");

		for (Element row: rows) {
		    Elements cols = row.select("td");
		    System.out.println(cols.text());
		}
	}
	
	public String getPlayerName() { return mPlayerName; }
	public int getPlayerNo() { return mPlayerNo; }
	public String toString() {
		return String.format("Name: %s\tNo: %d\tPos: %s", mPlayerName, mPlayerNo, mPosition);
	}
}
