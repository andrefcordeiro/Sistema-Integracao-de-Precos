<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Avaliações do Jogo ${jogo.titulo}</title>
</head>
<body>
<table>
    <tr>
        <td>
            <h4>${avaliacao.titulo}</h4>

            <p>${avaliacao.texto}</p>
        </td>

        <td>
            <p class="col-2" style="margin: 10px">Jogo: ${jogo.titulo}</p>
            <p class="col-2" style="margin: 10px">Data: ${avaliacao.dataRealizacao}</p>
            <p class="col-2" style="margin: 10px">Nota: ${avaliacao.estrelas}</p>
            <p class="col-2" style="margin: 10px">Avaliador: ${avaliacao.nomeAvaliador}</p>
            <p class="col-2" style="margin: 10px">País: ${avaliacao.paisAvaliador}</p>
            <p class="col-2" style="margin: 10px">Votos: ${avaliacao.votosAvalUtil}</p>

        </td>



    </tr>
</table>


</body>
</html>


