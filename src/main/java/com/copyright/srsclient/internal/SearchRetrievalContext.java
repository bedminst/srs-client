/**
 * 
 */
package com.copyright.srsclient.internal;

import java.io.Serializable;

import com.copyright.srsclient.web.ArtSearch;
import com.copyright.srsclient.web.PubSearch;

/**
 * @author lwojtowicz
 *
 */
public class SearchRetrievalContext implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected PubSearch mUserPubContext = new PubSearch();
    protected ArtSearch mUserArtContext = new ArtSearch();

    public SearchRetrievalContext(){}

    public PubSearch getPubContext() 
    {
        return mUserPubContext;
    }
    
    public void setPubContext( PubSearch context )
    {
    	mUserPubContext = context;
    }

    public ArtSearch getArtContext() 
    {
        return mUserArtContext;
    }
    
    public void setArtContext( ArtSearch context )
    {
    	mUserArtContext = context;
    }
}
