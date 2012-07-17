/*
 * SearchRetrievalClientConfiguration.java
 * Copyright (c) 2010, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2010-03-22   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */
package com.copyright.srsclient.api;

import com.copyright.base.config.ClasspathConfiguration;

/**
 * Configuration properties for SearchRetrieval Client.
 * 
 * @author lwojtowicz
 *
 */
public class SearchRetrievalClientConfiguration extends ClasspathConfiguration
{
	private static final long serialVersionUID = 1L;
	
	public static final String CONFIG_FILE_NAME = "srs-client.properties";

	private static SearchRetrievalClientConfiguration sInstance;

	private SearchRetrievalClientConfiguration()
	{
		super( CONFIG_FILE_NAME );
	}
	
	public static SearchRetrievalClientConfiguration getInstance()
	{
		if ( sInstance == null ) sInstance = new SearchRetrievalClientConfiguration();
		
		return sInstance;
	}
	
}
