package alex;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.jsoup.Jsoup;

import java.io.*;

public class main {
	public ArrayList<String> htmlLinks;
	public ArrayList<card> cards;
	public LinkedHashMap<String, card> cardMap;

	public static void main(String[] args) {
		main m = new main();
		m.run();
	}

	public void run() {
		ParseCardLink();
		// checkDisappareCard();
		// System.out.println("check 1");
		// writeIdolList(new ArrayList<String>(cardMap.keySet()));
		// writeURLList();
		// readLinks();

		downloadAllLink();
		System.out.println("dl done");
		parseAllData();
	}

	public void ParseCardLink() {
		try {
			BufferedReader mbr = new BufferedReader(new InputStreamReader(new FileInputStream(""),"UTF-8"));
			htmlLinks = new ArrayList<String>(0);
			while (mbr.ready()) {
				htmlLinks.add(mbr.readLine());
			}
			System.out.println(htmlLinks.size());
		} catch (IOException e) {
			htmlLinks = new ArrayList<String>(0);
		}
		cards = new ArrayList<card>(0);
		cardMap = new LinkedHashMap<String, card>(0);
		for (String s : htmlLinks) {
			String cardName = s.substring(s.indexOf("<span data-jscol_sort=\"") + "<span data-jscol_sort=\"".length());
			cardName = cardName.substring(0, cardName.indexOf(""));
			int index = s.indexOf("<a href=\"") + "<a href=\"".length();
			s = s.substring(index);
			s = s.substring(0, s.indexOf("\">"));
			cards.add(new card(s, cardName));
			cardMap.put(cardName, new card(s, cardName));
		}
	}

	public void checkDisappareCard() {
		try {
			BufferedReader mbr = new BufferedReader(
					new InputStreamReader(new FileInputStream("F:\\final_source\\word\\list.txt"), "UTF-8"));
			if (mbr.ready())
				mbr.readLine();
			while (mbr.ready()) {
				String s[] = mbr.readLine().split("\t");
				if (cardMap.remove(s[0] + s[1]) == null) {
					System.out.println(s[0] + s[1]);
				}
			}
			mbr.close();
			for (String s : new ArrayList<String>(cardMap.keySet()))
				System.out.println(s);
		} catch (IOException e) {

		}
	}

	public void writeIdolList(ArrayList<String> list) {
		try {
			FileOutputStream fout = new FileOutputStream(new File("F:\\final_source\\word\\IdolNameList(byHTML).txt"));
			for (String s : list)
				fout.write((s + "\n").getBytes());
			fout.close();
			System.out.println("write done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeURLList() {
		ArrayList<String> data = new ArrayList<String>(0);
		for (String key : new ArrayList<String>(cardMap.keySet()))
			data.add(cardMap.get(key).fullName + "\t" + cardMap.get(key).url);
		try {
			Writer writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("F:\\final_source\\word\\linkList.txt"), "UTF-8"));
			for (String line : data)
				writer.write(line + "\n");
			writer.close();
		} catch (IOException e) {

		}
	}

	public void readLinks() {
		try {
			BufferedReader mbr = new BufferedReader(
					new InputStreamReader(new FileInputStream("F:\\final_source\\word\\linkList.txt"), "UTF-8"));
			cards = new ArrayList<card>(0);
			while (mbr.ready()) {
				String s[] = mbr.readLine().split("\t");
				cards.add(new card(s[1], s[0]));
			}
			System.out.println(cards.size());
		} catch (IOException e) {
			cards = new ArrayList<card>(0);
		}
	}

	public void downloadAllLink() {
		for (card c : cards) {
			try {
				URL website = new URL(c.url);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("D:\\final_source\\word\\data\\" + c.fullName + ".txt");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				System.out.println("dl " + c.fullName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void parseAllData() {
		for (card c : cards) {
			String content = "";
			try {
				BufferedReader mbr = new BufferedReader(new InputStreamReader(
						new FileInputStream("D:\\final_source\\word\\data\\" + c.fullName + ".txt"), "UTF-8"));
				while (mbr.ready()) {
					// default
					// content += mbr.readLine()+"\n";
					// step5
					String s = mbr.readLine();
					if (s.contains("����") || s.contains("���"))
						s = "\n" + s + "\n";
					content += s + "\n";
					// 憟�/L�����膩銝剜��"���"嚗���������銝�
				}
				mbr.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			// step1
			content = content.substring(content.indexOf("js_wikidb_main_name") + "js_wikidb_main_name".length(),
					content.indexOf("���瘜�"));
			// step2
			content = Jsoup.parse(content).text();
			// step3
			content = content.replaceAll("\">", "");
			// step4
			content = content.substring(0, content.indexOf("�����"))
					+ content.substring(content.indexOf("��憭扼����") - 1, content.length());
			content = content.replaceAll(",", "");
			content = content.replaceAll("\\s", "\n");

			try {
				Writer writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream("D:\\final_source\\word\\data\\" + c.fullName + ".txt"), "UTF-8"));
				writer.write(content);
				writer.close();
			} catch (IOException e) {
			}

		}
	}

}
