package cpbl;

import java.io.File;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Player {
	final static String NameTag = "<div class=\"player_info_name\">";
	final static String PositionTag = "位置:";
	final static String PitchTableTag = "<!-- 投球 start -->";
	final static String HitTableTag = "<!-- 打擊 start -->";
	final static String FollowTableTag = "<!--	tpl版型	-->";

	String mOrginData;
	String mFollowData;
	String mPlayerName;
	int mPlayerNo;
	String mPlayerID;
	String mPosition;
	String mType;
	
	public Player(String id, String orginData, String followData) {
		mPlayerID = id;
		mOrginData = orginData;
		mFollowData = followData;
		parseData();
	}
	
	private void parseData() {
		setPersonData();
		setFollowData();
	}
	
	public void setPersonData() {
		String s = Library.foo(mOrginData, NameTag, "<br />");
		for (int i = 0; i < s.length(); i++)
			if (Library.isNum(s.substring(i))) {
				mPlayerName = s.substring(0, i);
				mPlayerNo = Integer.parseInt(s.substring(i));
				break;
			}
		mPosition = Library.foo(mOrginData, PositionTag, "</td>");
		
		String content;
		if (mPosition.equals("投手")) {
			mType = "Pitch";
			content = getScroeTable(mOrginData, PitchTableTag);
		} else {
			mType = "Hit";
			content = getScroeTable(mOrginData, HitTableTag);
		}
		Library.writeFile(new File("data\\" + mType + mPlayerID + ".tsv"), content);
	}
	
	public void setFollowData() {
		String ss = getScroeTable(mFollowData, FollowTableTag);
		String[] arr = ss.split("\r\n");
		for (int i = 0; i < arr.length; i++) {
			String s = arr[i];
			int b = s.indexOf("(");
			int e = s.indexOf(")");
			
			if (b > 0 && e > 0)
				arr[i] = s.substring(0, b) + "\t" + s.substring(b + 1, e) + s.substring(e + 1, s.length());
		}
		String t = Library.foo2(arr, "\r\n");
		Library.writeFile(new File("data\\Follow" + mType + mPlayerID + ".tsv"), t.replaceAll("\r\n\r\n", "\r\n"));
	}
	
	private String getScroeTable(String data, String tag) {
		String str = "";
		Element table = Jsoup.parse(data.substring(data.indexOf(tag))).select("table").get(0); //select the first table.
		Elements title = table.select("th");
		str = Library.spliteToTab(title.text(), " ") + "\r\n";
		Elements rows = table.select("tr");

		for (Element row: rows) {
			String tmp = Library.spliteToTab(row.select("td").text(), " ");
			if (tmp.equals("二軍成績")) break;
		    str += tmp + "\r\n";
		}
		return str;
	}
	
	public String getPlayerName() { return mPlayerName; }
	public String getPlayerType() { return mType; }
	public int getPlayerNo() { return mPlayerNo; }
	public String toString() {
		return String.format("Name: %s\tNo: %d\tPos: %s", mPlayerName, mPlayerNo, mPosition);
	}
}
