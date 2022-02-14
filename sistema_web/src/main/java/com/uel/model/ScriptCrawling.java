package com.uel.model;

import java.util.List;
import javax.validation.constraints.NotBlank;

public class ScriptCrawling {

  private Integer num;

  @NotBlank
  private String nomeLoja;

  @NotBlank
  private String funcaoScript;

  @NotBlank
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
