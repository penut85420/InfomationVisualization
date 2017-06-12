package cpbl;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.*;

public class Crawler {
	final static String tableTagStart = "<!-- 資料表格 start -->";
	final static String tableTagEnd = "<!-- 資料表格 end -->";
	
	public static void main(String[] args) throws Exception {
		ArrayList<String> brother = getAvaliablePlayer("E02");
		ArrayList<String> lamigo = getAvaliablePlayer("A02");		
		
		log("中信兄弟");
		for (String s: brother) {
			log(s);
			getPlayerData(s);
			Player p = new Player(loadPlayerData(s));
			log(p);
		}
		
		log("Lamigo桃猿");
		for (String s: lamigo) {
			getPlayerData(s);
			Player p = new Player(loadPlayerData(s));
			log(p);
		}
		
		log("Crawler Done");
	}
	
	private static void log(Object obj) {
		System.out.println(obj.toString());
	}
	
	public static ArrayList<String> getAvaliablePlayer(String id) {
		ArrayList<String> playerNo = new ArrayList<>();
		for (int offset = 0; ; offset += 20) {
			crawler("http://www.cpbl.com.tw/players.html?&status=1&teamno=" + id + "&offset=" + offset, "data\\aval.txt");
			String data = loadData("data\\aval.txt");
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
		return playerNo;
	}
	
	private static String loadData(String f) {
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

	public static void getPlayerData(String id) {
		crawler("http://www.cpbl.com.tw/players/person.html?player_id=" + id, "data\\" + id + ".txt");
	}
	
	private static void crawler(String url, String file) {
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
	
	public static String loadPlayerData(String id) {
		return loadData("data\\" + id + ".txt");
	}
}
