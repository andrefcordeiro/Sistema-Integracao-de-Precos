<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 02/05/2022
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Estatísticas</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.5.1/chart.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>

    <script type="text/javascript">
      function drawCharts() {

        const precos = []; // y
        const datas = []; // x

        <c:forEach items="${jogo.historico}" var="h">
        precos.push(${h.preco})
        datas.push("${h.dataColeta}".replaceAll('-', ''))
        </c:forEach>

        new Chart("historico_preco", {
          type: "line",
          data: {
            labels: datas,
            datasets: [{
              backgroundColor: "rgba(0,0,0,1.0)",
              borderColor: "rgba(0,0,0,0.1)",
              data: precos,
              label: "Preço"
            }]
          }
          , options: {
            scales: {
              xAxes: {
                type: 'time',
                time: {
                  unit: 'day',
                  parser: 'yyyyMMdd'
                }
              }
            }
          }
        });
      }
    </script>
</head>
<body onload="drawCharts()">
<div class="d-flex flex-column align-items-center m-5">

    <h2 class="text-dark"> Estatísticas </h2>
    <div class="d-flex mt-5">
        <div class="p-5 border rounded m-2">
            <p>O menor preço histórico deste jogo nesta loja é </p>
            <p style="font-size: 30px; font-weight: bold; color: darkgreen; margin-left: 20px">
                R$ ${menorPrecoHist.preco}
            </p>
            ${menorPrecoHist.parcelas} em ${menorPrecoHist.dataColeta}.
        </div>
        <div class="border rounded p-5 m-2">
            <p>Média das avaliações </p>
            <div class="d-flex flex-row">
                <p style="font-size: 30px; font-weight: bold; margin-left: 20px">
                    ${mediaAval}
                </p>
                <p class="mt-3 ml-2">estrelas</p>
            </div>

        </div>
    </div>


    <div class="border rounded p-5 m-5">
        <canvas id="historico_preco" width="400" height="250"></canvas>
    </div>

</div>
</body>
</html>
