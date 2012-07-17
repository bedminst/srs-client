package com.copyright.srsclient.web;

public abstract class BaseSearch {

    protected String mWrWrkInst;
    protected String mSortOrder;
    protected int mResultsPerPage;
    protected int mPageNum = 1;
    
    protected ResultList mResultList;

	public ResultList getResultList() {
		return mResultList;
	}

	public void setResultList(ResultList resultList) {
		this.mResultList = resultList;
	}

	public String getWrWrkInst() {
		return mWrWrkInst;
	}

	public void setWrWrkInst(String wrWrkInst) {
		mWrWrkInst = wrWrkInst;
	}

	public String getSortOrder() {
		return mSortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.mSortOrder = sortOrder;
	}

	public int getResultsPerPage() {
		return mResultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.mResultsPerPage = resultsPerPage;
	}

	public int getPageNum() {
		return mPageNum;
	}

	public void setPageNum(int pageNum) {
		this.mPageNum = pageNum;
	}

	public int getStartRec()
	{
		return ((mPageNum - 1) * mResultsPerPage) + 1; 
	}
	
	public int getEndRec()
	{
		int endRec = mPageNum * mResultsPerPage;
		if(mResultList.isLastPage())
		{
			endRec -= mResultsPerPage;
			endRec += mResultList.getWorks().size();
		}
		return endRec; 
	}
}
