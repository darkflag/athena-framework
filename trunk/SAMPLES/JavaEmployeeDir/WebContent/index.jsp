<%@page import="java.util.List"%>
<%@page import="org.athenasource.framework.eo.core.baseclass.Entity"%>
<%@page import="org.athenasource.framework.eo.query.EJBQLSelect"%>
<%@page import="org.athenasource.framework.eo.core.EOService"%>
<%@page import="org.athenasource.framework.eo.core.context.EOThreadLocal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body style="font-family: arial; ">
<h2>Welcome</h2>
<h3>Entity List:</h3>
<p>
<%
	// Feel free to modify the code
	EOService eoService = EOThreadLocal.getEOService();
	List customEntities = eoService.createEOContext().createSelectQuery("SELECT e FROM Entity e WHERE e.entity_ID > 100").getResultList();
	out.write("Total number of entities defined: " + customEntities.size() + "\n");
	if(customEntities.size() == 0) {
		out.write("</p><p>To define entities, please open Athena Console, then launch the Metadata Workbench.");
	} else {
		out.write("<ul>");
		for(int i = 0; i < customEntities.size(); i++) {
			Entity entity = (Entity) customEntities.get(i);
			out.write("<li>" + entity.getSystemName() + " [" + entity.getEntity_ID() + "]");
		}
		out.write("</ul>");
	}
%>
</p>
<p align="left" style="padding-top: 20px;">
<hr style="width: 100%; color: ${symbol_pound}ccc; height: 1px;"/>
<a href="http://www.athenasource.org" target='_blank'>
<img src="http://www.athenaframework.org/_img/logo/logo-poweredby.png" alt="Powered by Athena Framework" align="left" hspace="0" vspace="1" border="0">
</a></p>
</body>
</html>