package com.example.buzz.model;

import com.example.buzz.api.GitProjectData;
import com.google.gson.JsonElement;

/**
 * The Class GithubProjectData.
 */
public class GithubProjectData implements GitProjectData {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5259051871892577926L;

	private String name;
	private String fullName;
	private String summary;

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName the new full name
	 */
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	/* (non-Javadoc)
	 * @see com.example.buzz.api.GitProjectData#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	} 

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	/**
	 * Parses the GithubProjectData from github json response element.
	 *
	 * @param githubResponseJson the github response json
	 * @return the github project data
	 */
	public static GithubProjectData parse(final JsonElement githubResponseJson) {
		final GithubProjectData githubProjectData = new GithubProjectData();
		final String name = githubResponseJson.getAsJsonObject().get("name").getAsString();
		githubProjectData.setName(name);

		final String fullName = githubResponseJson.getAsJsonObject().get("full_name").getAsString();
		githubProjectData.setFullName(fullName);

		final JsonElement summary = githubResponseJson.getAsJsonObject().get("description");
		if (!summary.isJsonNull()) {
			githubProjectData.setSummary(summary.getAsString());
		}
		
		return githubProjectData;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GithubProjectData [name=" + name + ", fullName=" + fullName + ", summary=" + summary + "]";
	}

}
