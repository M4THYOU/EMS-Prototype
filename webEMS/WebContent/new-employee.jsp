<%@page import="webEMS.CompanyAccess"%>
<%@page import="webEMS.Master"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String companyName = (String) session.getAttribute("companyName");
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	String position = request.getParameter("position");
	String isManager = request.getParameter("isManager");//returns either String "on" for true OR null value for false.
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
	<p>
	<%
		if (companyName != null) {
			Master company = new Master(companyName, false);
			out.println(DBAccess.getPrintMsg());
			
			CompanyAccess compTB = new CompanyAccess("c:\\\\Users\\Matthew\\Desktop\\ems", companyName);
			
			int manager;
			if (isManager == null) {
				manager = 0;//if they ARE NOT a manager
			} else if (isManager.equals("on")) {
				manager = 1;//if they ARE a manager
			} else {
				manager = 0;
			}
			out.println(manager);
			
			//company.userToDB(company.newUser(firstName, lastName, position, true), compTB);
			
		} else {
			out.println("A company name must be submitted.");
		}
	%>
	</p>
	<a href="/webEMS/home.jsp">Back to Home</a>
	
</body>
</html>