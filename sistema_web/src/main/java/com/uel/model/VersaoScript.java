package com.uel.model;

import java.time.LocalDate;

public class VersaoScript {

  private Integer numVersao;
  private String numScript;
  private LocalDate dataUtilizacao;
  private String algoritmo;

  public Integer getNumVersao() {
    return numVersao;
  }

  public void setNumVersao(Integer numVersao) {
    this.numVersao = numVersao;
  }

  public String getNumScript() {
    return numScript;
  }

  public void setNumScript(String numScript) {
    this.numScript = numScript;
  }

  public LocalDate getDataUtilizacao() {
    return dataUtilizacao;
  }

  public void setDataUtilizacao(LocalDate dataUtilizacao) {
    this.dataUtilizacao = dataUtilizacao;
  }

  public String getAlgoritmo() {
    return algoritmo;
  }

  public void setAlgoritmo(String algoritmo) {
    this.algoritmo = algoritmo;
  }
}
