package com.example.buzz.api;

import java.io.Serializable;

/**
 * interface for information retrieved about a project.
 */
public interface GitProjectData extends Serializable {

	/**
	 * Gets the name of the Git Project.
	 *
	 * @return the name
	 */
	public String getName();

}
 