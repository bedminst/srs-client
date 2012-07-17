<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
	<title>Query Suggestions</title>
	<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
	
<div style="height: 50px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">
	<div style="float: left; color:red">
	<i>Your search for <strong><s:property
			value="publicationSearch.resultList.displayQuery" /></strong>
			generated no results.</i>
	</div>
</div>

<s:if test="publicationSearch.suggestions.size!=0">
	<div style="float: left">
	&nbsp;&nbsp;&nbsp;Please consider the following similar title search options:
	</div>
	
	<div id="global" style="width:95%" class="resultListText">
	    <table width="100%" cellspacing="0" cellpadding="5" rules="rows" border="1" style="border-collapse: collapse;">
	
	 	<s:iterator value="publicationSearch.suggestions" status="itsStatus">	
	    <tr>
	        <td valign="top" style="border-color: LightGrey;">
	            <div style="float: left;" class="resultNumber">Option <s:property value="#itsStatus.count"/></div><br/>
	            <a class="specialLink" href="pubSearchLaunch.action?title=${title}"><s:property value="title"/></a><br/>
	        </td>
	    </tr>
	 	</s:iterator>
	
	    </table>
	</div>
</s:if>

<div style="height: 50px; padding: 8px; border-left: solid 1px lightgrey; border-right: solid 1px lightgrey; border-bottom: solid 1px lightgrey; vertical-align: middle">

	<div style="float: left">
	<a href="publicationSearch">Return to Publication Search</a>
	</div> 
	
</div>

</body>
</html>
