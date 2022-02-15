package com.uel.model;

import java.sql.Date;
import java.time.LocalDate;

public class VersaoScript {

  private Integer numVersao;
  private String numScript;
  private Date dataUtilizacao;
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

  public Date getDataUtilizacao() {
    return dataUtilizacao;
  }

  public void setDataUtilizacao(Date date) {
    this.dataUtilizacao = date;
  }

  public String getAlgoritmo() {
    return algoritmo;
  }

  public void setAlgoritmo(String algoritmo) {
    this.algoritmo = algoritmo;
  }
}
