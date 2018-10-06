package com.example.buzz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.buzz.api.GitProjectInfoDao;
import com.example.buzz.api.SocialMediaInfoDao;
import com.example.buzz.dao.GithubProjectInfoDao;
import com.example.buzz.dao.TwitterInfoDao;
import com.example.buzz.model.ProjectBuzzData;
import com.example.buzz.model.ProjectBuzzFlatSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class BuzzApplication.
 */
public class BuzzApplication {

	private static Logger logger = LoggerFactory.getLogger(BuzzApplication.class);

	/**
	 * The main method for the BuzzApplication.
	 *
	 * @param args
	 *            the arguments
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(final String[] args) throws UnsupportedEncodingException, IOException {
		final Properties applicationProperties = loadApplictionProperties();

		logger.info("Instantiating ProjectInfoDao instance");
		final GitProjectInfoDao projectInfoDao = new GithubProjectInfoDao(applicationProperties);

		logger.info("Instantiating SocialMediaInfoDao instance");
		final SocialMediaInfoDao socialeMediaDao = new TwitterInfoDao(applicationProperties);

		final ProjectBuzzService projectBuzzService = new ProjectBuzzService(projectInfoDao, socialeMediaDao);
		final Collection<ProjectBuzzData> projectsdata = projectBuzzService
				.getProjectBuzz(applicationProperties.getProperty("project.search.term"));

		final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ProjectBuzzData.class, new ProjectBuzzFlatSerializer()).create();
		logger.info("Output:: {}", gson.toJson(projectsdata));
	}

	/**
	 * Load appliction properties.
	 *
	 * @return the properties
	 * @throws IOException 
	 */
	private static Properties loadApplictionProperties() throws IOException {
		logger.info("Loading properties");
		final Properties properties = new Properties();
		try (InputStream propertiesInputStream = BuzzApplication.class.getClassLoader()
				.getResourceAsStream("application.properties")) {

			properties.load(propertiesInputStream);
		} 

		return properties;
	}

}
