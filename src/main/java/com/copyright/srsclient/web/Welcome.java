package com.copyright.srsclient.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.copyright.srsclient.api.SearchRetrievalClientConfiguration;
import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.opensymphony.xwork2.ActionSupport;

public class Welcome extends ActionSupport
{
	private static final long serialVersionUID = 1;
	
	protected SearchRetrievalContext mSrContext = new SearchRetrievalContext();

	public String publicationSearch() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		PubSearch pubSearch = mSrContext.getPubContext();
		pubSearch.setupLimiters();
		mSrContext.setPubContext(pubSearch);

		session.setAttribute("srContext", mSrContext);

		return SUCCESS;
	}
	
	public String articleSearch() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		session.setAttribute("srContext", mSrContext);

		return SUCCESS;
	}

	public SearchRetrievalContext getSrContext()
	{
		return mSrContext;
	}

	public void setSrContext(SearchRetrievalContext srContext)
	{
		mSrContext = srContext;
	}

	public String getAutosuggestEndpoint()
	{
		return SearchRetrievalClientConfiguration.getInstance().getString( "autosuggest_svc_endpoint" );
	}

	public String getAutosuggestItems()
	{
		return SearchRetrievalClientConfiguration.getInstance().getString( "autosuggest_items" );
	}
	
	public String getAutosuggestCount()
	{
		return SearchRetrievalClientConfiguration.getInstance().getString( "autosuggest_count" );
	}
	
}
