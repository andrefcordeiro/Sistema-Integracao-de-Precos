package com.uel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistJogoOfertado {

  private String nomeLoja;
  private Integer idJogo;
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

  public Integer getIdJogo() {
    return idJogo;
  }

  public void setIdJogo(Integer idJogo) {
    this.idJogo = idJogo;
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