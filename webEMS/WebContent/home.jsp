<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>
    
<% DBAccess.connect("c:\\\\Users\\Student.A219-16\\Desktop\\matthew_stuff\\sqlite\\ems"); %>
    
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
</div>
<div class="content">
	
	<h2>Enter a new Company</h2>
	<form action="/webEMS/company-selected.jsp">
		<p>Company Name: <input type="text" name="companyName" required></p>
		<input type="submit" value="Submit">
	</form>
	
	<br>
	<h2>Current Companies</h2>
	<%
		ArrayList<String> companies = DBAccess.getCompanies();
		request.setAttribute("companies", companies);
	%>

	<c:forEach items="${companies}" var="company">
		
		<c:url value="company.jsp" var="companyURL">
			<c:param name="companyName" value="${company}"/>
		</c:url>
		
		<p><a href='/webEMS/<c:out value="${companyURL}"/>'><c:out value="${company}"/></a></p>
		
	</c:forEach>
	
</div>
</body>
</html>