package com.example.buzz.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.buzz.api.SocialMediaData;
import com.google.gson.JsonObject;

/**
 * The Class TwitterData repersents data retrieved form twitter.
 */
public class TwitterData implements SocialMediaData{

	private static final long serialVersionUID = -4009969869685976866L;
	private Collection<String> tweets = Collections.emptyList();
	
	/**
	 * Gets the tweets.
	 *
	 * @return the tweets
	 */
	public Collection<String> getTweets() {
		return tweets;
	}

	/**
	 * Sets the tweets.
	 *
	 * @param tweets the new tweets
	 */
	public void setTweets(final Collection<String> tweets) {
		this.tweets = tweets;
	}

	/**
	 * Parses the TwitterData from twitters json response.
	 *
	 * @param twitterSearchResult the twitter search result
	 * @return the twitter data
	 */
	public static TwitterData parse(final JsonObject twitterSearchResult){
		final TwitterData twitterData = new TwitterData();
		final Collection<String> tweets = new ArrayList<>();
		twitterData.setTweets(tweets);
		
		twitterSearchResult.get("statuses").getAsJsonArray().spliterator().forEachRemaining((status) -> {
			tweets.add(status.getAsJsonObject().get("text").getAsString());
		}); 
		return twitterData;	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TwitterData [tweets=" + tweets + "]";
	}


}
