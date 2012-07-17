<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
	<title>Article Detailed Display</title>
	<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left"><strong><s:property
		value="articleSearch.resultList.resultCount" /></strong> results
	for <strong><i><s:property
		value="articleSearch.displayQuery" /></i></strong>
	</div>
</div>
	
<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">
	<div style="float: left">
	<a href="returnToRL">Back to Result List</a>
	</div> 
</div>

<div style="height: 20px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left;">
	
	    <div><b>Result </b> <s:property value="index" /> <b>from record range between</b> <s:property value="articleSearch.startRec" /> - <s:property value="articleSearch.endRec" />
			&nbsp;&nbsp;&nbsp;&nbsp;
		
			<a class="specialLink" href="artDetail.action?index=${index-1}">Prev </a>|
			<a class="specialLink" href="artDetail.action?index=${index+1}">Next</a>
		
		</div>
		
	</div>

</div>

<div id="global" style="width:100%" class="resultListText">
    <table width="100%" cellspacing="0" cellpadding="5" rules="rows" border="1" style="border-collapse: collapse;">

	<s:set name="work" value="articleSearch.resultList.works[index - articleSearch.startRec]" />

    <tr>
		<td valign="top" style="border-color: LightGrey;">
		<table>
			<tr><td><b>Provider:</b></td><td><s:property value="#work.providerKey"/></td></tr>
			<tr><td><b>HostIdno:</b></td><td><s:property value="#work.hostIdno"/></td></tr>
			<tr><td><b>WrWrkInst:</b></td><td><s:property value="#work.primaryKey"/></td></tr>
			<tr><td><b>TfWrkInst:</b></td><td><s:property value="#work.tfWrkInst"/></td></tr>
			<tr><td><b>Article Title:</b></td><td><s:property value="#work.fullTitle"/></td></tr>
			<tr><td><b><s:property value="#work.idnoTypeCode"/>:</b></td><td><s:property value="#work.idno"/></td></tr>
			<tr><td><b>Publisher:</b></td><td><s:property value="#work.publisherName"/></td></tr>
			<tr><td><b>Author:</b></td><td><s:property value="#work.authorName"/></td></tr>
			<tr><td><b>Editor:</b></td><td><s:property value="#work.editorName"/></td></tr>
			<tr><td><b>Date:</b></td><td><s:property value="#work.runPubStartDate"/></td></tr>
			<tr><td><b>PublicationType:</b></td><td><s:property value="#work.publicationType"/></td></tr>
			<tr><td><b>FrequentlyRequested:</b></td><td><s:property value="#work.isFrequentlyRequested"/></td></tr>
			<tr><td><b>TfWksInst:</b></td><td><s:property value="#work.tfWksInst"/></td></tr>
			<tr><td><b>Country:</b></td><td><s:property value="#work.country"/></td></tr>
			<tr><td><b>Language:</b></td><td><s:property value="#work.language"/></td></tr>
			<tr><td><b>IdnoWop:</b></td><td><s:property value="#work.idnoWop"/></td></tr>
			<tr><td><b>ItemStartPage:</b></td><td><s:property value="#work.itemStartPage"/></td></tr>
			<tr><td><b>ItemEndPage:</b></td><td><s:property value="#work.itemEndPage"/></td></tr>
			<tr><td><b>ItemPageRange:</b></td><td><s:property value="#work.itemPageRange"/></td></tr>
			<tr><td><b>ItemVolume:</b></td><td><s:property value="#work.itemVolume"/></td></tr>
			<tr><td><b>ItemIssue:</b></td><td><s:property value="#work.itemIssue"/></td></tr>
			<tr><td><b>ItemNumber:</b></td><td><s:property value="#work.itemNumber"/></td></tr>
			<tr><td><b>ItemSection:</b></td><td><s:property value="#work.itemSection"/></td></tr>
			<tr><td><b>ItemQuarter:</b></td><td><s:property value="#work.itemQuarter"/></td></tr>
			<tr><td><b>ItemSeason:</b></td><td><s:property value="#work.itemSeason"/></td></tr>
			<tr><td><b>ItemWeek:</b></td><td><s:property value="#work.itemWeek"/></td></tr>
			<tr><td><b>ItemWordCount:</b></td><td><s:property value="#work.itemWordCount"/></td></tr>
			<tr><td><b>ItemUrl:</b></td><td><s:property value="#work.itemUrl"/></td></tr>
		</table>
		</td>
    </tr>

    </table>
</div>
</body>
</html>
