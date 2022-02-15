package com.uel.model;

import java.sql.Date;



public class PerguntaCliente {

  private Integer num;

  private Integer idJogo;

  private String nomeLoja;


  private String textoPergunta;

  
  private String textoResposta;

  private Integer votosPergUtil;

  private Date dataPergunta;

  private Date dataResposta;

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

  public String getNomeLoja() {
    return nomeLoja;
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public String getTextoPergunta() {
    return textoPergunta != null ? textoPergunta.trim() : null;
  }

  public void setTextoPergunta(String textoPergunta) {
    this.textoPergunta = textoPergunta;
  }

  public String getTextoResposta() {
    return textoResposta != null ? textoResposta.trim() : null;
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
    return dataPergunta != null ? dataPergunta.trim() : null;
  }

  public void setDataPergunta(Date date) {
    this.dataPergunta = date;
  }

  public String getDataResposta() {
    return dataResposta != null ? dataResposta.trim() : null;
  }

  public void setDataResposta(Date date) {
    this.dataResposta = date;
  }
}
