package com.uel.model;

import java.util.List;

public class ScriptDTO {

  private Integer num;
  private String nomeLoja;
  private String funcaoScript;
  private String algoritmo;
  private List<VersaoScript> versoesScript;

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getNomeLoja() {
    return nomeLoja;
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public String getFuncaoScript() {
    return funcaoScript;
  }

  public void setFuncaoScript(String funcaoScript) {
    this.funcaoScript = funcaoScript;
  }

  public String getAlgoritmo() {
    return algoritmo;
  }

  public void setAlgoritmo(String algoritmo) {
    this.algoritmo = algoritmo;
  }

  public List<VersaoScript> getVersoesScript() {
    return versoesScript;
  }

  public void setVersoesScript(List<VersaoScript> versoesScript) {
    this.versoesScript = versoesScript;
  }
}