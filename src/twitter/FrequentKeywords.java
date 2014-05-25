package twitter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import beans.Person;

public class FrequentKeywords {
	private String[] userIds;
	private int daysAGo;
	private int numberOfKeywords;
	public List<Person> people;
	private Map<String, Integer> map;
	private ResponseList<Status> statuses;
	private String userId;
	private ResponseList<Status> userTimeline;

	public FrequentKeywords(String[] userIds, int numberOfKeywords, int daysAGo) {
		this.userIds = userIds;
		this.numberOfKeywords = numberOfKeywords;
		this.daysAGo = daysAGo;
	}

	public FrequentKeywords(String userId) {
		this.userId = userId;

	}

	public void getFrequentKeywords(Twitter twitter) {
		StringBuilder resultString = new StringBuilder();
		map = new TreeMap<String, Integer>();
		people = new ArrayList<Person>();
		try {

			ResponseList<User> usersList = twitter.lookupUsers(userIds);

			for (User user : usersList) {

				Person person = new Person();
				person.setTwitterId(user.getId());
				person.setScreenName(user.getScreenName());
				person.setName(user.getName());
				person.setProfilePicture(user.getOriginalProfileImageURL());
				StringBuilder result = new StringBuilder();
				ResponseList<Status> statuses = twitter.getUserTimeline(user
						.getScreenName());
				for (Status st : statuses) {

					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -(daysAGo));
					if (st.getCreatedAt().after(cal.getTime())) {
						result.append(st.getText() + " ");
						resultString.append(st.getText() + " ");
					}

				}
				person.setMap(getKeywords(result.toString()));
				people.add(person);

			}
			//
		} catch (TwitterException e) {
			System.err.print("Failed to search tweets: " + e.getMessage());
		}
		map = getKeywords(resultString.toString());
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public List<Person> getUsers() {

		Twitter twitterConnection = null;
		try {
			twitterConnection = init();
			System.out.println(twitterConnection);
			if (userIds != null)
				getFrequentKeywords(twitterConnection);
			if (userId != null)
				getUserTimeLine(userId, twitterConnection);
			return people;
			// return new ArrayList<Person>();

		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
			return null;

		}
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

//	static Map<String, Integer> sortByValue(Map<String, Integer> map) {
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
	static Map<String, Integer> sortByValue(Map<String, Integer> map) {
		LinkedList<Entry<String,Integer>> list = new LinkedList<Entry<String,Integer>>();
		for(Entry<String, Integer> item:map.entrySet()){
			
			list.add(item);
		}
		Collections.sort(list, new Comparator() {
			@SuppressWarnings({ "rawtypes" })
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

	public ResponseList<Status> getUserTimeLine(String userId, Twitter twitter)
			throws TwitterException {
		userTimeline = twitter.getUserTimeline(userId, new Paging(1, 100));
		return userTimeline;

	}

	public ResponseList<Status> getUserTimeline() {

		return userTimeline;
	}

	public void setUserTimeline(ResponseList<Status> userTimeline) {
		this.userTimeline = userTimeline;
	}

}
