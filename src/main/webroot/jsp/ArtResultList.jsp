<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
	<title>Article Result List</title>
	<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left"><strong><s:property
		value="articleSearch.resultList.resultCount" /></strong> results
	for <strong><i><s:property
		value="articleSearch.resultList.displayQuery" /></i></strong>
	</div>
</div>
	
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">
	<div style="float: left">
	<a href="articleSearch">Return to Article Search</a>
	</div> 
	<div style="float: right">
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<s:form action="ArtResultList">
	<div style="float: right;">
	    <s:hidden name="articleSearch.hostIdno"/>
	    <s:hidden name="articleSearch.title"/>
    	<s:hidden name="articleSearch.author"/>
    	<s:hidden name="articleSearch.artIdno"/>
    	<s:hidden name="articleSearch.date"/>
	    <s:hidden name="articleSearch.sortOrder"/>
	    <s:hidden name="articleSearch.itemVolume"/>
	    <s:hidden name="articleSearch.itemIssue"/>
	    <s:hidden name="articleSearch.itemStartPage"/>
		<s:hidden name="articleSearch.cccWr"/>
		<s:hidden name="articleSearch.nature"/>
		<s:hidden name="articleSearch.nyt"/>
		<s:hidden name="articleSearch.pubget"/>
	    <s:select cssStyle="width: 100px;" label="Results per Page" 
		    name="articleSearch.resultsPerPage" 
		    headerKey="1"
		    list="#{'5':'5', '10':'10', '25':'25', '50':'50', '100':'100'}"
		    onchange="this.form.submit()"
   		/>
	
	</div>
	</s:form>

	<b>Sort by:&nbsp;</b>
	
	<a 
	<s:if test="articleSearch.sortOrder=='relevance'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortArtByRelevance.action">Relevance</a>
	  
	&nbsp;|&nbsp;
	
	<a 
	<s:if test="articleSearch.sortOrder=='title:asc'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortArtByTitle.action">Title</a>
	
	&nbsp;|&nbsp;
	
	<a 
	<s:if test="articleSearch.sortOrder=='date:desc'">
	class="selected" 
	</s:if>
	<s:else>
	class="specialLink" 
	</s:else>
	 href="sortArtByDate.action">Date</a>
	
	</div>
	
</div>

<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left;">
	
	    <div><b>Results</b> 
		    <s:property value="articleSearch.startRec" /> - <s:property value="articleSearch.endRec" /> of <s:property
				value="articleSearch.resultList.resultCount" />
			&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<b>Page</b>&nbsp;&nbsp;&nbsp;&nbsp;
			<s:property
				value="articleSearch.pageNum" />
			&nbsp;&nbsp;&nbsp;&nbsp;
		
			<a class="specialLink" href="gotoFirstArtPage.action">First</a>|
			<a class="specialLink" href="gotoPrevArtPage.action">Prev</a>
	&nbsp;
<!-- 			
			<s:iterator
				value="pageNumberList"
				id="pageNumber">
				<a class="specialLink" href="movePage.action?visiblePage=${pageNumber}">${pageNumber}</a>
			</s:iterator>
 -->			
	&nbsp;
			<a class="specialLink" href="gotoNextArtPage.action">Next</a>|
			<a class="specialLink" href="gotoLastArtPage.action">Last</a>		
		
		</div>
		
	</div>

</div>

<div id="global" style="width:95%" class="resultListText">
    <table width="100%" cellspacing="0" cellpadding="5" rules="rows" border="1" style="border-collapse: collapse;">

 	<s:iterator value="articleSearch.resultList.works" status="itsStatus">	

    <tr>
        <td valign="top" width="85px">
        	<s:set name="index" value="(#itsStatus.count - 1) + articleSearch.startRec" />
            <div style="float: left;" class="resultNumber">Record <s:property value="(#itsStatus.count - 1) + articleSearch.startRec"/></div><br/>
            <a class="specialLink" href="artDetail.action?index=${index}">Details</a>
        </td>
        <td valign="top" style="border-color: LightGrey;">
        	<b>Article Title:</b>&nbsp;<s:property value="fullTitle"/><br/>
        	<b>Author:</b>&nbsp;<s:property value="authorName"/><br/>
        	<b>Publisher:</b>&nbsp;<s:property value="publisherName"/><br/>
        	<b>Country:</b>&nbsp;<s:property value="country"/><br/>
        	<b>Language:</b>&nbsp;<s:property value="language"/><br/>
        </td>
        <td valign="top" style="border-color: LightGrey; width: 300px;">
        	<b>Provider:</b>&nbsp;<s:property value="providerKey"/><br/>
        	<b><s:property value="idnoTypeCode"/>:</b>&nbsp;<s:property value="idno"/><br/>
        	<b>Date:</b>&nbsp;<s:property value="runPubStartDate"/><br/>
        	<b>Item Volume:</b>&nbsp;<s:property value="itemVolume"/><br/>
        	<b>Item Issue:</b>&nbsp;<s:property value="itemIssue"/><br/>
        </td>
    </tr>

 	</s:iterator>

    </table>
</div>
</body>
</html>
