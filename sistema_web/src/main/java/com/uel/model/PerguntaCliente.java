package com.uel.model;

import com.google.gson.annotations.SerializedName;

public class PerguntaCliente {

  private Integer num;

  private Integer idJogo;

  private String nomeLoja;

  @SerializedName("pergunta")
  private String textoPergunta;

  @SerializedName("resposta")
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

  public void setDataPergunta(String dataPergunta) {
    this.dataPergunta = dataPergunta;
  }

  public String getDataResposta() {
    return dataResposta != null ? dataResposta.trim() : null;
  }

  public void setDataResposta(String dataResposta) {
    this.dataResposta = dataResposta;
  }
}
