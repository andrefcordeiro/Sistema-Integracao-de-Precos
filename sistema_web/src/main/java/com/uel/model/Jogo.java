package com.uel.model;

public class Jogo {

  private Integer idJogo;
  private String titulo;
  private String desenvolvedora;
  private String urlCapa;
  private Integer anoLancamento;
  private String genero;
  private String descricao;
  private String multijogador;
  private String fabricante;
  private String marca;

  public Integer getIdJogo() {
    return idJogo;
  }

  public void setIdJogo(Integer idJogo) {
    this.idJogo = idJogo;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDesenvolvedora() {
    return desenvolvedora;
  }

  public void setDesenvolvedora(String desenvolvedora) {
    this.desenvolvedora = desenvolvedora;
  }

  public String getUrlCapa() {
    return urlCapa;
  }

  public void setUrlCapa(String urlCapa) {
    this.urlCapa = urlCapa;
  }

  public Integer getAnoLancamento() {
    return anoLancamento;
  }

  public void setAnoLancamento(Integer anoLancamento) {
    this.anoLancamento = anoLancamento;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getMultijogador() {
    return multijogador;
  }

  public void setMultijogador(String multijogador) {
    this.multijogador = multijogador;
  }

  public String getFabricante() {
    return fabricante;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }
}
