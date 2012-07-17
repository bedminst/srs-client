package com.copyright.srsclient.web.actions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.copyright.domain.data.WorkExternal;
import com.copyright.domain.data.WorkTagExternal;
import com.copyright.srsclient.internal.SearchRetrievalContext;
import com.copyright.srsclient.web.ArtSearch;
import com.copyright.srsclient.web.PubSearch;
import com.opensymphony.xwork2.ActionSupport;

public class ArtSearchLaunchAction extends ActionSupport
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

		PubSearch ps = ((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).getPubContext();
		int pageSize = ps.getResultList().getPageSize();
		int page = ps.getResultList().getPage();
		WorkExternal publication = ps.getResultList().getWorks().get(mIndex - ((page - 1) * pageSize) - 1);
		
		mArticleSearch.setHostIdno(hostIdnosOrGroup(publication));
		
		List<WorkTagExternal> srsTags = publication.getSrsTagList();
		for(WorkTagExternal tag : srsTags)
		{
			if(tag.getTagName().equals("CCCWR"))
			{
				mArticleSearch.setCccWr(true);
			}
			if(tag.getTagName().equals("NATURE"))
			{
				mArticleSearch.setNature(true);
			}
			if(tag.getTagName().equals("NYT"))
			{
				mArticleSearch.setNyt(true);
			}
		}
		
		((SearchRetrievalContext)getHttpSession().getAttribute("srContext")).setArtContext(mArticleSearch);

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
	
	/*
	 * Helper method
	 */
	private String hostIdnosOrGroup(WorkExternal publication)
	{
		Set<String> uniqueHostIdnos = new HashSet<String>();

		if(publication.getIdnoWop() != null)
		{
			String[] list1 = publication.getIdnoWop().split("\\|");
			for(String hostIdno : list1)
			{
				uniqueHostIdnos.add(hostIdno);
			}
		}
		if(publication.getOtherFormatIdnos() != null)
		{
			String[] list2 = publication.getOtherFormatIdnos().split("\\|");
			for(String hostIdno : list2)
			{
				uniqueHostIdnos.add(hostIdno);
			}
		}
		
		if(uniqueHostIdnos.size() == 0)
		{
			return "";
		}
		
		String orListOfHostIdnos = "";
		for(String hostIdno : uniqueHostIdnos)
		{
			if(orListOfHostIdnos.length() > 0)
			{
				orListOfHostIdnos += " OR ";
			}
			orListOfHostIdnos += hostIdno;
		}
		
		// finally, pass this for searching
		return orListOfHostIdnos;
	}

}
