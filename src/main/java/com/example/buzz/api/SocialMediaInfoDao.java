package com.example.buzz.api;

import java.io.IOException;

/**
 * The Interface SocialMediaInfoDao is implemented by classes capable of
 * retrieving data form social media.
 */
public interface SocialMediaInfoDao {

	/**
	 * Find information about the search term.
	 *
	 * @param seatchTerm
	 *            the search term
	 * @return the social media data
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */ 
	public SocialMediaData find(String searchTerm) throws IOException;
	
	
}
