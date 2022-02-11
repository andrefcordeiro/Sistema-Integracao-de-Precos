package com.uel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class JogoLojaDTO {

  private String nomeLoja;
  private Jogo jogo;

  private String nomeVendedor;
  private String nomeTransportadora;

  private LocalDate dataColeta;
  private BigDecimal preco;
  private Integer qtdParcelas;
  private BigDecimal valorParcela;
  private Double mediaAval;

  public String getNomeLoja() {
    return nomeLoja;
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public Jogo getJogo() {
    return jogo;
  }

  public void setJogo(Jogo jogo) {
    this.jogo = jogo;
  }

  public String getNomeVendedor() {
    return nomeVendedor;
  }

  public void setNomeVendedor(String nomeVendedor) {
    this.nomeVendedor = nomeVendedor;
  }

  public String getNomeTransportadora() {
    return nomeTransportadora;
  }

  public void setNomeTransportadora(String nomeTransportadora) {
    this.nomeTransportadora = nomeTransportadora;
  }

  public LocalDate getDataColeta() {
    return dataColeta;
  }

  public void setDataColeta(LocalDate dataColeta) {
    this.dataColeta = dataColeta;
  }

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  public Integer getQtdParcelas() {
    return qtdParcelas;
  }

  public void setQtdParcelas(Integer qtdParcelas) {
    this.qtdParcelas = qtdParcelas;
  }

  public BigDecimal getValorParcela() {
    return valorParcela;
  }

  public void setValorParcela(BigDecimal valorParcela) {
    this.valorParcela = valorParcela;
  }

  public Double getMediaAval() {
    return mediaAval;
  }

  public void setMediaAval(Double mediaAval) {
    this.mediaAval = mediaAval;
  }
}
