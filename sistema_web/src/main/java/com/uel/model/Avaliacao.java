package com.uel.model;


import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

public class Avaliacao {

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

  public String getTitulo() {
    return titulo.trim();
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
    return nomeLoja.trim();
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public String getTexto() {
    return texto.trim();
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public String getDataRealizacao() {
    return dataRealizacao.trim();
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
    return nomeAvaliador.trim();
  }

  public void setNomeAvaliador(String nomeAvaliador) {
    this.nomeAvaliador = nomeAvaliador;
  }

  public String getPaisAvaliador() {
    return paisAvaliador.trim();
  }

  public void setPaisAvaliador(String paisAvaliador) {
    this.paisAvaliador = paisAvaliador;
  }
}
