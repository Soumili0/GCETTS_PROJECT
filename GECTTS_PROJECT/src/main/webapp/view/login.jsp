<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>Login  Here</h4>
<c:if test="${not empty errorMsg }">
<h4 style="color:red"> ${errorMsg }</h4>
</c:if>


<form action="/loginForm" method="post">
Email:<input type="email" name="email" required/><br/><br/>
Password:<input type="password" name="password" required/><br/><br/>
<input type="hidden" name="role" value="TEACHER" />
Login:<input type="submit" name="Login"/><br/><br/><br/>
 if not Register...<a href="/regPage"><b>Click Here</b></a>
</form>
</body>
</html>