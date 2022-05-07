<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 04/05/2022
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Estatísticas gerais</title>
    <%@include file="/view/head.jsp" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.5.1/chart.min.js"></script>

    <script type="text/javascript">
      function drawCharts() {

        const mediaAval = [];
        const titulos = [];

        <c:forEach items="${maisBemAvaliados}" var="jogo">
        mediaAval.push(${jogo.mediaAval})
        titulos.push("${jogo.titulo}")
        </c:forEach>

        new Chart("maisBemAvaliados", {
          type: "bar",
          data: {
            labels: titulos,
            datasets: [{
              backgroundColor: "#F9FC11",
              borderColor: "rgba(0,0,0,0.1)",
              data: mediaAval,
              label: "Estrelas"
            }]
          }
        });
      }

    </script>

</head>
<body onload="drawCharts()">
<jsp:include page="header.jsp"/>

<div class="d-flex flex-column align-items-center p-5">
    <div class="d-flex flex-column align-items-center border rounded p-5 " style="width: 70%">
        <h4> Jogos mais bem avaliados nas lojas </h4>
        <canvas id="maisBemAvaliados" width="800" height="250"></canvas>
    </div>
    <div class="d-flex flex-column align-items-center border rounded p-4 m-5">
        <h4> Jogos mais barato encontrado </h4>
        <c:set var="jogo" value="${jogoMaisBarato}" scope="request"/>
        <jsp:include page="jogo.jsp">
            <jsp:param name="texto_preco"
                       value=" "/>
            <jsp:param name="mostrar_dados_jogo"
                       value="false"/>
        </jsp:include>
    </div>
</div>
</body>
</html>
