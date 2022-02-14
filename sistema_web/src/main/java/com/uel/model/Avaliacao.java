package com.uel.model;

import com.google.gson.annotations.SerializedName;

public class Avaliacao {

  private Integer numAval;

  private String titulo;

  private Integer idJogo;

  private String nomeLoja;

  private String texto;

  @SerializedName("data")
  private String dataRealizacao;

  private Integer estrelas;

  private Integer votosAvalUtil;

  private String nomeAvaliador;

  private String paisAvaliador;

  public Integer getNumAval() {
    return numAval;
  }

  public void setNumAval(Integer numAval) {
    this.numAval = numAval;
  }

  public String getTitulo() {
    return titulo != null ? titulo.trim() : null;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Integer getIdJogo() {
    return idJogo;
  }

  public void setIdJogo(Integer idJogo) {
    this.idJogo = idJogo;
  }

  public String getNomeLoja() {
    return nomeLoja != null ? nomeLoja.trim() : null;
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public String getTexto() {
    return texto != null ? texto.trim() : null;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public String getDataRealizacao() {
    return dataRealizacao != null ? dataRealizacao.trim() : null;
  }

  public void setDataRealizacao(String dataRealizacao) {
    this.dataRealizacao = dataRealizacao;
  }

  public Integer getEstrelas() {
    return estrelas;
  }

  public void setEstrelas(Integer estrelas) {
    this.estrelas = estrelas;
  }

  public Integer getVotosAvalUtil() {
    return votosAvalUtil;
  }

  public void setVotosAvalUtil(Integer votosAvalUtil) {
    this.votosAvalUtil = votosAvalUtil;
  }

  public String getNomeAvaliador() {
    return nomeAvaliador != null ? nomeAvaliador.trim() : null;
  }

  public void setNomeAvaliador(String nomeAvaliador) {
    this.nomeAvaliador = nomeAvaliador;
  }

  public String getPaisAvaliador() {
    return paisAvaliador != null ? paisAvaliador.trim() : null;
  }

  public void setPaisAvaliador(String paisAvaliador) {
    this.paisAvaliador = paisAvaliador;
  }
}
