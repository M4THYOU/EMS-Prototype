<%@page import="webEMS.Master"%>
<%@page import="webEMS.CompanyAccess"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>

<%
	String companyName = request.getParameter("companyName");
	Master companyMaster = new Master(companyName, false);
	webEMS.CompanyAccess companyAccess = new CompanyAccess("c:\\\\Users\\Matthew\\Desktop\\ems", companyName);
	
	session.setAttribute("companyName", companyName);
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript" src="js/base.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/base.css">
	
	<title>EMS</title>
</head>
<body>
	<h1><%= companyName %></h1>
	<a href="/webEMS/home.jsp">Back to Home</a>
	
	<h2>Enter a new Employee</h2>
	<form action="/webEMS/new-employee.jsp">
		<p>Employee First Name: <input type="text" name="firstName" required></p>
		<p>Employee Last Name: <input type="text" name="lastName" required></p>
		<p>Employee Position: <input type="text" name="position" required></p>
		<p>Manager: <input type="checkbox" name="isManager"></p>
		<input type="submit" value="Submit">
	</form>
	
	<h2>Employees at <%= companyName %></h2>
	
	<%
	
		ArrayList<String> employees = companyMaster.getAllEmployees("all", companyAccess);
		
		for (String employee:employees) {
			String[] data = employee.split(",");
			
			String nameAndPosition = (data[1] + " " + data[2] + "," + data[3]);
			String id = data[0];
			
			out.println("<p><a href='/webEMS/employee.jsp?companyName=" + companyName + "&employeeID=" + id + "'>" + nameAndPosition + "</a></p>");
			
			
		}
		
		if (employees.isEmpty()) {
			out.println("<p>No employees found.</p>");
		}
		
	%>
	
</body>
</html>