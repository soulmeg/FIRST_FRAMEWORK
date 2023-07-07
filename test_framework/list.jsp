<%
  String[] test = (String[])request.getAttribute("ls");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
</head>
<body>
    <h2>Bienvenue Admin</h2>
    <% for(String t : test){%>
        <p><%=t%></p>
    <% } %>
    
    <form action="test2-insert" method="post">
        <input type="submit" value="test session">
    </form>
</body>
</html>