package com.copyright.srsclient.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.copyright.srsclient.web.PubSearch;
import com.opensymphony.xwork2.ActionSupport;

public class PubDetailAction extends ActionSupport
{
	private static final long serialVersionUID = 1;
	
	protected HttpSession getHttpSession()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession();
	}
	
	private Integer mIndex;

	private PubSearch mPublicationSearch = new PubSearch();
	
	@Override
	public String execute() throws Exception
	{
		if (mIndex == null)
		{
			throw new Exception("index is null!");
		}
		
		mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
		
		if(mIndex > mPublicationSearch.getEndRec())
		{
			mIndex -= 1;
		}

		if(mIndex < mPublicationSearch.getStartRec())
		{
			mIndex += 1;
		}
		
		return "SUCCESS";
	}
	
	public String returnToPubRL()
	{
		try
		{
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
		}
		catch(Exception e)
		{
			// handle ...
		}

		return "SUCCESS";
	}
	

	public Integer getIndex() {
		return mIndex;
	}

	public void setIndex(Integer index) {
		mIndex = index;
	}

	public PubSearch getPublicationSearch() {
		return mPublicationSearch;
	}

	public void setPublicationSearch(PubSearch publicationSearch) {
		mPublicationSearch = publicationSearch;
	}

}
