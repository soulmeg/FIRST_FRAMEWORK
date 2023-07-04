<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="dataObject.*"%>
<% Vector<Emp> emp = (Vector<Emp>) request.getAttribute("idChoisi");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Liste des employes</h1>
    <%for(Emp e: emp){ %>
        <p>Id : <%=e.getId()%></p>
        <p>Nom : <%=e.getNom()%></p>
        <p>Prenom(s) : <%=e.getPrenoms()%></p>
    <%}%>
</body>
</html>
