<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<html>
<head>
<title>Publication Search</title>
<style type="text/css">@import url(css/main.css);</style>
<style>
.errorMessage {
	color:red;
}
</style>

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8" >
function autosuggest(term, endpoint, items, count)
{
	var query = "q=" + escape(term);
    var url = "suggest.action?" + query + "&endpoint=" + endpoint + "&items=" + items + "&count=" + count;

    $.get(url, function(data, textStatus) {

      // this will give us an array of objects
      var terms = JSON.parse(data);

      // iterate over terms
      var list = new Array();
      for(var i=0; i < terms.response.docs.length; i++) {
		var trm = terms.response.docs[i];
		list[i] = trm.title;
      }

      $( "#tokens" ).autocomplete( "option", "source", list );

    }, 'text');

} 

$(function() {
		$( "#tokens" ).autocomplete(
		   { autoFocus: true }
		);
		
	});
</script>

</head>
<body>
<div align="center"
	style="width: 90%; margin-left: 5%; border: solid 1px lightgrey">
<div align="right" style="border-bottom: solid 1px lightgrey;"><a href="articleSearch.action">Article Search</a></div>
<div align="center" style="border-bottom: solid 1px lightgrey;"><h4>Publication Search<br></h4></div>
<div align="left" style="margin-left: 5%; margin-top: 10px">
	<s:form action="PubResultList">

	    <s:hidden name="publicationSearch.endpoint" value="%{autosuggestEndpoint}" id="endpoint"/>
	    <s:hidden name="publicationSearch.count" value="%{autosuggestCount}" id="count"/>
	    <s:hidden name="publicationSearch.items" value="%{autosuggestItems}" id="items"/>
    	<s:select id="silent" name="publicationSearch.hasRights" label="Has Rights"  list="#{'Y':'Yes', '':'Any', 'N':'No'}"/>
    	<s:textfield style="width:400px" id="silent" name="publicationSearch.wrWrkInst" label="WR ID"/>
    	<s:textfield style="width:400px" id="silent" name="publicationSearch.tfWrkInst" label="TF ID"/>
    	<s:textfield style="width:400px" id="tokens" name="publicationSearch.titleStdNo" label="Publication Title or ISSN/ISBN" onkeyup="autosuggest(this.value,endpoint.value,items.value,count.value)"/>
    	<s:textfield style="width:400px" name="publicationSearch.authorEditor" label="Author or Editor"/>
    	<s:textfield style="width:400px" name="publicationSearch.publisherRightsholder" label="Publisher or Rightsholder"/>
    	<s:textfield style="width:400px" name="publicationSearch.seriesName" label="Series Name"/>
 		<s:updownselect
		list="srContext.pubContext.publicationTypeList"
		name="publicationSearch.selectedPublicationTypeList" label="Publication Type"
		headerKey="-1"
		emptyOption="true" />
 		<s:updownselect
		list="srContext.pubContext.countryList"
		name="publicationSearch.selectedCountryList" label="Country"
		headerKey="-1"
		emptyOption="true" />
 		<s:updownselect
		list="srContext.pubContext.languageList"
		name="publicationSearch.selectedLanguageList" label="Language"
		headerKey="-1"
		emptyOption="true" />
	    <s:select cssStyle="width: 100px;" label="Sort By" 
		    name="publicationSearch.sortOrder" 
		    headerKey="1"
		    list="#{'relevance':'Relevance', 'title:asc':'Title', 'publisher:asc':'Publisher', 'date:desc':'Date'}"
   		/>
	    <s:select cssStyle="width: 100px;" label="Results per Page" 
		    name="publicationSearch.resultsPerPage" 
		    headerKey="1"
		    list="#{'5':'5', '10':'10', '25':'25', '50':'50', '100':'100'}"
   		/>
    	<s:submit type="button" value="%{'Submit'}" label="Search"/>
		
	</s:form>
</div>
</div>
</body>
</html>
