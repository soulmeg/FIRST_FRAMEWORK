<%-- 
    Document   : index
    Created on : 18 mars 2023, 17:38:11
    Author     : megane
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Enter ur name !</h2>
        <form action="emp-list" method="post">
            <p><input type="text" name="Nom" placeholder="name" required value="Kantoniaina kely"></p>
            <p><input type="text" name="Prenoms" placeholder="first name" required value="Rakoto"></p>
            <p><input type="date" name="DateNaissance" id="" required value="2023-02-02"></p>
            <p><input type="submit" value="valider"/></p>
        </form>
        <p><a href="employe.jsp">Voir les employes</a></p>
        <p><a href="handrana.jsp">Utilisation session</a></p>
        </body>
</html>
