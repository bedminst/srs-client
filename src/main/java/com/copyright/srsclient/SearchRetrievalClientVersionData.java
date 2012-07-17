package com.copyright.srsclient;

import java.io.IOException;

import com.copyright.appintegrity.version.PropertyBasedVersionData;
import com.copyright.base.CCCRuntimeException;

/**
 * Version data for Search Retrieval Client module.
 * 
 * @author lwojtowicz
 */
public class SearchRetrievalClientVersionData extends PropertyBasedVersionData
{
	private static final long serialVersionUID = 1L;
	
	private static final String VERSION_DATA_FILENAME = "srs-client-versiondata.txt";
	
	private static SearchRetrievalClientVersionData INSTANCE;

	private SearchRetrievalClientVersionData() throws IOException
	{
		load( VERSION_DATA_FILENAME );
	}
	
	public static SearchRetrievalClientVersionData getInstance()
	{
		if ( INSTANCE == null )
		{
			try
			{
				INSTANCE = new SearchRetrievalClientVersionData();
			}
			catch ( IOException ioe )
			{
				throw new CCCRuntimeException( ioe );
			}
		}
		
		return INSTANCE;
	}
}
