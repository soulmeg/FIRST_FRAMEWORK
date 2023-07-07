<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="dataObject.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>LOGIN</title>
</head>
<body>
<h1>SE CONNECTER</h1>
<!-- <a href="test-show">Hello Servlet</a> -->
<!-- <a href="test2-insert?ok=ok">Coucou</a> -->
<form method="get" action="test2-login">
    
    <p><input type="text" name="ok" value="megi"></p>
    <!-- admin -->
    <p><input type="text" name="admin" value=""></p>
    <p><input type="submit" value="valider"></p>
</form>
</body>
</html>