<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 10/02/2022
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/view/head.jsp" %>
    <title>Erro ao inserir novos dados</title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div class="d-flex flex-column justify-content-center align-items-center">
    <h1 class="display-3"> Erro ao inserir dados </h1>
    <%=session.getAttribute("error")%>
</div>
</body>
</html>
