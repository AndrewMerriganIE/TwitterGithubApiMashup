package com.example.buzz.model;

import com.example.buzz.api.GitProjectData;
import com.example.buzz.api.SocialMediaData;

/**
 * The Class ProjectBuzzData.
 */
public class ProjectBuzzData {
	
	private GitProjectData projectData;
	private SocialMediaData socialMediaData;


	/**
	 * Instantiates a new project buzz data.
	 *
	 * @param projectData
	 *            the project data
	 * @param socialMediaData
	 *            the social media data
	 */
	public ProjectBuzzData(final GitProjectData projectData, final SocialMediaData socialMediaData) {
		this.socialMediaData = socialMediaData;
		this.projectData = projectData;
	}

	/** 
	 * Gets the social media data.
	 *
	 * @return the social media data
	 */
	public SocialMediaData getSocialMediaData() {
		return socialMediaData;
	}

	/**
	 * Sets the social media data.
	 *
	 * @param socialMediaData
	 *            the new social media data
	 */
	public void setSocialMediaData(final SocialMediaData socialMediaData) {
		this.socialMediaData = socialMediaData;
	}

	/**
	 * Gets the project data.
	 *
	 * @return the project data
	 */
	public GitProjectData getProjectData() {
		return projectData;
	}

	/**
	 * Sets the project data.
	 *
	 * @param projectData
	 *            the new project data
	 */
	public void setProjectData(final GitProjectData projectData) {
		this.projectData = projectData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProjectDataDecorator [socialMediaData=" + socialMediaData + ", projectData=" + projectData + "]";
	}

}
