package com.uel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistJogoOfertado {

  private String nomeLoja;
  private Integer idJogo;
  private LocalDate dataColeta;
  private BigDecimal preco;
  private String parcelas;
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

  public String getParcelas() {
    return parcelas;
  }

  public void setParcelas(String parcelas) {
    this.parcelas = parcelas;
  }

  public Double getMediaAval() {
    return mediaAval;
  }

  public void setMediaAval(Double mediaAval) {
    this.mediaAval = mediaAval;
  }
}
