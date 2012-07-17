package com.copyright.srsclient.web;

import java.io.Serializable;

import com.copyright.srsclient.internal.SearchRetrievalClient;

public class ArtSearch extends BaseSearch implements Serializable
{
	private static final long serialVersionUID = 1L;

    private String mTitle;
    private String mAuthor;
    private String mArtIdno;
    private String mDate;
    private String mHostIdno;
    private String mItemVolume;
    private String mItemIssue;
    private String mItemStartPage;
    private boolean mCccWr;
    private boolean mNature;
    private boolean mNyt;
    private boolean mPubget;
    
	public void performSearch()
	{
		SearchRetrievalClient src = new SearchRetrievalClient();
		mResultList = src.getArticles(this);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String author) {
		this.mAuthor = author;
	}

	public String getArtIdno() {
		return mArtIdno;
	}

	public void setArtIdno(String artIdno) {
		this.mArtIdno = artIdno;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String date) {
		this.mDate = date;
	}

	public String getHostIdno() {
		return mHostIdno;
	}

	public void setHostIdno(String hostIdno) {
		mHostIdno = hostIdno;
	}

	public boolean isCccWr() {
		return mCccWr;
	}

	public void setCccWr(boolean cccWr) {
		mCccWr = cccWr;
	}

	public boolean isNature() {
		return mNature;
	}

	public void setNature(boolean nature) {
		mNature = nature;
	}

	public boolean isNyt() {
		return mNyt;
	}

	public void setNyt(boolean nyt) {
		mNyt = nyt;
	}

	public boolean isPubget() {
		return mPubget;
	}

	public void setPubget(boolean pubget) {
		mPubget = pubget;
	}

	public String getItemVolume() {
		return mItemVolume;
	}

	public void setItemVolume(String itemVolume) {
		mItemVolume = itemVolume;
	}

	public String getItemIssue() {
		return mItemIssue;
	}

	public void setItemIssue(String itemIssue) {
		mItemIssue = itemIssue;
	}

	public String getItemStartPage() {
		return mItemStartPage;
	}

	public void setItemStartPage(String itemStartPage) {
		mItemStartPage = itemStartPage;
	}

}
