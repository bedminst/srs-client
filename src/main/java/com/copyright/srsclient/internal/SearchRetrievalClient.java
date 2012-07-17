/*
 * SearchRetrievalClient.java
 * Copyright (c) 2010, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2010-02-04   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.srsclient.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.copyright.domain.data.WorkExternal;
import com.copyright.domain.data.WorkTagExternal;
import com.copyright.srsclient.web.ArtSearch;
import com.copyright.srsclient.web.PubSearch;
import com.copyright.srsclient.web.ResultList;
import com.copyright.srsclient.web.Suggestion;
import com.copyright.svc.searchRetrieval.api.SearchRetrievalService;
import com.copyright.svc.searchRetrieval.api.data.ArticleSearch;
import com.copyright.svc.searchRetrieval.api.data.PublicationSearch;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalConsumerContext;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalResult;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;


/**
 * @author lwojtowicz
 *
 */
public class SearchRetrievalClient 
{
    private Logger mLog = Logger.getLogger(getClass());
	private SearchRetrievalService mSearchRetrievalService;
	
	/*
	 *  When making a request to autosuggest service, bring "mNumberOfItems" results back
	 */
	private int mNumberOfItems;
	/*
	 *  When making a request to autosuggest service, consider only records 
	 *  which have a count greater than "mCount"
	 */
	private int mCount;
	
	private static ClassPathXmlApplicationContext mSearchContext = new ClassPathXmlApplicationContext("classpath:com/copyright/srsclient/internal/SearchRetrievalClientContext.xml");
	
	/**
	 * @param args
	 */
	public ResultList getPublications(PubSearch ps)
	{
		// Instantiate the search client bean and retrieve client bean
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();
        // these are the inputs to our service call
        PublicationSearch pubSearch = new PublicationSearch();
        pubSearch.setWrWrkInst(ps.getWrWrkInst());
        pubSearch.setTfWrkInst(ps.getTfWrkInst());
        pubSearch.setPubTitleOrStdNumber(ps.getTitleStdNo());
        pubSearch.setAuthorOrEditor(ps.getAuthorEditor());
        pubSearch.setPublisherOrRightsholder(ps.getPublisherRightsholder());
        pubSearch.setSeriesName(ps.getSeriesName());
        pubSearch.setHasRights(ps.getHasRights());

        /*
         *  Publication type, country and language are multiple selection fields.
         *  They need to be passed onto svc-searchRetrieval as ORed lists.
         */
        pubSearch.setPubType(buildORedList(ps.getSelectedPublicationTypeList()));
        pubSearch.setCountry(buildORedList(ps.getSelectedCountryList()));
        pubSearch.setLanguage(buildORedList(ps.getSelectedLanguageList()));
        
        List<String> sortList = new ArrayList<String>();
       	sortList.add(ps.getSortOrder());
        int page = ps.getPageNum();
        int pageSize = ps.getResultsPerPage();

        ResultList resultList = new ResultList();
        resultList.setDisplayQuery(buildDisplayQuery(pubSearch));

        // invoke the service
        mLog.info("Searching for '" + pubSearch.getPubTitleOrStdNumber() + "', sorting by " + sortList.toString() + "...");
        SearchRetrievalResult result = remoteSrsClient.getSearchRetrievalService().searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);

        // Process the result set
        mLog.info("Result count: " + result.getResultCount());
        mLog.info("Last Page: " + result.getLastPage());
        mLog.info("Number of records returned: " + result.getWorks().size());
        
		List<WorkExternal> works = result.getWorks();

		resultList.setDisplayModifiedQuery("");
		// set displayModifiedQuery if it is different from the original query
		if(!resultList.getDisplayQuery().equalsIgnoreCase(buildDisplayQuery(pubSearch)))
		{
			resultList.setDisplayModifiedQuery(buildDisplayQuery(pubSearch));
		}
		
		resultList.setResultCount(result.getResultCount());
		resultList.setPage(page);
		resultList.setPageSize(pageSize);
		resultList.setWorksOnPage(result.getWorks().size());
		resultList.setLastPage(result.getLastPage());

		resultList.setWorks(works);
		
		return resultList;
	}
		
	public ResultList tryWithoutSecondaryStopwords(PubSearch ps)
	{
		// Instantiate the search client bean and retrieve client bean
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();
        // these are the inputs to our service call
        PublicationSearch pubSearch = new PublicationSearch();
        pubSearch.setWrWrkInst(ps.getWrWrkInst());
        pubSearch.setTfWrkInst(ps.getTfWrkInst());
        pubSearch.setPubTitleOrStdNumber(ps.getTitleStdNo());
        pubSearch.setAuthorOrEditor(ps.getAuthorEditor());
        pubSearch.setPublisherOrRightsholder(ps.getPublisherRightsholder());
        pubSearch.setSeriesName(ps.getSeriesName());
        pubSearch.setHasRights(ps.getHasRights());

        /*
         *  Publication type, country and language are multiple selection fields.
         *  They need to be passed onto svc-searchRetrieval as ORed lists.
         */
        pubSearch.setPubType(buildORedList(ps.getSelectedPublicationTypeList()));
        pubSearch.setCountry(buildORedList(ps.getSelectedCountryList()));
        pubSearch.setLanguage(buildORedList(ps.getSelectedLanguageList()));
        
        List<String> sortList = new ArrayList<String>();
       	sortList.add(ps.getSortOrder());
        int page = ps.getPageNum();
        int pageSize = ps.getResultsPerPage();

        ResultList resultList = new ResultList();
        resultList.setDisplayQuery(buildDisplayQuery(pubSearch));

    	/*
    	 *  try to remove secondary stopwords
    	 */
    	String title = pubSearch.getPubTitleOrStdNumber();
    	String titleAfterChange = applySecondaryStopwordList(title);
    	if(titleAfterChange == null || titleAfterChange.isEmpty())
    	{
    		return resultList;
    	}
		pubSearch.setPubTitleOrStdNumber(titleAfterChange);
        mLog.info("Trying with '" + pubSearch.getPubTitleOrStdNumber() + "' plus other fields (if selected), sorting by " + sortList.toString() + "...");
        SearchRetrievalResult result = remoteSrsClient.getSearchRetrievalService().searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);
        
        if(result.getResultCount() == 0)
        {
        	/*
        	 *  try even harder... omit fields other than title
        	 */
        	if( (pubSearch.getAuthorOrEditor() != null && !pubSearch.getAuthorOrEditor().isEmpty()) ||
       			(pubSearch.getPublisherOrRightsholder() != null && !pubSearch.getPublisherOrRightsholder().isEmpty()) ||
				(pubSearch.getSeriesName() != null && !pubSearch.getSeriesName().isEmpty()) ||
				(pubSearch.getPubType() != null && !pubSearch.getPubType().isEmpty()) ||
				(pubSearch.getCountry() != null && !pubSearch.getCountry().isEmpty()) ||
				(pubSearch.getLanguage() != null && !pubSearch.getLanguage().isEmpty()) )
        	{
	            pubSearch.setAuthorOrEditor("");
	            pubSearch.setPublisherOrRightsholder("");
	            pubSearch.setSeriesName("");
	            pubSearch.setPubType("");
	            pubSearch.setCountry("");
	            pubSearch.setLanguage("");
	            
	            mLog.info("Trying with '" + pubSearch.getPubTitleOrStdNumber() + "' only, sorting by " + sortList.toString() + "...");
	    		result = remoteSrsClient.getSearchRetrievalService().searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);
        	}
        }

        // Process the result set
        mLog.info("Result count: " + result.getResultCount());
        mLog.info("Last Page: " + result.getLastPage());
        mLog.info("Number of records returned: " + result.getWorks().size());
        
		List<WorkExternal> works = result.getWorks();

		resultList.setDisplayModifiedQuery("");
		// set displayModifiedQuery if it is different from the original query
		if(!resultList.getDisplayQuery().equalsIgnoreCase(buildDisplayQuery(pubSearch)))
		{
			resultList.setDisplayModifiedQuery(buildDisplayQuery(pubSearch));
		}
		
		resultList.setResultCount(result.getResultCount());
		resultList.setPage(page);
		resultList.setPageSize(pageSize);
		resultList.setWorksOnPage(result.getWorks().size());
		resultList.setLastPage(result.getLastPage());

		resultList.setWorks(works);
		
		return resultList;
	}
		
	public List<Suggestion> buildSuggestions(PubSearch ps)
	{
		/*
		 *  At this point we know that the search brought no results, even with secondary stopwords
		 *  and removing other query elements (author, publisher, etc.) if they were present.
		 *  It's time to present to the end user fuzzy titles similar to what they came up with.
		 *  It will be created with use of autosuggest index, interfaced thru SRS.
		 */
		String originalTitle = ps.getTitleStdNo();
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		mLog.info("About to call autosuggest service for: " + originalTitle + "; number of items: " + remoteSrsClient.getNumberOfItems() + "; count: " + remoteSrsClient.getCount());
		List<String> titles = remoteSrsClient.getSearchRetrievalService().fuzzyTitlesForQuery(consumerCtx, originalTitle, remoteSrsClient.getNumberOfItems(), remoteSrsClient.getCount());

		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		if(titles != null)
		{
			for(String title : titles)
			{
				suggestions.add(new Suggestion(title));
				mLog.info("Suggested title: " + title);
			}
		}
		
		return suggestions;
	}
	
	public ResultList getArticles(ArtSearch artSearch)
	{
		// Instantiate the search client bean and retrieve client bean
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();
        // these are the inputs to our service call
        ArticleSearch as = new ArticleSearch();
        as.setWrWrkInst(artSearch.getWrWrkInst());
        as.setArtTitle(artSearch.getTitle());
        as.setAuthor(artSearch.getAuthor());
        as.setArtIdno(artSearch.getArtIdno());
        as.setDate(artSearch.getDate());
        as.setItemVolume(artSearch.getItemVolume());
        as.setItemIssue(artSearch.getItemIssue());
        as.setItemStartPage(artSearch.getItemStartPage());
        
        // prepare sources...
        List<WorkTagExternal> sources = new ArrayList<WorkTagExternal>();
        WorkTagExternal wt1 = new WorkTagExternal();
        WorkTagExternal wt2 = new WorkTagExternal();
        WorkTagExternal wt3 = new WorkTagExternal();
        WorkTagExternal wt4 = new WorkTagExternal();
        wt1.setTagName("NYT");
        wt2.setTagName("CCCWR");
        wt3.setTagName("NATURE");
        wt4.setTagName("PUBGET");

        // select sources
        if(artSearch.isCccWr())
        {
            sources.add(wt2);
        }
        if(artSearch.isNature())
        {
            sources.add(wt3);
        }
        if(artSearch.isNyt())
        {
            sources.add(wt1);
        }
        if(artSearch.isPubget())
        {
            sources.add(wt4);
        }

        /*
         * this is a place to build a fake publication record based on elements from screen
         */
        WorkExternal publication = new WorkExternal();
        publication.setIdnoWop(convertToPipedList(artSearch.getHostIdno()));
        publication.setSrsTagList(sources);

        List<String> sortList = new ArrayList<String>();
       	sortList.add(artSearch.getSortOrder());
        int page = artSearch.getPageNum();
        int pageSize = artSearch.getResultsPerPage();

        // invoke the service
        mLog.info("Searching for '" + as.toString() + "', sorting by " + sortList.toString() + "...");
        SearchRetrievalResult result = remoteSrsClient.getSearchRetrievalService().searchArticle(consumerCtx, as, publication, sortList, page, pageSize);

        // Process the result set
        mLog.info("Result count: " + result.getResultCount());
        mLog.info("Last Page: " + result.getLastPage());
        mLog.info("Number of records returned: " + result.getWorks().size());
        
		List<WorkExternal> works = result.getWorks().get(0).getSubWorks();
		
		ResultList resultList = new ResultList();
        resultList.setDisplayQuery(buildDisplayQuery(as));
		resultList.setResultCount(result.getResultCount());
		resultList.setPage(page);
		resultList.setPageSize(pageSize);
		resultList.setWorksOnPage(result.getWorks().size());
		resultList.setLastPage(result.getLastPage());

		resultList.setWorks(works);
		
		return resultList;
	}
		
	public List<SrsLimiter> getPublicationTypes()
	{
		// Instantiate the search client bean and retrieve client bean
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();

		return remoteSrsClient.getSearchRetrievalService().itemsForField(consumerCtx, "pub_type_facet");
	}
	
	public List<SrsLimiter> getCountries()
	{
		// Instantiate the search client bean and retrieve client bean
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();

		return remoteSrsClient.getSearchRetrievalService().itemsForField(consumerCtx, "country_facet");
	}
	
	public List<SrsLimiter> getLanguages()
	{
		// Instantiate the search client bean and retrieve client bean
		SearchRetrievalClient remoteSrsClient = (SearchRetrievalClient) mSearchContext.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();

		return remoteSrsClient.getSearchRetrievalService().itemsForField(consumerCtx, "lang_facet");
	}
	
	/**
	 * @return the SearchRetrievalService
	 */
	public SearchRetrievalService getSearchRetrievalService() {
		return mSearchRetrievalService;
	}

	/**
	 * @param SearchRetrievalService the SearchRetrievalService to set
	 */
	public void setSearchRetrievalService(SearchRetrievalService searchRetrievalService) {
		this.mSearchRetrievalService = searchRetrievalService;
	}

	/*
	 *  helper methods...
	 */
	private String buildORedList(String itemList)
	{
		String orList = "";
		
        mLog.info("buildORedList: " + itemList);

        if(itemList == null || itemList.isEmpty())
		{
			return orList;
		}
		
		String[] items = itemList.split(",");
		
		if(items.length == 0)
		{
			return orList;
		}
		
		for(String item : items)
		{
			orList += "\"" + item.trim() + "\"" + " OR ";			
		}
		// truncate the last " OR " item separator
		orList = orList.substring(0, orList.length() - 4);
		
		return orList;
	}
	
	private String convertToPipedList(String oredList)
	{
		String pipedList = "";
		
		String[] items = oredList.split("OR");
		
		if(items == null || items.length == 0)
		{
			return pipedList;
		}
		
		for(String item : items)
		{
			if(pipedList.length() > 0)
			{
				pipedList += "|";
			}
			pipedList += item.trim();			
		}

		return pipedList;
	}
	
	private String applySecondaryStopwordList(String title)
	{
		if(title == null || title.isEmpty())
		{
			return title;
		}

		String titleAfterChange = "";
		
		String[] tokens = title.trim().split(" ");
		boolean found = false;
		if(tokens == null || tokens.length == 0)
		{
			return title;
		}
		for(String token : tokens)
		{
	        mLog.debug("Analyzing token: " + token);
	        found = false;
			for(String stopword : sSecondaryStopwords)
			{
				if(token.equalsIgnoreCase(stopword))
				{
					found = true;
			        mLog.debug("Removed! " + token);
					break;
				}
			}
			
			if(!found)
			{
		        mLog.debug("Added: " + token);
				titleAfterChange += token + " ";
			}
		}
		
        mLog.info("TitleAfterChange: " + titleAfterChange.trim());
		return titleAfterChange.trim();
	}
	
	private String buildDisplayQuery(PublicationSearch pubSearch)
	{
		String displayQuery = "";
		
		if(pubSearch.getWrWrkInst() != null && !pubSearch.getWrWrkInst().isEmpty())
		{
			displayQuery += "wr_wrk_inst: " + pubSearch.getWrWrkInst() + "; ";
		}
		if(pubSearch.getTfWrkInst() != null && !pubSearch.getTfWrkInst().isEmpty())
		{
			displayQuery += "tf_wrk_inst: " + pubSearch.getTfWrkInst() + "; ";
		}
		if(pubSearch.getPubTitleOrStdNumber() != null && !pubSearch.getPubTitleOrStdNumber().isEmpty())
		{
			displayQuery += "title or idno: " + pubSearch.getPubTitleOrStdNumber() + "; ";
		}
		if(pubSearch.getAuthorOrEditor() != null && !pubSearch.getAuthorOrEditor().isEmpty())
		{
			displayQuery += "author or editor: " + pubSearch.getAuthorOrEditor() + "; ";
		}
		if(pubSearch.getPublisherOrRightsholder() != null && !pubSearch.getPublisherOrRightsholder().isEmpty())
		{
			displayQuery += "publisher or rightsholder: " + pubSearch.getPublisherOrRightsholder() + "; ";
		}
		if(pubSearch.getSeriesName() != null && !pubSearch.getSeriesName().isEmpty())
		{
			displayQuery += "series name: " + pubSearch.getSeriesName() + "; ";
		}
		if(pubSearch.getPubType() != null && !pubSearch.getPubType().isEmpty())
		{
			displayQuery += "publication type: " + pubSearch.getPubType() + "; ";
		}
		if(pubSearch.getCountry() != null && !pubSearch.getCountry().isEmpty())
		{
			displayQuery += "country: " + pubSearch.getCountry() + "; ";
		}
		if(pubSearch.getLanguage() != null && !pubSearch.getLanguage().isEmpty())
		{
			displayQuery += "language: " + pubSearch.getLanguage() + "; ";
		}
		return displayQuery;
	}
	
	private String buildDisplayQuery(ArticleSearch artSearch)
	{
		String displayQuery = "";
		
		if(artSearch.getArtTitle() != null && !artSearch.getArtTitle().isEmpty())
		{
			displayQuery += "title: " + artSearch.getArtTitle() + "; ";
		}
		if(artSearch.getAuthor() != null && !artSearch.getAuthor().isEmpty())
		{
			displayQuery += "author: " + artSearch.getAuthor() + "; ";
		}
		if(artSearch.getArtIdno() != null && !artSearch.getArtIdno().isEmpty())
		{
			displayQuery += "idno: " + artSearch.getArtIdno() + "; ";
		}
		if(artSearch.getDate() != null && !artSearch.getDate().isEmpty())
		{
			displayQuery += "date: " + artSearch.getDate() + "; ";
		}
		if(artSearch.getItemVolume() != null && !artSearch.getItemVolume().isEmpty())
		{
			displayQuery += "volume: " + artSearch.getItemVolume() + "; ";
		}
		if(artSearch.getItemIssue() != null && !artSearch.getItemIssue().isEmpty())
		{
			displayQuery += "issue: " + artSearch.getItemIssue() + "; ";
		}
		if(artSearch.getItemStartPage() != null && !artSearch.getItemStartPage().isEmpty())
		{
			displayQuery += "start page: " + artSearch.getItemStartPage() + "; ";
		}
		return displayQuery;
	}
	
	private static String[] sSecondaryStopwords = {
		"news",
		"newswire",
		"newswires",
		"newspaper",
		"newsletter",
		"week",
		"weekly",
		"review",
		"worldwide",
		"today",
		"update",
		"magazine",
		"limited",
		"com",
		"org",
		"gov",
		"net",
		"edu",
		"biz",
		"br",
		"ca",
		"fr",
		"jp",
		"ru",
		"eu",
		"uk",
		"us",
		"de",
		"au",
		"cn",
		"mobi",
		"gb",
		"il",
		"journal",
		"service",
		"daily",
		"online",
		"press",
		"website",
		"monthly",
		"report",
		"publishing",
		"quarterly",
		"wire",
		"blog",
		"newsweekly",
		"issn",
		"isbn",
		"herald",
		"manual",
		"archives",
		"annual"
	};

	public int getNumberOfItems() {
		return mNumberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		mNumberOfItems = numberOfItems;
	}

	public int getCount() {
		return mCount;
	}

	public void setCount(int count) {
		mCount = count;
	}

}
