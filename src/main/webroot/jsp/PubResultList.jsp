<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
	<title>Publication Result List</title>
	<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<s:if test="publicationSearch.resultList.displayModifiedQuery==''">
		<div style="float: left"><strong><s:property
			value="publicationSearch.resultList.resultCount" /></strong> results
		for <strong><i><s:property
			value="publicationSearch.resultList.displayQuery" /></i></strong>
		</div>
	</s:if>
	<s:else>
		<div style="float: left; color: red">No results
		for <strong><i><s:property
			value="publicationSearch.resultList.displayQuery" /></i></strong>
		</div>
		<div style="float: right; color: green"><strong><s:property
			value="publicationSearch.resultList.resultCount" /></strong> results
		for <strong><i><s:property
			value="publicationSearch.resultList.displayModifiedQuery" /></i></strong>
		</div>
	</s:else>

</div>
	
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">
	<div style="float: left">
	<a href="publicationSearch">Return to Publication Search</a>
	</div> 
	<div style="float: right">
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<s:form action="PubResultList">
	<div style="float: right;">
	    <s:hidden name="publicationSearch.hasRights"/>
	    <s:hidden name="publicationSearch.titleStdNo"/>
    	<s:hidden name="publicationSearch.authorEditor"/>
    	<s:hidden name="publicationSearch.publisherRightsholder"/>
    	<s:hidden name="publicationSearch.seriesName"/>
 		<s:hidden name="publicationSearch.selectedPublicationTypeList"/>
 		<s:hidden name="publicationSearch.selectedCountryList"/>
 		<s:hidden name="publicationSearch.selectedLanguageList"/>
	    <s:hidden name="publicationSearch.sortOrder"/>
	    <s:select cssStyle="width: 100px;" label="Results per Page" 
		    name="publicationSearch.resultsPerPage" 
		    headerKey="1"
		    list="#{'5':'5', '10':'10', '25':'25', '50':'50', '100':'100'}"
		    onchange="this.form.submit()"
   		/>
	
	</div>
	</s:form>

	<b>Sort by:&nbsp;</b>
	
	<a 
	<s:if test="publicationSearch.sortOrder=='relevance'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortPubByRelevance.action">Relevance</a>
	  
	&nbsp;|&nbsp;
	
	<a 
	<s:if test="publicationSearch.sortOrder=='title:asc'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortPubByTitle.action">Title</a>
	
	&nbsp;|&nbsp;
	
	<a 
	<s:if test="publicationSearch.sortOrder=='publisher:asc'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortPubByPublisher.action">Publisher</a>
	
	&nbsp;|&nbsp;
	
	<a 
	<s:if test="publicationSearch.sortOrder=='date:desc'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortPubByDate.action">Date</a>

	</div>
	
</div>

<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left;">
	
	    <div><b>Results</b> 
		    <s:property value="publicationSearch.startRec" /> - <s:property value="publicationSearch.endRec" /> of <s:property
				value="publicationSearch.resultList.resultCount" />
			&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<b>Page</b>&nbsp;&nbsp;&nbsp;&nbsp;
			<s:property
				value="publicationSearch.pageNum" />
			&nbsp;&nbsp;&nbsp;&nbsp;
		
			<a class="specialLink" href="gotoFirstPubPage.action">First</a>|
			<a class="specialLink" href="gotoPrevPubPage.action">Prev</a>
	&nbsp;
<!-- 			
			<s:iterator
				value="pageNumberList"
				id="pageNumber">
				<a class="specialLink" href="movePage.action?visiblePage=${pageNumber}">${pageNumber}</a>
			</s:iterator>
 -->			
	&nbsp;
			<a class="specialLink" href="gotoNextPubPage.action">Next</a>|
			<a class="specialLink" href="gotoLastPubPage.action">Last</a>		
		
		</div>
		
	</div>

</div>

<div id="global" style="width:95%" class="resultListText">
    <table width="100%" cellspacing="0" cellpadding="5" rules="rows" border="1" style="border-collapse: collapse;">

 	<s:iterator value="publicationSearch.resultList.works" status="itsStatus">	
    <tr>
        <td valign="top" width="85px">
        	<s:set name="index" value="(#itsStatus.count - 1) + publicationSearch.startRec" />
        	<div style="float: left;" class="resultNumber">Record <s:property value="(#itsStatus.count - 1) + publicationSearch.startRec"/></div>
            <a class="specialLink" href="pubDetail.action?index=${index}">Details</a>
            <s:if test="publicationType=='Journal'"> 
            	<a class="specialLink" href="artSearchLaunch.action?index=${index}">ArtSearch</a>
            </s:if>
            <s:if test="publicationType=='e-Journal'"> 
            	<a class="specialLink" href="artSearchLaunch.action?index=${index}">ArtSearch</a>
            </s:if>
            <s:if test="publicationType=='Newspaper'"> 
            	<a class="specialLink" href="artSearchLaunch.action?index=${index}">ArtSearch</a>
            </s:if>
        </td>
        <td valign="top" style="border-color: LightGrey;">
        	<b>Publication Title:</b>&nbsp;<s:property value="fullTitle"/><br/>
        	<b>Author:</b>&nbsp;<s:property value="authorName"/><br/>
        	<b>Country:</b>&nbsp;<s:property value="country"/><br/>
        	<b>Language:</b>&nbsp;<s:property value="language"/><br/>
        </td>
        <td valign="top" style="border-color: LightGrey; width: 300px;">
        	<b><s:property value="idnoTypeCode"/>:</b>&nbsp;<s:property value="idno"/><br/>
        	<b>Publication Type:</b>&nbsp;<s:property value="publicationType"/><br/>
        	<b>Publisher:</b>&nbsp;<s:property value="publisherName"/><br/>
        	<b>Date:</b>&nbsp;<s:property value="runPubStartDate"/><br/>
        </td>
    </tr>
 	</s:iterator>

    </table>
</div>
</body>
</html>
