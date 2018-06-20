<%@page import="webEMS.CompanyAccess"%>
<%@page import="webEMS.Master"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String companyName = (String) session.getAttribute("companyName");
	String status = request.getParameter("status");
	if (status == null) {
		status = "null";
	}
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	String position = request.getParameter("position");
	String isManager = request.getParameter("isManager");//returns either String "on" for true OR null value for false.
	
	String finalMessage = "";
	
	Master company;
	CompanyAccess compTB;
	
	if (companyName != null) {
		company = new Master(companyName, false);
		
		compTB = new CompanyAccess("c:\\\\Users\\Student.A219-16\\Desktop\\matthew_stuff\\sqlite\\ems", company.getName());
		
	} else {
		finalMessage = "A company name must be submitted.";
		return;
	}
	
	
	
	
	boolean manager;
	if (isManager == null) {
		manager = false;//if they ARE NOT a manager
	} else if (isManager.equals("on")) {
		manager = true;//if they ARE a manager
	} else {
		manager = false;
	}
	
	//company.userToDB(company.newUser(firstName, lastName, position, true), compTB);
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

	<%
		System.out.println("Status: " + status);
		String initialStatus = company.userToDBWeb(company.newUser(firstName, lastName, position, manager), status, compTB);
		String checkedStatus = compTB.checkStatus(initialStatus);
		String newStatus = compTB.insertionMessage(checkedStatus);
		System.out.println("INITIAL STATUS: " + initialStatus);
		System.out.println("CHECKED STATUS: " + checkedStatus);
		System.out.println("NEW STATUS: " + newStatus + "\n\n");
		
		boolean showForm = ((initialStatus.equals("c") || initialStatus.equals("g")) ? false:true);
		System.out.println(showForm);
		
		finalMessage = newStatus;
	%>

	<div class="center">
	<p><% out.println(finalMessage); %></p>
	
		<c:if test="<%= showForm %>">
			<form action="/webEMS/new-employee.jsp" id="status-form">
				<input type="hidden" value="<%= firstName %>" name="firstName">
				<input type="hidden" value="<%= lastName %>" name="lastName">
				<input type="hidden" value="<%= position %>" name="position">
				<input type="hidden" value="<%= isManager %>" name="isManager">
				<button type="submit" value="confirmed" name="status">Confirm</button>
				<button type="submit" value="cancelled" name="status">Cancel</button>
			</form>
		</c:if>
	</div>
	
	<a href="/webEMS/company.jsp?companyName=<%= companyName %>"><button type="button" name="back" onclick="history.back()">Back</button></a>
	<a href="/webEMS/home.jsp">Back to Home</a>

</div>
</body>
</html>