package twitter;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MixQuery {
	private GeoLocation geoLocation;
	private int radius = 100;
	private int daysAgo;
	private boolean defaultLocation = true;
	private Map<String, Integer> words;

	public MixQuery(int daysAgo) {
		this.daysAgo = daysAgo;
	}

	public MixQuery(GeoLocation geoLocation, int daysAgo) {
		this.geoLocation = geoLocation;
	}

	public MixQuery() {

	}

	public void getMixedQueries(Twitter twitter) throws TwitterException {

		StringBuilder sb = new StringBuilder();
		Query query = new Query();
		// query.setResultType(Query.);
		if ((geoLocation == null) && (defaultLocation)) {
			query.setGeoCode(new GeoLocation(53.3836, -1.4669), radius,
					Query.KILOMETERS);
		} else if (geoLocation == null) {
			query.setGeoCode(new GeoLocation(0, 0), radius, Query.KILOMETERS);

		} else if (geoLocation != null)
			query.setGeoCode(geoLocation, radius, Query.KILOMETERS);
		query.setCount(100);
		QueryResult result = twitter.search(query);
		List<Status> statuses = result.getTweets();

		for (Status st : statuses) {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -(daysAgo));
			if (st.getCreatedAt().after(cal.getTime())) {
				if (st.getGeoLocation() != null) {
					sb.append(st.getText() + " ");
					System.out.println(st.getUser().getName());
				}
			}

		}
		words = getKeywords(sb.toString());
		// return sb.toString();
	}

	public void go() {

		Twitter twitterConnection = null;
		try {
			twitterConnection = init();
			getMixedQueries(twitterConnection);

		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();

		}

	}

	// public static void main(String[] args){
	// MixQuery mix = new MixQuery(15);
	// mix.setDefaultLocation(true);
	// mix.go();
	// System.out.println(mix.getKeywords());
	// }
	//
	public void setDefaultLocation(boolean defaultLocation) {
		this.defaultLocation = defaultLocation;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public static Map<String, Integer> getKeywords(String text) {
		String[] keywords = StopWord.stopWords(text);
		Map<String, Integer> map = new TreeMap<String, Integer>();

		for (String item : keywords) {
			Integer value = map.get(item);
			if (value != null) {
				map.put(item, ++value);
			} else {
				map.put(item, 1);
			}
		}

		return sortByValue(map);
	}

//	private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
//		List list = new LinkedList<>(map.entrySet());
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				return -((Comparable) ((Map.Entry) (o1)).getValue())
//						.compareTo(((Map.Entry) (o2)).getValue());
//			}
//		});
//
//		Map result = new LinkedHashMap();
//		for (Iterator it = list.iterator(); it.hasNext();) {
//			Map.Entry entry = (Map.Entry) it.next();
//			result.put(entry.getKey(), entry.getValue());
//		}
//		return result;
//	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
		LinkedList<Entry<String,Integer>> list = new LinkedList<Entry<String,Integer>>();
		for(Entry<String, Integer> item:map.entrySet()){
			
			list.add(item);
		}
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return -((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	private Twitter init() throws Exception {
		String consumerkey = "u7F2NWTzyd3t6YCCy1uEw";
		String consumersecret = "N8xk0IIBoBshR47T9EP3teXtgkblMBsiuDVAGJvVEbQ";
		String accesstoken = "1013000588-jeXwNa502Fgs1McucwhFP8aBhLnfTeVNFWqh1F1";
		String accesstokensecret = "VIY7lfFfXuTkkQkV5b5BpkULKuVAGslmTgROxWal2eUVT";
		Twitter twitterConnection = initTwitter(consumerkey, consumersecret,
				accesstoken, accesstokensecret);
		return twitterConnection;
	}

	private Twitter initTwitter(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(true);
		return (new TwitterFactory(cb.build()).getInstance());
	}

	public Map<String, Integer> getWords() {
		return words;
	}

	public void setWords(Map<String, Integer> words) {
		this.words = words;
	}

}
