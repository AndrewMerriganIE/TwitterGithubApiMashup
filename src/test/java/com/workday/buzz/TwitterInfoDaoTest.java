package com.workday.buzz;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.buzz.api.SocialMediaInfoDao;
import com.example.buzz.dao.TwitterInfoDao;
import com.example.buzz.model.TwitterData;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
/**
 * 
 * Tests the oauth, search and parse funtionalitty of TwitterInfoDao
 *
 */
public class TwitterInfoDaoTest {

	private final MockWebServer server = new MockWebServer();

	@Before
	public void setup() throws IOException {
		final InputStream accessJsonStream = TwitterInfoDaoTest.class.getClassLoader()
				.getResourceAsStream("TwitterAcessTokenResponse.json");

		final MockResponse mockAccessResponse = new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8").addHeader("Cache-Control", "no-cache")
				.setBody(IOUtils.toString(accessJsonStream));

		final InputStream searchJsonStream = TwitterInfoDaoTest.class.getClassLoader()
				.getResourceAsStream("TwitterSearchResponse.json");

		final MockResponse mockSearchResponse = new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8").addHeader("Cache-Control", "no-cache")
				.setBody(IOUtils.toString(searchJsonStream));

		server.enqueue(mockAccessResponse);
		server.enqueue(mockSearchResponse);
		server.start();
	}

	@Test
	public void shouldRetrieveReactiveProjects() throws IOException {
		final Properties properties = new Properties();
		properties.setProperty("twitter.service.scheme", "http");
		properties.setProperty("twitter.service.url", server.getHostName());
		properties.setProperty("twitter.service.port", "" + server.getPort());
		properties.setProperty("twitter.consumer.secret", "TEST");
		properties.setProperty("twitter.consumer.key", "TEST");

		final SocialMediaInfoDao socialeMediaDao = new TwitterInfoDao(properties);
		final TwitterData twitterData = (TwitterData) socialeMediaDao.find("reactive");
		
		assertEquals(1, twitterData.getTweets().size());
		assertEquals(Collections.singletonList("reacts"), twitterData.getTweets());
	}

	@After
	public void teardown() throws IOException {
		server.shutdown();
	}

}
