package com.copyright.srsclient.web;

import java.util.ArrayList;
import java.util.List;

import com.copyright.domain.data.WorkExternal;

public class ResultList
{
	protected List<WorkExternal> mWorks = new ArrayList<WorkExternal>(); // page of work objects
	protected int mResultCount = 0;		// the number of hits
	protected int mPage;			 	// the requested page
	protected int mPageSize;		 	// the requested page size
	protected int mWorksOnPage;			// number of works returned on this page
	protected boolean mLastPage;		// the last page given page size
	protected String mDisplayQuery;
	protected String mDisplayModifiedQuery;

	
	public List<WorkExternal> getWorks() {
		return mWorks;
	}
	public void setWorks(List<WorkExternal> works) {
		mWorks = works;
	}
	public int getResultCount() {
		return mResultCount;
	}
	public void setResultCount(int resultCount) {
		mResultCount = resultCount;
	}
	public int getPage() {
		return mPage;
	}
	public void setPage(int page) {
		mPage = page;
	}
	public int getPageSize() {
		return mPageSize;
	}
	public void setPageSize(int pageSize) {
		mPageSize = pageSize;
	}
	public boolean isLastPage() {
		return mLastPage;
	}
	public void setLastPage(boolean lastPage) {
		mLastPage = lastPage;
	}
	public int getWorksOnPage() {
		return mWorksOnPage;
	}
	public void setWorksOnPage(int worksOnPage) {
		mWorksOnPage = worksOnPage;
	}
	public String getDisplayQuery() {
		return mDisplayQuery;
	}
	public void setDisplayQuery(String displayQuery) {
		mDisplayQuery = displayQuery;
	}
	public String getDisplayModifiedQuery() {
		return mDisplayModifiedQuery;
	}
	public void setDisplayModifiedQuery(String displayModifiedQuery) {
		mDisplayModifiedQuery = displayModifiedQuery;
	}

}
