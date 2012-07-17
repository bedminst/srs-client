package com.copyright.srsclient.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.copyright.srsclient.web.PubSearch;
import com.opensymphony.xwork2.ActionSupport;

public class PubSearchLaunchAction extends ActionSupport
{
	private static final long serialVersionUID = 1;
    private Logger mLog = Logger.getLogger(getClass());
	
	protected HttpSession getHttpSession()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession();
	}
	
	private PubSearch mPublicationSearch = new PubSearch();

	private String mTitle;

	@Override
	public String execute() throws Exception
	{
		mLog.info("title: " + mTitle);
		if (mTitle == null)
		{
			throw new Exception("title is null!");
		}

		PubSearch preloadedLimiters = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();

		if(preloadedLimiters != null)
		{
			mPublicationSearch.setSortOrder(preloadedLimiters.getSortOrder());
			mPublicationSearch.setAuthorEditor("");
			mPublicationSearch.setPublisherRightsholder("");
			mPublicationSearch.setSeriesName("");
			mPublicationSearch.setPublicationTypeList(null);
			mPublicationSearch.setCountryList(null);
			mPublicationSearch.setLanguageList(null);
			// keep these values...
			mPublicationSearch.setPageNum(preloadedLimiters.getPageNum());
			mPublicationSearch.setResultsPerPage(preloadedLimiters.getResultsPerPage());
		}

		mPublicationSearch.setHasRights("Y");
		mPublicationSearch.setTitleStdNo(mTitle);
		mPublicationSearch.performSearch();
		
		((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setPubContext(mPublicationSearch);

		getHttpSession().setAttribute("srContext", ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")));

		if(mPublicationSearch.getResultList().getResultCount() == 0)
		{
			return "NO_RESULTS";
		}
		else
		{
			return "SUCCESS";
		}
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public PubSearch getPublicationSearch() {
		return mPublicationSearch;
	}

	public void setPublicationSearch(PubSearch publicationSearch) {
		mPublicationSearch = publicationSearch;
	}
	

}
