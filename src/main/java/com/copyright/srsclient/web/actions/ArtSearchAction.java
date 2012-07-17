package com.copyright.srsclient.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.copyright.srsclient.web.ArtSearch;
import com.copyright.svc.searchRetrieval.api.data.ArticleSearch;
import com.opensymphony.xwork2.ActionSupport;

public class ArtSearchAction extends ActionSupport
{
	private static final long serialVersionUID = 1;
    private Logger mLog = Logger.getLogger(getClass());

	protected HttpSession getHttpSession()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession();
	}
	
	private ArtSearch mArticleSearch = new ArtSearch();
	
	@Override
	public String execute() throws Exception
	{
		mArticleSearch.performSearch();

		if(mArticleSearch.getResultList().getResultCount() == 0)
		{
			return "NO_RESULTS";
		}

		((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);

		return "SUCCESS";
	}
	
	public ArtSearch getArticleSearch() {
		return mArticleSearch;
	}

	public void setArticleSearch(ArtSearch articleSearch) {
		mArticleSearch = articleSearch;
	}

	// actions...
	public String gotoNextPage()
	{
		try {
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
	
			if(mArticleSearch != null && mArticleSearch.getPageNum() < getLastPageNum())
			{
				mArticleSearch.setPageNum(mArticleSearch.getPageNum() + 1);
				mArticleSearch.performSearch();
				
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
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
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
		
			if(mArticleSearch != null)
			{
				int pageNum = mArticleSearch.getPageNum();
				if( pageNum > 1)
				{
					pageNum -= 1;
				
					mArticleSearch.setPageNum(pageNum);
					mArticleSearch.performSearch();
					
					((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
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
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
		
			if(mArticleSearch != null)
			{
				mArticleSearch.setPageNum(1);
				mArticleSearch.performSearch();
				
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
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
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
		
			if(mArticleSearch != null)
			{
				mArticleSearch.setPageNum(getLastPageNum());
				mArticleSearch.performSearch();
				
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
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
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
	
			if(mArticleSearch != null)
			{
				mArticleSearch.setSortOrder(ArticleSearch.SORT_RELEVANCE);
				mArticleSearch.setPageNum(1);
				
				mArticleSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
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
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
	
			if(mArticleSearch != null)
			{
				mArticleSearch.setSortOrder(ArticleSearch.SORT_TITLE + ArticleSearch.ORDER.asc);
				mArticleSearch.setPageNum(1);
				
				mArticleSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
			}
		}
		catch(Exception e)
		{
			mLog.error("sortByTitle(): exception " + e.getMessage());
		}
		return "SUCCESS";
	}
	public String sortByDate()
	{
		try
		{
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
	
			if(mArticleSearch != null)
			{
				mArticleSearch.setSortOrder(ArticleSearch.SORT_DATE + ArticleSearch.ORDER.desc);
				mArticleSearch.setPageNum(1);
				
				mArticleSearch.performSearch();
		
				((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);
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
		if(mArticleSearch != null)
		{
			int lastPageNum = mArticleSearch.getResultList().getResultCount()/mArticleSearch.getResultList().getPageSize();
			int remainder = mArticleSearch.getResultList().getResultCount()%mArticleSearch.getResultList().getPageSize();
			if(remainder > 0)
			{
				lastPageNum += 1;
			}
			return lastPageNum;
		}
		else
		{
			return 0;
		}
	}

}
