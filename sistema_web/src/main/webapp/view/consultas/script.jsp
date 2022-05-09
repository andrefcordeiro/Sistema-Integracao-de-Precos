<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dados dos Script</title>
</head>
<body>
<table>
    <tr>
        <td>

            <p class="col-2" style="margin: 10px">Numero: ${script.num}</p></td>
        <td>
            <p class="col-2" style="margin: 10px">Função: ${script.funcaoScript}</p>
        </td>


        <td>
            <a href="${pageContext.servletContext.contextPath}/consultas/getVersoes?num_script=${script.num}">
                <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar Versões</button>
            </a>
        </td>
    </tr>
</table>
</body>
</html>


