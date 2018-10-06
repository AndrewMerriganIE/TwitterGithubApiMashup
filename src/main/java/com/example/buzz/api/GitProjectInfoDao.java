package com.example.buzz.api;

import java.io.IOException;
import java.util.List;

/**
 * The Interface GitProjectInfoDao is used to retrive project information from a
 * hosting service.
 */
public interface GitProjectInfoDao {

	/**
	 * Find a limited number of projects on a hosting service using the
	 * searchname.
	 *
	 * @param searchName
	 *            the search name
	 * @param limit
	 *            the number of projects to remove
	 * @return the list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	List<GitProjectData> find(String searchName, int limit) throws IOException;
}
 