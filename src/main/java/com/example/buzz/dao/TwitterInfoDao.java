package com.example.buzz.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.buzz.api.SocialMediaData;
import com.example.buzz.api.SocialMediaInfoDao;
import com.example.buzz.model.TwitterData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * DAO class used to communicate with Twitter over rest.
 *
 */
public class TwitterInfoDao implements SocialMediaInfoDao {

	private static Logger logger = LoggerFactory.getLogger(TwitterInfoDao.class);
	private final OkHttpClient client = new OkHttpClient();

	private final String scheme;
	private final String url;
	private final int port;
	
	private String consumerKey = "";
	private String consumerSecret = "";
	private final String accessToken;

	/**
	 * Instantiates a new twitter info DAO based on the provided properties.
	 *
	 * @param properties
	 *            the properties
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public TwitterInfoDao(final Properties properties) throws UnsupportedEncodingException, IOException {
		scheme = properties.getProperty("twitter.service.scheme");
		url = properties.getProperty("twitter.service.url");
		port = Integer.parseInt(properties.getProperty("twitter.service.port", scheme.equals("https")? "443": "80"));
		
		consumerKey = properties.getProperty("twitter.consumer.key");
		consumerSecret = properties.getProperty("twitter.consumer.secret");
		accessToken = generateAccessToken();
	} 

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.workday.buzz.api.SocialMediaInfoDao#find(java.lang.String)
	 */
	@Override
	public SocialMediaData find(final String seatchTerm) throws IOException {
		final HttpUrl searchUrl = new HttpUrl.Builder().scheme(scheme).host(url).port(port).addPathSegment("1.1")
				.addPathSegment("search").addPathSegment("tweets.json").addQueryParameter("q", seatchTerm).build();

		final Request request = new Request.Builder().addHeader("Authorization", "Bearer " + accessToken).url(searchUrl)
				.build();

		try (Response response = client.newCall(request).execute()) {
			final JsonObject twitterResponseJson = new JsonParser().parse(response.body().string()).getAsJsonObject();
			logger.debug("Got reponse from twitter::{}", twitterResponseJson.toString());
			return TwitterData.parse(twitterResponseJson);
		}
	}

	/**
	 * Gets an access token using the consumer key and secret in order to be
	 * able to search twitter.
	 *
	 * @return the access token
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String generateAccessToken() throws UnsupportedEncodingException, IOException {
		final String encoded = getEncodedAuthorizationString();

		final RequestBody formBody = new FormBody.Builder().add("grant_type", "client_credentials").build();

		final HttpUrl accessUrl = new HttpUrl.Builder().scheme(scheme).host(url).port(port).addPathSegment("oauth2")
				.addPathSegment("token").build();

		final Request request = new Request.Builder().url(accessUrl).addHeader("Authorization", encoded).post(formBody)
				.build();

		try (Response response = client.newCall(request).execute()) {
			final JsonObject responseJson = new JsonParser().parse(response.body().string()).getAsJsonObject();
			logger.debug("Twitter auth response::{}",responseJson);
			return responseJson.get("access_token").getAsString();
		}
	}

	/**
	 * Gets the encoded authorization string used to request an access token.
	 *
	 * @return the encoded authorization string
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	private String getEncodedAuthorizationString() throws UnsupportedEncodingException {
		final String authString = URLEncoder.encode(consumerKey, "UTF-8") + ':' + URLEncoder.encode(consumerSecret, "UTF-8");
		return "Basic " + DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
	}
}
