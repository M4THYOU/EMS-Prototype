<%@page import="webEMS.Master"%>
<%@page import="webEMS.CompanyAccess"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>

<%
	String companyName = request.getParameter("companyName");
	int employeeID = Integer.parseInt(request.getParameter("employeeID"));
	
	Master companyMaster = new webEMS.Master(companyName, false);
	webEMS.CompanyAccess companyAccess = new CompanyAccess("c:\\\\Users\\Student.A219-16\\Desktop\\matthew_stuff\\sqlite\\ems", companyName);
	
	ArrayList<String> employeeList = companyMaster.getEmployee(Integer.toString(employeeID), "null", companyAccess);
	String currentEmployee = employeeList.get(0);
	
	String[] data = currentEmployee.split(",");
	String name = data[1] + " " + data[2];
	String position = data[3];
	boolean isManager = ((Integer.parseInt(data[4]) == 1) ? true:false);
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
	<h1><%= companyName %> - <%= name %></h1>
	
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
	
	<p><strong>Position:</strong> <%= position %></p>
	<p><strong>Is a manager:</strong> <%= isManager %></p>
	
	<button type="button" name="back" onclick="history.back()">Back</button>

</div>
</body>
</html>