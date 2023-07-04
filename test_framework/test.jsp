<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="dataObject.*"%>
<%
    Emp test = (Emp) request.getAttribute("test");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
</head>
<body>
  <h3>Details du fichier uploader</h3>
    <p>Nom du fichier: <%=test.getBadge().getName()%></p>
    <p>Bytes: <%=test.getBadge().getContent().length%></p>
</body>
</html>