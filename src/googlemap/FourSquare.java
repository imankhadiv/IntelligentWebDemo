package googlemap;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Location;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.Photos;
//http://4sq.com/1qezcsR
public class FourSquare {
	static String CLIENT_ID="KBG4IHZCCEPFXDBBRGS5NZAE45VHUBVXY1K22W2F1C3CCH3Y";
	static String CLIENT_SECRET="EJSGG20AKXFOUAJO1A0RTLMPLX1SZUC3NBJZKWECROT221HX";
	static String URI="http://www.yourapp.com";
	static String tokenString="LJIXSK34YNUO54OZKLB2XNMLQOOK32XEDEXVFMPDN4QRAJIX";
	
	private String getFullURL(String shortURLs) throws IOException {
		URL shortUrl = new URL(shortURLs);
		final HttpURLConnection httpURLConnection = (HttpURLConnection) shortUrl
				.openConnection();
		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.connect();
		final String header = httpURLConnection.getHeaderField("Location");
		return header;
	}

	private String expandUrl(String shortURLs) {
		String url = shortURLs;
		// String initialUrl = url;
		while (url != null) {
			try {
				url = getFullURL(shortURLs);
				if (url != null)
					shortURLs = url;
				else {
					url = shortURLs;
					break;
				}
			} catch (IOException e) {
				// this is not a tiny URL as it is not redirected!
				break;
			}
		}
		return url;
	}
//	public FoursquareInformationList getLocationInformation(String shortURLs) {
	public URL_Info getLocationInformation(String shortURLs) {
		FoursquareApi myApi = new FoursquareApi(CLIENT_ID, CLIENT_SECRET, URI);
		myApi.setoAuthToken(tokenString);
		// expand the url if it is a short url!
		String url = expandUrl(shortURLs);
		// if it is not a 4square login url then we return!
		if (!((url.startsWith("https://foursquare.com/"))
				&& (url.contains("checkin")) && (url.contains("s=")))||url == null) {
			return null;
		} else {
			// url now contains the full url!
			Pattern pId = Pattern.compile(".+?checkin/(.+?)\\?s=.+",
					Pattern.DOTALL);
			Matcher matche = pId.matcher(url);
			String checkInId = (matche.matches()) ? matche.group(1) : "";
			Pattern pSig = Pattern.compile(".+?\\?s=(.*)\\&.+", Pattern.DOTALL);
			matche = pSig.matcher(url);
			String sig = (matche.matches()) ? matche.group(1) : "";
			Result<Checkin> chck = null;
			Result<CompleteVenue> completeCheck  = null;
			URL_Info currentInfo = null;
			try {
				chck = myApi.checkin(checkInId, sig);
//				myApi.photo(checkInId);
				Checkin cc = chck.getResult();
				CompactUser user = cc.getUser();
				CompactVenue venue = cc.getVenue();
				System.out.println(venue.getId());
				
				completeCheck = myApi.venue(venue.getId());
				System.out.println(completeCheck.getResult().getName()+completeCheck.getResult().getDescription());
				//get photo url
				Result<PhotoGroup> photoResult =myApi.venuesPhotos(venue.getId(), "venue", 10, 10);
				PhotoGroup mPhoto = photoResult.getResult();
				Photo[] photoarray=mPhoto.getItems();
			
				// TODO save four square account and venue to RDF
				
				
				currentInfo = new URL_Info(user, venue);
				//set pic
				if(photoarray.length>0)
				{
					currentInfo.getVenue().setPhotoURL(photoarray[0].getUrl());
				}
				
			} catch (FoursquareApiException e) {
				e.printStackTrace();
			}
			return currentInfo;
		}
	}
}
