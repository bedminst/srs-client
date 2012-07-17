package com.copyright.srsclient.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.copyright.srsclient.web.ArtSearch;
import com.opensymphony.xwork2.ActionSupport;

public class ArtDetailAction extends ActionSupport
{
	private static final long serialVersionUID = 1;
	
	protected HttpSession getHttpSession()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession();
	}
	
	private Integer mIndex;

	private ArtSearch mArticleSearch = new ArtSearch();
	
	@Override
	public String execute() throws Exception
	{
		if (mIndex == null)
		{
			throw new Exception("index is null!");
		}
		
		mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
		
		if(mIndex > mArticleSearch.getEndRec())
		{
			mIndex -= 1;
		}

		if(mIndex < mArticleSearch.getStartRec())
		{
			mIndex += 1;
		}
		
		return "SUCCESS";
	}
	
	public String returnToRL()
	{
		try
		{
			mArticleSearch = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getArtContext();
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

	public ArtSearch getArticleSearch() {
		return mArticleSearch;
	}

	public void setArticleSearch(ArtSearch articleSearch) {
		mArticleSearch = articleSearch;
	}

}
