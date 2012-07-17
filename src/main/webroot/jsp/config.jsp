<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.copyright.svc.searchRetrieval.SearchRetrievalServiceBuildData"%>
<%@ page import="com.copyright.srsclient.SearchRetrievalClientVersionData"%>
<%@ page import="com.copyright.srsclient.api.SearchRetrievalClientConfiguration"%>
<%@ page import="com.copyright.svc.searchRetrieval.api.SearchRetrievalServiceConfiguration"%>
<%@ taglib prefix="cfg" uri="http://xmlns.copyright.com/tld/config.tld" %>
<%@ taglib prefix="svcCommon" uri="http://xmlns.copyright.com/tld/ccc-svc-common.tld" %>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>SRS Client Configuration</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
	<div style="float: right">
		<a href="SrsWelcome.action">Back to SRS Welcome Screen</a>
	</div> 
	<h1 align="center"> <u>SRS Client Configuration</u></h1>
  
	<h2><u>Build Version Data</u></h2>
	<cfg:versionData versionData="<%= SearchRetrievalClientVersionData.getInstance() %>" header="SRS Client" tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:buildData buildData="<%= SearchRetrievalServiceBuildData.getInstance() %>" header="svc-searchRetrieval" tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>Config Properties</u></h2>
	<cfg:properties properties="<%= SearchRetrievalClientConfiguration.getInstance().getProperties() %>" header="<%= SearchRetrievalClientConfiguration.getInstance().getFile() %>" tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>CCC Shared Components</u></h2>
	<cfg:cccBase tableAttr="border=1 cellpadding=1  width=80%"/>
	<cfg:appIntegrity tableAttr="border=1 cellpadding=1  width=80%"/>
	<cfg:svcCommon tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>Web Application</u></h2>
	<cfg:deployment tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:jboss tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:jvm tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>Deployed Applications</u></h2>
	<cfg:deployedApps header="Apps" generateConfigLinks="true" tableAttr="border=1 cellpadding=1  width=80%"/>
	<br/>
  </body>
</html>
