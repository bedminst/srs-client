package com.copyright.srsclient.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.copyright.srsclient.web.PubSearch;
import com.copyright.svc.searchRetrieval.api.data.PublicationSearch;
import com.opensymphony.xwork2.ActionSupport;

public class PubSearchAction extends ActionSupport
{
	private static final long serialVersionUID = 1;
    private Logger mLog = Logger.getLogger(getClass());

	protected HttpSession getHttpSession()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession();
	}
	
	private PubSearch mPublicationSearch = new PubSearch();
	
	@Override
	public String execute() throws Exception
	{
		PubSearch preloadedLimiters = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();

		if(preloadedLimiters != null)
		{
			mPublicationSearch.setPublicationTypeList(preloadedLimiters.getPublicationTypeList());
			mPublicationSearch.setCountryList(preloadedLimiters.getCountryList());
			mPublicationSearch.setLanguageList(preloadedLimiters.getLanguageList());
		}

		mPublicationSearch.performSearch();
		
		((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);

		getHttpSession().setAttribute("srContext", ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")));

		if(mPublicationSearch.getResultList().getResultCount() == 0)
		{
			// remove secondary stopwords and other fields
			mPublicationSearch.tryWithoutSecondaryStopwords();
		}

		if(mPublicationSearch.getResultList().getResultCount() == 0)
		{
			mPublicationSearch.buildSuggestions();
			return "NO_RESULTS";
		}
		else
		{
			return "SUCCESS";
		}
	}
	
/*
	fuzzy autosuggest second

	@Override
	public String execute() throws Exception
	{
		PubSearch preloadedLimiters = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();

		if(preloadedLimiters != null)
		{
			mPublicationSearch.setPublicationTypeList(preloadedLimiters.getPublicationTypeList());
			mPublicationSearch.setCountryList(preloadedLimiters.getCountryList());
			mPublicationSearch.setLanguageList(preloadedLimiters.getLanguageList());
		}

		mPublicationSearch.performSearch();
		
		((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);

		getHttpSession().setAttribute("srContext", ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")));

		if(mPublicationSearch.getResultList().getResultCount() == 0)
		{
			mPublicationSearch.buildSuggestions();
		}

		if(mPublicationSearch.getSuggestions() != null && mPublicationSearch.getSuggestions().size() == 0)
		{
			// remove secondary stopwords and other fields
			mPublicationSearch.tryWithoutSecondaryStopwords();
		}
		
		if(mPublicationSearch.getResultList().getResultCount() == 0)
		{
			return "NO_RESULTS";
		}
		else
		{
			return "SUCCESS";
		}
	}
 */
	
	public PubSearch getPublicationSearch() {
		return mPublicationSearch;
	}

	public void setPublicationSearch(PubSearch publicationSearch) {
		mPublicationSearch = publicationSearch;
	}

	// actions...
	public String fillSelectLists()
	{
		mPublicationSearch.setupLimiters();
		return "SUCCESS";
	}
	
	public String gotoNextPage()
	{
		try {
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
	
			if(mPublicationSearch != null && mPublicationSearch.getPageNum() < getLastPageNum())
			{
				mPublicationSearch.setPageNum(mPublicationSearch.getPageNum() + 1);
				mPublicationSearch.performSearch();
				
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("gotoNextPage(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}
	
	public String gotoPrevPage()
	{
		try {
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
		
			if(mPublicationSearch != null)
			{	
				int pageNum = mPublicationSearch.getPageNum();
				if( pageNum > 1)
				{
					pageNum -= 1;
				
					mPublicationSearch.setPageNum(pageNum);
					mPublicationSearch.performSearch();
					
					((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
				}
			}
		}
		catch(Exception e)
		{
			mLog.error("gotoPrevPage(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}
	
	public String gotoFirstPage()
	{
		try {
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
		
			if(mPublicationSearch != null)
			{	
				mPublicationSearch.setPageNum(1);
				mPublicationSearch.performSearch();
				
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("gotoFirstPage(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}	
	public String gotoLastPage()
	{
		try {
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
		
			if(mPublicationSearch != null)
			{	
				mPublicationSearch.setPageNum(getLastPageNum());
				mPublicationSearch.performSearch();
				
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("gotoLastPage(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}

	// Sort ...
	public String sortByRelevance()
	{
		try
		{
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
	
			if(mPublicationSearch != null)
			{	
				mPublicationSearch.setSortOrder(PublicationSearch.SORT_RELEVANCE);
				mPublicationSearch.setPageNum(1);
				
				mPublicationSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("sortByRelevance(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}
	public String sortByTitle()
	{
		try
		{
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
	
			if(mPublicationSearch != null)
			{	
				mPublicationSearch.setSortOrder(PublicationSearch.SORT_TITLE + PublicationSearch.ORDER.asc);
				mPublicationSearch.setPageNum(1);
				
				mPublicationSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("sortByTitle(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}
	public String sortByPublisher()
	{
		try
		{
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
	
			if(mPublicationSearch != null)
			{	
				mPublicationSearch.setSortOrder(PublicationSearch.SORT_PUBLISHER + PublicationSearch.ORDER.asc);
				mPublicationSearch.setPageNum(1);
				
				mPublicationSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("sortByPublisher(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}	
	
	public String sortByDate()
	{
		try
		{
			mPublicationSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
	
			if(mPublicationSearch != null)
			{
				mPublicationSearch.setSortOrder(PublicationSearch.SORT_DATE + PublicationSearch.ORDER.desc);
				mPublicationSearch.setPageNum(1);
				
				mPublicationSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("sortByDate(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}	

	// helper functions...
	
	private int getLastPageNum()
	{
		int lastPageNum = mPublicationSearch.getResultList().getResultCount()/mPublicationSearch.getResultList().getPageSize();
		int remainder = mPublicationSearch.getResultList().getResultCount()%mPublicationSearch.getResultList().getPageSize();
		if(remainder > 0)
		{
			lastPageNum += 1;
		}
		return lastPageNum;
	}

}
