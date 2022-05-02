<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 01/05/2022
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Link Seção</title>
</head>
<body>

<a href="${pageContext.request.getParameter('href')}">
    <div class="d-flex flex-column mb-5">
        <img src="https://img.icons8.com/pastel-glyph/64/000000/collapse-arrow.png"/>
        <p class="text-dark"><strong>${pageContext.request.getParameter('nome')}</strong></p>
    </div>
</a>

</body>
</html>
