<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<title>SRS Client</title>
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
<div align="center" style="border-bottom: solid 1px lightgrey;"><h3>Search Retrieval Service Client App Version 1.0<br></h3></div>
<div align="center" style="margin-left: 5%; margin-top: 10px">
	<div style="float: right">
		<a href="config.action">Configuration</a>
	</div> 
	<s:form action="SrsWelcome">
	
	<a href="publicationSearch.action">Publication Search</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="articleSearch.action">Article Search</a>

	</s:form>
</div>
</div>
</body>
</html>
