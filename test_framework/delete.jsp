<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="dataObject.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete session</title>
</head>

<body>
    <%= request.getSession().getAttribute("megane") %>
    <%= request.getSession().getAttribute("soul") %>
    <%= request.getSession().getAttribute("piso") %>
    <%= request.getSession().getAttribute("juju") %>
    <%= request.getSession().getAttribute("popo") %>
</body>
</html>