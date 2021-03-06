<%@page import="webEMS.Master"%>
<%@page import="webEMS.CompanyAccess"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>

<%
	String companyName = request.getParameter("companyName");
	Master companyMaster = new Master(companyName, false);
	webEMS.CompanyAccess companyAccess = new CompanyAccess("c:\\\\Users\\Student.A219-16\\Desktop\\matthew_stuff\\sqlite\\ems", companyName);
	
	session.setAttribute("companyName", companyName);
	session.setAttribute("status", "null");
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
<div class="header">
	<div class="img-container">
		<img src="img/logo.png" height="100%" width="100%">
	</div>
	<h1 class="company-name"><%= companyName %></h1>
	
	<form action="/webEMS/search.jsp" class="search-form">
		<input type="text" placeholder="Employee ID or position/name" name="query">
		<select name="isManager">
			<option value="null">---</option>
			<option value="true">Manager</option>
			<option value="false">Not a Manager</option>
		</select>
		<input type="submit" value="Find" id="find">
	</form>
	
</div>
<div class="content">
	
	<a href="/webEMS/home.jsp">Back to Home</a>
	
	<h2>Enter a new Employee</h2>
	<form action="/webEMS/new-employee.jsp">
		<p>Employee First Name: <input type="text" name="firstName" required></p>
		<p>Employee Last Name: <input type="text" name="lastName" required></p>
		<p>Employee Position: <input type="text" name="position" required></p>
		<p>Manager: <input type="checkbox" name="isManager"></p>
		<input type="submit" value="Submit">
	</form>
	
	<br><br>
	<h2>Employees at <%= companyName %></h2>
	
	<p class="name-position" id="title"><span class="name">Name</span><span class="position">Position</span></p>
	
	<%
		ArrayList<String> employees = companyMaster.getAllEmployees("all", companyAccess);
		String switcher = "two";
		for (String employee:employees) {
			
			if (switcher.equals("one")) {
				switcher = "two";
			} else if (switcher.equals("two")) {
				switcher = "one";
			}
			
			String[] data = employee.split(",");
			
			String nameAndPosition = (data[1] + " " + data[2] + "," + data[3]);
			String id = data[0];
			
			String[] namePositionSplit = nameAndPosition.split(",");
			String name = namePositionSplit[0];
			String position = namePositionSplit[1];
			
			out.println("<p class='name-position' id='" + switcher + "'><a href='/webEMS/employee.jsp?companyName=" + companyName + "&employeeID=" + id + "'>"
						+ "<span class='name'>" + name + "</span>" + "<span class='position'>" + position + "</span>" +
						"</a></p>");
			
			
		}
		
		if (employees.isEmpty()) {
			out.println("<p>No employees found.</p>");
		}
	%>
</div>
</body>
</html>