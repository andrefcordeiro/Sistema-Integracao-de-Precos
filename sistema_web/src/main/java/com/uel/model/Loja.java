package com.uel.model;

import javax.validation.constraints.NotBlank;

public class Loja {

  @NotBlank
  private String nome;

  @NotBlank
  private String nomeSecao;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNomeSecao() {
    return nomeSecao;
  }

  public void setNomeSecao(String nomeSecao) {
    this.nomeSecao = nomeSecao;
  }
}
