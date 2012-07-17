<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
	<title>Publication Detailed Display</title>
	<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left"><strong><s:property
		value="publicationSearch.resultList.resultCount" /></strong> results
	for <strong><i><s:property
		value="publicationSearch.displayQuery" /></i></strong>
	</div>
</div>
	
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">
	<div style="float: left">
	<a href="returnToPubRL">Back to Result List</a>
	</div> 
</div>

<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left;">
	
	    <div><b>Result </b> <s:property value="index" /> <b>from record range between</b> <s:property value="publicationSearch.startRec" /> - <s:property value="publicationSearch.endRec" />
			&nbsp;&nbsp;&nbsp;&nbsp;
		
			<a class="specialLink" href="pubDetail.action?index=${index-1}">Prev </a>|
			<a class="specialLink" href="pubDetail.action?index=${index+1}">Next</a>
		
		</div>
		
	</div>

</div>

<div id="global" style="width:100%" class="resultListText">
    <table width="100%" cellspacing="0" cellpadding="5" rules="rows" border="1" style="border-collapse: collapse;">

	<s:set name="work" value="publicationSearch.resultList.works[index - publicationSearch.startRec]" />

    <tr>
		<td valign="top" style="border-color: LightGrey;">
		<table>
			<tr><td><b>WrWrkInst:</b></td><td><s:property value="#work.primaryKey"/></td></tr>
			<tr><td><b>TfWrkInst:</b></td><td><s:property value="#work.tfWrkInst"/></td></tr>
			<tr><td><b>Publication Title:</b></td><td><s:property value="#work.fullTitle"/></td></tr>
			<tr><td><b><s:property value="#work.idnoTypeCode"/>:</b></td><td><s:property value="#work.idno"/></td></tr>
			<tr><td><b>Volume:</b></td><td><s:property value="#work.volume"/></td></tr>
			<tr><td><b>Edition:</b></td><td><s:property value="#work.edition"/></td></tr>
			<tr><td><b>Publisher:</b></td><td><s:property value="#work.publisherName"/></td></tr>
			<tr><td><b>Author:</b></td><td><s:property value="#work.authorName"/></td></tr>
			<tr><td><b>Editor:</b></td><td><s:property value="#work.editorName"/></td></tr>
			<tr><td><b>PubStartDate:</b></td><td><s:property value="#work.runPubStartDate"/></td></tr>
			<tr><td><b>PubEndDate:</b></td><td><s:property value="#work.runPubEndDate"/></td></tr>
			<tr><td><b>PublicationType:</b></td><td><s:property value="#work.publicationType"/></td></tr>
			<tr><td><b>FrequentlyRequested:</b></td><td><s:property value="#work.isFrequentlyRequested"/></td></tr>
			<tr><td><b>TfWksInst:</b></td><td><s:property value="#work.tfWksInst"/></td></tr>
			<tr><td><b>Country:</b></td><td><s:property value="#work.country"/></td></tr>
			<tr><td><b>Language:</b></td><td><s:property value="#work.language"/></td></tr>
			<tr><td><b>Oclcnum:</b></td><td><s:property value="#work.oclcnum"/></td></tr>
			<tr><td><b>IdnoWop:</b></td><td><s:property value="#work.idnoWop"/></td></tr>
			<tr><td><b>Pages:</b></td><td><s:property value="#work.pages"/></td></tr>
			<tr><td><b>Series:</b></td><td><s:property value="#work.series"/></td></tr>
			<tr><td><b>SeriesNumber:</b></td><td><s:property value="#work.seriesNumber"/></td></tr>
			<tr><td><b>RR Tags:</b></td><td><s:property value="#work.rrTags"/></td></tr>
			<tr><td><b>LB Tags:</b></td><td><s:property value="#work.lbTags"/></td></tr>
			<tr><td><b>SRS Tags:</b></td><td><s:property value="#work.srsTags"/></td></tr>
			<tr><td><b>OtherFormatIdnos:</b></td><td><s:property value="#work.otherFormatIdnos"/></td></tr>
		</table>
		</td>
    </tr>

    </table>
</div>
</body>
</html>
