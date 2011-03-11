<%
if(EOThreadLocal.getUserContext() == null) { // not in session, redirect to login page.
	response.sendRedirect("login.jsp");
	return;
} else {
	out.println("<a href='login.jsp?action=logout'>Logout</a> ");
	EOThreadLocal.setOrgId(EOThreadLocal.getUserContext().getUser().getOrgId()); // set org id.
	out.println("ORG ID: " + EOThreadLocal.getOrgId() + ", User: " + EOThreadLocal.getUserContext().getUser().getNameFull());
}

%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.athenasource.framework.eo.core.UnitOfWork"%>
<%@page import="com.test.Employee"%>
<%@page import="java.util.List"%>
<%@page import="org.athenasource.framework.eo.query.EJBQLSelect"%>
<%@page import="org.athenasource.framework.eo.core.EOService"%>
<%@page import="org.athenasource.framework.eo.core.context.EOThreadLocal"%>
<%@page import="org.athenasource.framework.eo.core.EOObject"%>
<%@page import="com.test.generated.Employee_EO"%>
<%@page import="org.athenasource.framework.eo.core.EOContext"%><html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<body style="font-family: arial; ">
<h2>Employee Directory</h2>

<p><a href="?action=LIST">List</a> | <a href="?action=FORM_CREATE">Create</a> </p>
<br>

<%

String action = request.getParameter("action");
if(action == null || action.trim().length() == 0) { // if no action specified, use 'LIST'.
	action = "LIST";
}

if("LIST".equalsIgnoreCase(action)) {

	EOService eoService = EOThreadLocal.getEOService();
	EJBQLSelect selectEmployees = eoService.createEOContext().createSelectQuery("SELECT e FROM Employee e");
	List<Object> employees = selectEmployees.getResultList();

	out.println("<p>Total number of employees: " + employees.size() + "</p>");
	out.println("<ul>");
	for(int i=0; i < employees.size(); i++) {
		Employee employee = (Employee)employees.get(i);
		out.println("<li>" + employee.getNameFull() + ", born in " + employee.getBornYear());
		out.println(" <a href='?action=FORM_UPDATE&empid=" + employee.getEmployee_ID() + "'><font size=-1>Update</font></a>");
		out.println(" <a href='?action=DELETE&empid=" + employee.getEmployee_ID() + "' onClick=\"return confirm('Are you sure to delete this employee?')\"><font size=-1>Delete</font></a>");
	}
	out.println("</ul>");

}else if("CREATE".equalsIgnoreCase(action)) {

	UnitOfWork uow = EOThreadLocal.getEOService().createUnitOfWork();
	Employee newEmp = (Employee)uow.createNewInstance(Employee.SYSTEM_NAME);
	uow.persist(newEmp);
	try {
		newEmp.setNameFull(request.getParameter("fullname"));
		newEmp.setBornYear(Integer.parseInt(request.getParameter("bornyear")));
		uow.flush();
		uow.close();
		out.println("Employee created successfully. ID: " + newEmp.getEmployee_ID());
	}catch(Throwable t) {
		out.println("Failed to create employee due to exception: <pre>");
		t.printStackTrace(new PrintWriter(out));
		out.println("</pre>");
	}

}else if("UPDATE".equalsIgnoreCase(action)) {

	UnitOfWork uow = EOThreadLocal.getEOService().createUnitOfWork();
	EJBQLSelect selectEmp = uow.createSelectQuery("SELECT e FROM Employee e WHERE e.employee_ID = ?1");
	selectEmp.setParameter(1, Integer.parseInt(request.getParameter("empid")));
	Employee emp = (Employee)selectEmp.getSingleResult();
	if(emp == null) {
		out.println("Employee not found, id: " + request.getParameter("empid"));
	}else{
		try {
			emp.setNameFull(request.getParameter("fullname"));
			emp.setBornYear(Integer.parseInt(request.getParameter("bornyear")));
			uow.flush();
			uow.close();
			out.println("Employee data updated successfully, id: " + emp.getEmployee_ID());
		}catch(Throwable t) {
			out.println("Failed to create employee due to exception: <pre>");
			t.printStackTrace(new PrintWriter(out));
			out.println("</pre>");
		}
	}

}else if("DELETE".equalsIgnoreCase(action)) { // delete
	UnitOfWork uow = EOThreadLocal.getEOService().createUnitOfWork();
	Employee emp = (Employee)uow.find(Employee_EO.SYSTEM_NAME, Integer.parseInt(request.getParameter("empid")), null, null);
	if(emp == null) {
		out.println("Employee not found, id: " + request.getParameter("empid"));
	}else{
		try {
			uow.remove(emp);
			uow.flush();
			uow.close();
			out.println("Employee data deleted successfully, id: " + emp.getEmployee_ID());
		}catch(Throwable t) {
			out.println("Failed to delete employee due to exception: <pre>");
			t.printStackTrace(new PrintWriter(out));
			out.println("</pre>");
		}
	}

}else if("FORM_CREATE".equalsIgnoreCase(action)) { // display form for create
%>

<form action="">
<input type="hidden" name="action" value="CREATE" />
<%= EOThreadLocal.getEOService().getEntity(Employee_EO.SYSTEM_NAME).getAttributeBySystemName(Employee_EO.ATTR_nameFull).getDisplayName() %>: <input name="fullname" type="text" size="20" />
<%= EOThreadLocal.getEOService().getEntity(Employee_EO.SYSTEM_NAME).getAttributeBySystemName(Employee_EO.ATTR_bornYear).getDisplayName() %>: <input name="bornyear" type="text" size="4" />
<input type="submit" value="Create">
</form>

<%
} else if("FORM_UPDATE".equalsIgnoreCase(action)) { // display form for update
	Employee emp = null;

	EJBQLSelect selectEmp = EOThreadLocal.getEOService().createEOContext().createSelectQuery("SELECT e FROM Employee e WHERE e.employee_ID = ?1");
	selectEmp.setParameter(1, Integer.parseInt(request.getParameter("empid")));
	emp = (Employee)selectEmp.getSingleResult();
%>

<form action="">
<input type="hidden" name="action" value="UPDATE" />
<input type="hidden" name="empid" value="<%= request.getParameter("empid") %>" />
<%= EOThreadLocal.getEOService().getEntity(Employee_EO.SYSTEM_NAME).getAttributeBySystemName(Employee_EO.ATTR_nameFull).getDisplayName() %>: <input name="fullname" type="text" size="20" value="<%= emp.getNameFull() %>"/>
<%= EOThreadLocal.getEOService().getEntity(Employee_EO.SYSTEM_NAME).getAttributeBySystemName(Employee_EO.ATTR_bornYear).getDisplayName() %>: <input name="bornyear" type="text" size="4" value="<%= emp.getBornYear() %>"/>
<input type="submit" value="Update">
</form>

<%
}else if(action == null || action.trim().length() == 0) {
	out.println("Welcome.");
}else{
	out.println("Unsupported action: " + action);
}

%>

<p align="left" style="padding-top: 20px;">
<hr style="width: 100%; color: #ccc; height: 1px;"/>
<a href="http://www.athenaframework.org" target='_blank'>
<img src="http://www.athenaframework.org/_img/logo/logo-poweredby.png" alt="Powered by Athena Framework" align="left" hspace="0" vspace="1" border="0">
</a></p>
</body>
</html>