<%@page import="webEMS.Master"%>
<%@page import="webEMS.CompanyAccess"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>

<%
	String companyName = request.getParameter("companyName");
	int employeeID = Integer.parseInt(request.getParameter("employeeID"));
	
	Master companyMaster = new Master(companyName);
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
	<h1><%= name %></h1>
	
	<button type="button" name="back" onclick="history.back()">Back</button>
	
	<p><strong>Company:</strong> <%= companyName %></p>
	<p><strong>Position:</strong> <%= position %></p>
	<p><strong>Is a manager:</strong> <%= isManager %></p>
	
</body>
</html>