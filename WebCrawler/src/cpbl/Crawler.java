package cpbl;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.*;

public class Crawler {
	final static String tableTagStart = "<!-- 資料表格 start -->";
	final static String tableTagEnd = "<!-- 資料表格 end -->";
	
	final static String TeamBrother = "E02";
	final static String TeamLamigo = "A02";
	final static String TeamFubon = "B04";
	final static String TeamLion = "L01";
	final static String[] AllTeam = {TeamBrother, TeamLamigo, TeamFubon, TeamLion};
	
	final static String TypeFollow = "Follow";
	final static String TypePerson = "Person";
	
	public static void main(String[] args) throws Exception {
 		unitTest();
		log("Crawler Done");
	}
	
	private static void unitTest() {
		for (String teamID: AllTeam)
			getTeam(teamID);
	}
	
	private static void getTeam(String teamID) {
		ArrayList<String> teamData = getAvaliablePlayer(teamID);
		String pitcher = "ID\tNAME\r\n";
		String hitter = "ID\tNAME\r\n";
		for (String id: teamData) {
			Player p = new Player(id, getData(TypePerson, id), getData(TypeFollow, id));
			String t = String.format("%s\t%s\r\n", id, p.getPlayerName());
			if (p.getPlayerType().equals("Pitch"))
				pitcher += t;
			else hitter += t;
		}
		Library.writeFile("data\\PlayerPitcher" + teamID + "ID.txt", pitcher);
		Library.writeFile("data\\PlayerHitter" + teamID + "ID.txt", hitter);
	}
	
	private static void log(Object obj) {
		System.out.println(obj.toString());
	}
	
	public static ArrayList<String> getAvaliablePlayer(String id) {
		ArrayList<String> playerNo = new ArrayList<>();
		File tmp = new File("data\\aval.txt");
		for (int offset = 0; ; offset += 20) {
			goCraw("http://www.cpbl.com.tw/players.html?&status=1&teamno=" + id + "&offset=" + offset, tmp.getPath());
			String data = Library.loadData("data\\aval.txt");
			int begin = data.indexOf(tableTagStart) + tableTagStart.length();
			int end = data.indexOf(tableTagEnd);
			String[] s = data.substring(begin, end).split("player_id=");
			if (s.length <= 1) break;
				for (String ss: s) {
					String t = ss.substring(0, 4);
					t = t.replaceAll("[ \r\n\t\0]", "");
					if (t.length() > 0)
						playerNo.add(t);
				}
		}
		if (tmp.exists()) tmp.delete();
		return playerNo;
	}
	
	public static String getData(String type, String id) {
		goCraw("http://www.cpbl.com.tw/players/" + type + ".html?player_id=" + id, "data\\" + type + id + ".txt");
		return loadData(type, id);
	}
	
	private static void goCraw(String url, String file) {
		try {
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(file);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String loadData(String type, String id) {
		return Library.loadData("data\\" + type + id + ".txt");
	}
}
