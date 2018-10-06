package com.example.buzz.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.buzz.api.GitProjectData;
import com.example.buzz.api.GitProjectInfoDao;
import com.example.buzz.model.GithubProjectData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The Class GithubProjectInfoDao.
 */
public class GithubProjectInfoDao implements GitProjectInfoDao {

	private final OkHttpClient client = new OkHttpClient();

	private final String scheme;
	private final String url;
	private final int port;

	/**
	 * Instantiates a new github project info dao.
	 *
	 * @param properties the properties
	 */
	public GithubProjectInfoDao(final Properties properties) {
		url = properties.getProperty("git.service.url");
		scheme = properties.getProperty("git.service.scheme");
		port = Integer.parseInt(properties.getProperty("git.service.port", scheme.equals("https")? "443": "80"));
	}

	/* (non-Javadoc)
	 * @see com.example.buzz.api.GitProjectInfoDao#find(java.lang.String, int)
	 */
	@Override
	public List<GitProjectData> find(final String repoName, final int limit) throws IOException {

		final HttpUrl serachUrl = new HttpUrl.Builder().scheme(scheme).host(url).port(port).addPathSegment("search")
				.addPathSegment("repositories").addQueryParameter("q", repoName).addQueryParameter("sort", "stars")
				.addQueryParameter("order", "desc").build();

		final Request request = new Request.Builder().url(serachUrl).build();

		try (Response response = client.newCall(request).execute()) {
			return parseProjectsData(response.body().string(), limit);
		}

	}

	private static List<GitProjectData> parseProjectsData(final String responseBodyJson, final int limit) {
		final JsonObject responseJson = new JsonParser().parse(responseBodyJson).getAsJsonObject();
		final List<GitProjectData> projects = new ArrayList<>(); 

		int projectsParsed = 0;
		for (final JsonElement item : responseJson.getAsJsonArray("items")) {
			projects.add(GithubProjectData.parse(item));
			projectsParsed++;
			if (projectsParsed == limit) {
				break;
			}
		}
  
		return projects;
	}

}
