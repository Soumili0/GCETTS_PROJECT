<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrator Login</title>
</head>
<body>
<h4>Administrator Login</h4>
<c:if test="${param.msg == 'registered'}">
<h4 style="color:green"> Registration successful. You are logged in automatically.</h4>
</c:if>

<c:if test="${not empty errorMsg }">
<h4 style="color:red"> ${errorMsg }</h4>
</c:if>

<form action="Adm_Form" method="post">
Email:<input type="email" name="email" required/><br/><br/>
Password:<input type="password" name="password" required/><br/><br/>
Login:<input type="submit" name="Login"/><br/><br/><br/>
 if not Register...<a href="Admin_Register"><b>Click Here</b></a>
</form>
</body>
</html>