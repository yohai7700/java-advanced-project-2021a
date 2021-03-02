<%--
  Created by IntelliJ IDEA.
  User: yohaimazuz
  Date: 02/03/2021
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="game" scope="request" type="openu.advanced.java_workshop.model.Game"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${game.name}</title>
</head>
<body>
<h1>${game.name}</h1>
<p>Publisher: ${game.publisher.toString()}</p>
<p>Developer: ${game.developer}</p>
<p>Condition: ${game.condition.toString()}</p>
<p>Release Date: ${game.releaseDate.toString()}</p>
<p>Price: ${game.price}$</p>
<p>${game.stock == 0 ? "None": game.stock} Left.</p>
</body>
</html>
