<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 02/05/2022
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Estatísticas</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.5.1/chart.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>

    <script type="text/javascript">

      const scales = {
        xAxes: {
          type: 'time',
          time: {
            unit: 'day',
            parser: 'yyyyMMdd'
          }
        }
      }

      function drawGraficos() {

        /* desenha gráfico com histórico de preço na loja selecionada */
        drawHistoricoPrecos()

        /* desenha gráfico com histórico de preço em todas as lojas que ofertam o jogo*/
        drawHistoricoPrecosComparativo()
      }

      function criaDataset(backgroundColor, borderColor, data, label) {
        return {
          backgroundColor: backgroundColor, borderColor: borderColor,
          data: data, label: label
        }
      }

      function drawHistoricoPrecos() {

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
            datasets: [
              criaDataset("#11AEFC", "#11AEFC", precos, "${jogo.nomeLoja}")
            ]
          }
          , options: {scales: scales}
        });
      }

      function drawHistoricoPrecosComparativo() {

        let datasets = []
        let data
        let nomeLoja
        let backgroundColors = ["#FC11F5", "#FC1158", "#43FC11"]
        let bgColor

        <c:forEach items="${historicoLojas}" var="oferta"> /* cada lista de histórico da loja */

        /* setando uma cor específica p/ a loja selecionada */
        nomeLoja = "${oferta.nomeLoja}"
        if ("${oferta.nomeLoja}" === "${jogo.nomeLoja}")
          bgColor = "#11AEFC"
        else
          bgColor = backgroundColors.pop()

        data = []
        <c:forEach items="${oferta.historicos}" var="h"> /* cada histórico da loja */

        data.push({x: "${h.dataColeta}".replaceAll('-', ''), y: ${h.preco}})

        </c:forEach>

        /* linha de histórico de preços da loja */
        datasets.push(criaDataset(bgColor, bgColor, data, nomeLoja))

        </c:forEach>

        new Chart("historico_preco_comp", {
          type: "line", data: {datasets: datasets}
          , options: {
            scales: scales
          }
        });
      }

    </script>
</head>
<body onload="drawGraficos()">
<div class="d-flex flex-column align-items-center m-5">

    <h2 class="text-dark"> Estatísticas </h2>
    <div class="d-flex mt-5">
        <div class="p-5 border rounded m-2">
            <p>O menor preço histórico deste jogo nesta loja é </p>
            <p style="font-size: 30px; font-weight: bold; color: darkgreen; margin-left: 20px">
                R$ ${menorPrecoHist.preco}
            </p>
            ${menorPrecoHist.parcelas} em <tags:localDate date="${menorPrecoHist.dataColeta}"/>.
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

    <div class="d-flex">
        <div class="d-flex flex-column border rounded align-items-center p-5 m-5">
            <h4 class="text-dark mb-3"> Histórico de preço nesta loja </h4>
            <canvas id="historico_preco" width="400" height="250"></canvas>
        </div>

        <div class="d-flex flex-column border rounded align-items-center p-5 m-5">
            <h4 class="text-dark mb-3"> Comparativo de preços entre as lojas </h4>
            <canvas id="historico_preco_comp" width="400" height="250"></canvas>
        </div>
    </div>

</div>
</body>
</html>
