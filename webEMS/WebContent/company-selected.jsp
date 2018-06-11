<%@page import="webEMS.Master"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="webEMS.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% String companyName = request.getParameter("companyName"); %>

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
			Master company = new Master(companyName, true);
			out.println(DBAccess.getPrintMsg());
		} else {
			out.println("A company name must be submitted.");
		}
	%>
	</p>
	<a href="/webEMS/home.jsp">Back to Home</a>
	
</body>
</html>