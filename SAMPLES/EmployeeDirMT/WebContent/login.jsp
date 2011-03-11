<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.athenasource.framework.eo.query.EJBQLSelect"%>
<%@page import="org.athenasource.framework.eo.core.context.EOThreadLocal"%>
<%@page import="com.sys.User"%>
<%@page import="org.athenasource.framework.eo.core.context.UserContext"%>
<%@page import="org.athenasource.framework.eo.web.BindingConstants"%>
<%@page import="java.io.PrintWriter"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body style="font-family: arial; ">
<%
String action = request.getParameter("action");

try {
	if("LOGIN".equalsIgnoreCase(action)) {
		EJBQLSelect selectUser = EOThreadLocal.getEOService().createEOContext().createSelectQuery("SELECT u FROM User u WHERE u.nameFull = ?1 AND u.password_ = ?2 AND u.ORG_ID = ?3 {os_u='false', result_first='1', result_max='1'}");
		selectUser.setParameter(1, request.getParameter("fullname"));
		selectUser.setParameter(2, request.getParameter("password"));
		selectUser.setParameter(3, Integer.parseInt(request.getParameter("orgid")));
		User user = (User)selectUser.getSingleResult();
		if(user == null) { // unable to find the user record
			out.println("<h3 style='color: red'>No match for Org ID/Full name/password. Please try again.</h3>");
		} else { // ok, login
			UserContext uc = new UserContext(user);
			session.setAttribute(BindingConstants.ATTRIBUTE_USERCONTEXT, uc);
			out.println("<h3 style='color: green'>User logged in to org (org id: " + user.getORG_ID() + ") successfully.</h3>");
			out.println("<p>You may visit <a href='main.jsp'>main.jsp</a> now.</p>");
		}
	} else if("LOGOUT".equalsIgnoreCase(action)) { // logout.
		session.setAttribute(BindingConstants.ATTRIBUTE_USERCONTEXT, null);
		out.println("<h3 style='color: green'>User logged out successfully.</h3>");
	}
} catch(Throwable t) {
	out.println("<pre style='color: red'>");
	t.printStackTrace(new PrintWriter(out));
	out.println("</pre>");
}
%>
<h3>Login Form</h3>
<form action="">
<input type="hidden" name="action" value="login" />
Org ID: <input name="orgid" type="text" size="1" />
User full name: <input name="fullname" type="text" size="8" />
Password: <input name="password" type="password" size="4" />
<input type="submit" value="Login">
</form>
<p><i>To view list of orgs and users, please visit <a href='admin.jsp' target=_blank>admin.jsp</a></i></p>
</body>
</html>