<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dados do Jogo ${jogo.titulo}</title>
</head>
<body>
<table>
    <tr>
        <td>


            <img src="${jogo.urlCapa}" width="205"; height="250";>
        </td>
        <td>

            <p class="col-2" style="margin: 10px">Nome: ${jogo.titulo}</p>
            <p class="col-2" style="margin: 10px">Desenvolvedora: ${jogo.desenvolvedora}</p>
            <p class="col-2" style="margin: 10px">Lançamento: ${jogo.dataLancamento}</p>
            <p class="col-2" style="margin: 10px">Fabricante: ${jogo.fabricante}</p>
            <p class="col-2" style="margin: 10px">Marca: ${jogo.marca}</p>
            <p class="col-2" style="margin: 10px">Multijogador: ${jogo.multijogador}</p>
            <p class="col-2" style="margin: 10px">Gênero: ${jogo.genero}</p>


            <p>
                <a href="${pageContext.servletContext.contextPath}/consultas/getHistorico?id=${jogo.idJogo}&loja=${loja}">
                    <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar historico de precos</button>
                </a>
            </p>
            <p>
                <a href="${pageContext.servletContext.contextPath}/consultas/getAvaliacoes?id=${jogo.idJogo}&loja=${loja}">
                    <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar Avaliações</button>
                </a>
            </p>
            <p>
                <a href="${pageContext.servletContext.contextPath}/consultas/getPerguntas?id=${jogo.idJogo}&loja=${loja}">
                    <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar Perguntas</button>
                </a>
            </p>
        </td>
        <td>



            <h3>Descrição</h3>

            <p>${jogo.descricao}</p>
        </td>
    </tr>
</table>
</body>
</html>

