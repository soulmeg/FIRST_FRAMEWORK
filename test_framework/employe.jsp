<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="dataObject.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Veuiller choisir un id de personne </h1>
    <form action="hab-emp" method="post">
        <select name="id" id="">
            <% for(int i=1;i<3;i++) { %>
                <option value="<%=i%>" ><%=i%></option>
            <% } %>
        </select>
        <input type="submit" value="Valider">
    </form>
</body>
</html>
