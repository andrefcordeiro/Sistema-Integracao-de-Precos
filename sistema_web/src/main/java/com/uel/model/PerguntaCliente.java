package com.uel.model;

import java.time.LocalDate;

public class PerguntaCliente {

  private Integer num;

  private Integer idJogo;

  private String textoPergunta;

  private String textoResposta;

  private Integer votosPergUtil;

  private String dataPergunta;

  private String dataResposta;

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Integer getIdJogo() {
    return idJogo;
  }

  public void setIdJogo(Integer idJogo) {
    this.idJogo = idJogo;
  }

  public String getTextoPergunta() {
    return textoPergunta.trim();
  }

  public void setTextoPergunta(String textoPergunta) {
    this.textoPergunta = textoPergunta;
  }

  public String getTextoResposta() {
    return textoResposta.trim();
  }

  public void setTextoResposta(String textoResposta) {
    this.textoResposta = textoResposta;
  }

  public Integer getVotosPergUtil() {
    return votosPergUtil;
  }

  public void setVotosPergUtil(Integer votosPergUtil) {
    this.votosPergUtil = votosPergUtil;
  }

  public String getDataPergunta() {
    return dataPergunta.trim();
  }

  public void setDataPergunta(String dataPergunta) {
    this.dataPergunta = dataPergunta;
  }

  public String getDataResposta() {
    return dataResposta.trim();
  }

  public void setDataResposta(String dataResposta) {
    this.dataResposta = dataResposta;
  }
}
