package com.copyright.srsclient.web;

import java.io.Serializable;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

//import org.apache.log4j.Logger;

import com.copyright.srsclient.internal.SearchRetrievalClient;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;

public class PubSearch extends BaseSearch implements Serializable
{
//    private Logger mLog = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	
	private String mTitleStdNo;
    private String mAuthorEditor;
    private String mPublisherRightsholder;
    private String mSeriesName;
    private String mHasRights;
    private String mTfWrkInst;

    // hidden fields for autosuggest
	private String mEndpoint;
	private String mCount;
	private String mItems;
	
	private List<Suggestion> mSuggestions;

    private SortedMap<String,String> mPublicationTypeList = new TreeMap<String,String>();
    private String mSelectedPublicationTypeList;

    private SortedMap<String,String> mCountryList = new TreeMap<String,String>();
    private String mSelectedCountryList;
    
    private SortedMap<String,String> mLanguageList = new TreeMap<String,String>();
    private String mSelectedLanguageList;
    
	public void performSearch()
	{
		SearchRetrievalClient src = new SearchRetrievalClient();
		mResultList = src.getPublications(this);
	}

	public void buildSuggestions()
	{
		SearchRetrievalClient src = new SearchRetrievalClient();
		mSuggestions = src.buildSuggestions(this);
	}

	public void tryWithoutSecondaryStopwords()
	{
		SearchRetrievalClient src = new SearchRetrievalClient();
		mResultList = src.tryWithoutSecondaryStopwords(this);
	}

	public void setupLimiters()
	{
		SearchRetrievalClient src = new SearchRetrievalClient();
		
		List<SrsLimiter> pubTypes = src.getPublicationTypes();
		for(SrsLimiter pubType : pubTypes)
		{
			String pubTypeName = pubType.getItem();
			mPublicationTypeList.put(pubTypeName.toLowerCase(), pubTypeName); 
		}

		List<SrsLimiter> countries = src.getCountries();
		for(SrsLimiter country : countries)
		{
			String countryName = country.getItem();
			mCountryList.put(countryName.toLowerCase(), countryName); 
		}
		
		List<SrsLimiter> languages = src.getLanguages();
		for(SrsLimiter language : languages)
		{
			String languageName = language.getItem();
			mLanguageList.put(languageName.toLowerCase(), languageName); 
		}
	}

    public String getTitleStdNo() {
		return mTitleStdNo;
	}
	public void setTitleStdNo(String titleStdNo) {
		this.mTitleStdNo = titleStdNo;
	}
	public String getAuthorEditor() {
		return mAuthorEditor;
	}
	public void setAuthorEditor(String authorEditor) {
		this.mAuthorEditor = authorEditor;
	}
	public String getPublisherRightsholder() {
		return mPublisherRightsholder;
	}
	public void setPublisherRightsholder(String publisherRightsholder) {
		this.mPublisherRightsholder = publisherRightsholder;
	}
	public ResultList getResultList() {
		return mResultList;
	}

	public void setResultList(ResultList resultList) {
		this.mResultList = resultList;
	}

	public String getSeriesName() {
		return mSeriesName;
	}

	public void setSeriesName(String seriesName) {
		this.mSeriesName = seriesName;
	}

	public String getHasRights() {
		return mHasRights;
	}

	public void setHasRights(String hasRights) {
		this.mHasRights = hasRights;
	}

	/**
	 * @return the mTfWrkInst
	 */
	public String getTfWrkInst() {
		return mTfWrkInst;
	}

	/**
	 * @param mTfWrkInst the mTfWrkInst to set
	 */
	public void setTfWrkInst(String mTfWrkInst) {
		this.mTfWrkInst = mTfWrkInst;
	}

	public SortedMap<String, String> getPublicationTypeList() {
		return mPublicationTypeList;
	}

	public void setPublicationTypeList(SortedMap<String, String> publicationTypeList) {
		this.mPublicationTypeList = publicationTypeList;
	}

	public String getSelectedPublicationTypeList() {
		return mSelectedPublicationTypeList;
	}

	public void setSelectedPublicationTypeList(String selectedPublicationTypeList) {
		this.mSelectedPublicationTypeList = selectedPublicationTypeList;
	}

	public SortedMap<String, String> getCountryList() {
		return mCountryList;
	}

	public void setCountryList(SortedMap<String, String> countryList) {
		this.mCountryList = countryList;
	}

	public String getSelectedCountryList() {
		return mSelectedCountryList;
	}

	public void setSelectedCountryList(String selectedCountryList) {
		this.mSelectedCountryList = selectedCountryList;
	}

	public SortedMap<String, String> getLanguageList() {
		return mLanguageList;
	}

	public void setLanguageList(SortedMap<String, String> languageList) {
		this.mLanguageList = languageList;
	}

	public String getSelectedLanguageList() {
		return mSelectedLanguageList;
	}

	public void setSelectedLanguageList(String selectedLanguageList) {
		this.mSelectedLanguageList = selectedLanguageList;
	}

	public String getEndpoint() {
		return mEndpoint;
	}

	public void setEndpoint(String endpoint) {
		mEndpoint = endpoint;
	}

	public String getCount() {
		return mCount;
	}

	public void setCount(String count) {
		mCount = count;
	}

	public void setItems(String items) {
		mItems = items;
	}

	public String getItems() {
		return mItems;
	}

	public List<Suggestion> getSuggestions() {
		return mSuggestions;
	}

	public void setSuggestions(List<Suggestion> suggestions) {
		mSuggestions = suggestions;
	}

}
