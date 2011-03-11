<%@page import="java.io.PrintWriter"%>
<%@page import="org.athenasource.framework.eo.core.EOService"%>
<%@page import="org.athenasource.framework.eo.core.context.EOThreadLocal"%>
<%@page import="org.athenasource.framework.eo.query.EJBQLSelect"%>
<%@page import="java.util.List"%>
<%@page import="com.sys.Org"%>
<%@page import="com.sys.User"%>
<%@page import="org.athenasource.framework.eo.core.UnitOfWork"%>
<%@page import="com.sys.generated.User_EO"%>
<%@page import="com.sys.generated.Org_EO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body style="font-family: arial; ">
<h2>Org/User Administration</h2>

<%
String action = request.getParameter("action");

if("CREATE".equalsIgnoreCase(action)) {
	try {
		// validate input parameters
		int orgId = Integer.parseInt(request.getParameter("orgid"));
		if(orgId <= 0) {
			throw new IllegalArgumentException("Org id must be greater than 0 - your input was: " + orgId);
		}

		String nameFull = request.getParameter("fullname");
		if(nameFull == null || nameFull.trim().length() == 0) {
			throw new IllegalArgumentException("Please provide the full name of the user");
		}

		String password = request.getParameter("password");
		if(password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Please provide the password for the user");
		}

		UnitOfWork uow = EOThreadLocal.getEOService().createUnitOfWork();

		EJBQLSelect selectExistingOrg = uow.createSelectQuery("SELECT o FROM Org o WHERE o.org_ID = ?1 {os_o='false'}");
		selectExistingOrg.setParameter(1, orgId);
		Org existingOrg = (Org) selectExistingOrg.getSingleResult();
		if(existingOrg == null) { // org with the given id does not exist, create it now.
			Org org = (Org) uow.createNewInstance(Org_EO.SYSTEM_NAME);
			org.setOrg_ID(orgId);
			org.setNameFull("<untitled>");
		}

		User user = (User) uow.createNewInstance(User_EO.SYSTEM_NAME);
		user.setNameFull(nameFull);
		user.setPassword_(password);
		user.setORG_ID(orgId);

		uow.flush();
		out.println("<h3 style='color: green;'>New user created successfully.</h3>");
	}catch(Throwable t) {
		out.println("<pre style='color: red'>");
		t.printStackTrace(new PrintWriter(out));
		out.println("</pre>");
	}
}

%>


<h3>Create new user</h3>
<form action="">
<input type="hidden" name="action" value="CREATE" />
<%= EOThreadLocal.getEOService().getEntity(User_EO.SYSTEM_NAME).getAttributeBySystemName(User_EO.ATTR_nameFull).getDisplayName() %>: <input name="fullname" type="text" size="20" />
<%= EOThreadLocal.getEOService().getEntity(User_EO.SYSTEM_NAME).getAttributeBySystemName(User_EO.ATTR_password_).getDisplayName() %>: <input name="password" type="text" size="4" />
 Org ID: <input name="orgid" type="text" size="4" />
<input type="submit" value="Create">
</form>

<%
// list orgs and users
EOService eoService = EOThreadLocal.getEOService();
EJBQLSelect selectOrgs = eoService.createEOContext().createSelectQuery("SELECT o FROM Org o ORDER BY o.org_ID {os_o='false'}");
List<Object> orgs = selectOrgs.getResultList();

out.println("<h3>Total number of orgs: " + orgs.size() + "</h3>");
out.println("<ul>");
for(int i=0; i < orgs.size(); i++) {
	Org org = (Org)orgs.get(i);
	out.println("\n<li>Org - ID: " + org.getOrg_ID());
	out.println("\n<ul>");
	EJBQLSelect selectUsers = eoService.createEOContext().createSelectQuery("SELECT u FROM User u WHERE u.ORG_ID = ?1 ORDER BY u.user_ID {os_u='false'}");
	selectUsers.setParameter(1, org.getOrg_ID());
	List<Object> users = selectUsers.getResultList();
	for(int u = 0; u < users.size(); u++) {
		User user = (User) users.get(u);
		out.println("\n<li>User - nameFull: <b>" + user.getNameFull() + "</b>, password: <b>" + user.getPassword_() + "</b> (ID: " + user.getId() + ")</li>");
	}
	out.println("\n</ul></li>");
}
out.println("\n</ul>");

%>

 <a href='main.jsp'>-&gt;Go to main.jsp</a>

<p align="left" style="padding-top: 20px;">
<hr style="width: 100%; color: #ccc; height: 1px;"/>
<a href="http://www.athenaframework.org" target='_blank'>
<img src="http://www.athenaframework.org/_img/logo/logo-poweredby.png" alt="Powered by Athena Framework" align="left" hspace="0" vspace="1" border="0">
</a></p>
</body>
</html>