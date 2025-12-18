<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>Register Here</h4>
<c:if test="${not empty successMsg }">
<h4 style="color:green"> ${successMsg }</h4>
</c:if>


<c:if test="${not empty errorMsg }">
<h4 style="color:red"> ${errorMsg }</h4>
</c:if>


<form action="/regform" method="post">
<input type="hidden" name="role" value="TEACHER" />
Name:<input type="text" name="name"/><br/><br/>
Email:<input type="text" name="email"/><br/><br/>
Password:<input type="text" name="password"/><br/><br/>
Phone No:<input type="text" name="phoneno"/><br/><br/>
<input type="submit" value="Register" />
</form>
</body>
</html>