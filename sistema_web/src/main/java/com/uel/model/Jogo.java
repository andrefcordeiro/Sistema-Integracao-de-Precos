package com.uel.model;

public class Jogo {

  private Integer id;
  private String titulo;
  private String desenvolvedora;
  private String capa;
  private String ano_lancamento;
  private String genero;
  private String descricao;
  private String multijogador;
  private String fabricante;
  private String marca;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public String getCapa() {
    return capa;
  }

  public void setCapa(String capa) {
    this.capa = capa;
  }

  public String getAno_lancamento() {
    return ano_lancamento;
  }

  public void setAno_lancamento(String ano_lancamento) {
    this.ano_lancamento = ano_lancamento;
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
