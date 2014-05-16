package twitter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

public class StopWord {

	String text;

	public StopWord(String text) {
		this.text = text;
	}

	public StopWord() {

	}

	public static String[] stopWords(String text) {
		Set<String> set = new TreeSet<String>();

		BufferedReader br = null;
		try {

			URL resource = new StopWord().getClass().getResource("");
			String path = resource.getPath();
			System.out.println(path
					+ " ............bufferreader...................");
			path = path.replace("WEB-INF/classes/twitter", "stopwords.txt");

			br = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = br.readLine()) != null) {
				set.add(line.trim());
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String content = text.replaceAll("RT +@[^ :]+:?", "");
		content = content.replaceAll("http[s]?://([A-Za-z0-9.-/]+)\\s", "");
		content = content.replaceAll("[-+.^:!?#=<>$,']", " ");
		content = content.replaceAll("[^a-zA-Z0-9]", " ");
		content = content.replaceAll("\\w*\\d\\w*", " ");

		content = content.replaceAll("  +", " ");
		String[] words = content.split(" ");

		StringBuilder stb = new StringBuilder();
		for (String item : words) {
			if (!set.contains(item.toLowerCase())) {
				stb.append(item + " ");
			}
		}
		return stb.toString().split(" ");

	}

}
