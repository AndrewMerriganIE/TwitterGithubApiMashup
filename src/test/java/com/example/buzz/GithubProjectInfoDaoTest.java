package com.example.buzz;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.buzz.api.GitProjectData;
import com.example.buzz.dao.GithubProjectInfoDao;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
/**
 * 
 * Tests search and parse funtionalitty of GithubProjectInfoDao
 *
 */
public class GithubProjectInfoDaoTest {

	private final MockWebServer server = new MockWebServer();

	@Before
	public void setup() throws IOException {
		final InputStream jsonStream = GithubProjectInfoDaoTest.class.getClassLoader()
				.getResourceAsStream("GithubReactiveSearch.json");

		final MockResponse mockSearchResponse = new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8").addHeader("Cache-Control", "no-cache")
				.setBody(IOUtils.toString(jsonStream));

		server.enqueue(mockSearchResponse);
		server.start();
	}

	@Test
	public void shouldRetrieveReactiveProjects() throws IOException {

		final Properties properties = new Properties();
		properties.setProperty("git.service.scheme", "http");
		properties.setProperty("git.service.url", server.getHostName());
		properties.setProperty("git.service.port", "" + server.getPort());

		final GithubProjectInfoDao projectInfoDao = new GithubProjectInfoDao(properties);
		final List<GitProjectData> data = projectInfoDao.find("reactive", 10);
		
		assertEquals(1, data.size());
		final GitProjectData projectData = data.get(0);
		assertEquals("reactive", projectData.getName());
	}

	@After
	public void teardown() throws IOException {
		server.shutdown();
	}
}
