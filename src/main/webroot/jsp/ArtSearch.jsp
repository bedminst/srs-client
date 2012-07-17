<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import = "com.copyright.srsclient.internal.SearchRetrievalContext"%>
<%@ page import = "com.copyright.srsclient.web.ArtSearch"%>
<%@ page import = "com.copyright.srsclient.web.actions.ArtSearchAction"%>
<html>
<head>
<title>Article Search</title>
<style type="text/css">@import url(css/main.css);</style>
<style>
.errorMessage {
	color:red;
}
</style>
</head>
<body>
<div align="center"
	style="width: 90%; margin-left: 5%; border: solid 1px lightgrey">
<div align="right" style="border-bottom: solid 1px lightgrey;"><a href="publicationSearch.action">Publication Search</a></div>
<div align="center" style="border-bottom: solid 1px lightgrey;"><h4>Article Search<br></h4></div>
<div align="left" style="margin-left: 5%; margin-top: 10px">
	<s:form action="ArtResultList">

		<s:textfield style="width:400px" id="silent" name="articleSearch.hostIdno" label="Host Idno"/>
    	<s:textfield style="width:400px" id="silent" name="articleSearch.wrWrkInst" label="WR ID"/>
    	<s:textfield style="width:400px" name="articleSearch.title" label="Article Title"/>
    	<s:textfield style="width:400px" name="articleSearch.author" label="Author"/>
    	<s:textfield style="width:400px" name="articleSearch.artIdno" label="Article Idno"/>
    	<s:textfield style="width:400px" name="articleSearch.date" label="Date"/>
    	<s:textfield style="width:100px" name="articleSearch.itemVolume" label="Volume"/>
    	<s:textfield style="width:100px" name="articleSearch.itemIssue" label="Issue"/>
    	<s:textfield style="width:100px" name="articleSearch.itemStartPage" label="Start Page"/>
		<s:checkbox label="WR" name="articleSearch.cccWr" fieldValue="true"/>
		<s:checkbox label="Nature" name="articleSearch.nature" fieldValue="true"/>
		<s:checkbox label="New York Times" name="articleSearch.nyt" fieldValue="true"/>
		<s:checkbox label="Pubget" name="articleSearch.pubget" fieldValue="true"/>
	    <s:select cssStyle="width: 100px;" label="Sort By" 
		    name="articleSearch.sortOrder" 
		    headerKey="1"
		    list="#{'relevance':'Relevance', 'title:asc':'Title', 'date:desc':'Date'}"
   		/>
	    <s:select cssStyle="width: 100px;" label="Results per Page" 
		    name="articleSearch.resultsPerPage" 
		    headerKey="1"
		    list="#{'5':'5', '10':'10', '25':'25', '50':'50', '100':'100'}"
   		/>
    	<s:submit type="button" value="%{'Submit'}" label="Search"/>
		
	</s:form>
</div>
</div>
</body>
</html>
