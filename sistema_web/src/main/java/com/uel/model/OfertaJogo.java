package com.uel.model;

import com.google.gson.annotations.SerializedName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OfertaJogo {

  @NotBlank private String nomeLoja;

  @NotNull private Integer idJogo;

  @NotBlank
  @SerializedName("vendedora")
  private String nomeVendedor;

  @NotBlank
  @SerializedName("transportadora")
  private String nomeTransportadora;

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
}
