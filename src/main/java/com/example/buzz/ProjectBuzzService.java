package com.example.buzz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.buzz.api.GitProjectData;
import com.example.buzz.api.GitProjectInfoDao;
import com.example.buzz.api.SocialMediaData;
import com.example.buzz.api.SocialMediaInfoDao;
import com.example.buzz.model.ProjectBuzzData;

/**
 * The Class ProjectBuzzService uses ProjectInfoDao to locate projects and a
 * SocialMediaInfoDao to augment the retrieved project data with social media information.
 */
public class ProjectBuzzService {

	private static Logger logger = LoggerFactory.getLogger(BuzzApplication.class);

	private final GitProjectInfoDao projectInfoDao;
	private final SocialMediaInfoDao socialMediaDao;

	/**
	 * Instantiates a new project buzz service.
	 *
	 * @param projectInfoDao
	 *            the project info dao 
	 * @param socialMediaDao
	 *            the social media dao
	 */
	public ProjectBuzzService(final GitProjectInfoDao projectInfoDao, final SocialMediaInfoDao socialMediaDao) {
		this.projectInfoDao = projectInfoDao;
		this.socialMediaDao = socialMediaDao;
	}

	/**
	 * Gets the project buzz.
	 *
	 * @param searchName
	 *            the search name
	 * @return the project buzz
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Collection<ProjectBuzzData> getProjectBuzz(final String searchName) throws IOException {

		final List<GitProjectData> projects = projectInfoDao.find(searchName, 10);

		logger.debug("Retrieved projects {}", projects);
		final Collection<ProjectBuzzData> combinedProjectSocialMediaData = new ArrayList<>();

		projects.forEach((project) -> {
			try {
				final SocialMediaData socialMediaData = socialMediaDao.find(project.getName());
				combinedProjectSocialMediaData.add(new ProjectBuzzData(project, socialMediaData));

			} catch (final IOException e) {
				logger.error("An exception occured whilst retieving social media data for project::{}", projects);
			}
		});

		return combinedProjectSocialMediaData;
	}
}
