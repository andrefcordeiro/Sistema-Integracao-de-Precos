package com.uel.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;

public class JogoLojaDTO {

  @NotBlank
  private String nomeLoja;

  private Integer idJogo;

  @NotBlank
  private String titulo;

  private String desenvolvedora;

  @NotBlank
  private String urlCapa;

  private String dataLancamento;

  private String genero;

  private String descricao;

  private String multijogador;

  private String fabricante;

  private String marca;

  @NotBlank
  @SerializedName("vendedora")
  private String nomeVendedor;

  @NotBlank
  @SerializedName("transportadora")
  private String nomeTransportadora;

  private LocalDate dataColeta;

  @NotBlank
  private String preco;

  private String parcelas;

  private BigDecimal mediaAval;

  private List<Avaliacao> avaliacoesClientes;

  private List<PerguntaCliente> perguntasClientes;

  private List<HistJogoOfertado> histJogoOfertadoList;

  public String getNomeLoja() {
    return nomeLoja != null ? nomeLoja.trim() : null;
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public String getNomeVendedor() {
    return nomeVendedor != null ? nomeVendedor.trim() : null;
  }

  public void setNomeVendedor(String nomeVendedor) {
    this.nomeVendedor = nomeVendedor;
  }

  public String getNomeTransportadora() {
    return nomeTransportadora != null ? nomeTransportadora.trim() : null;
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

  public String getPreco() {
    return preco;
  }

  public BigDecimal getPrecoBigDecimal() {

    if (preco == null) {
      return null;
    }
    String p = preco.replace("R$", "").replace(",", ".").trim();
    return new BigDecimal(p);
  }

  public void setPreco(String preco) {
    this.preco = preco;
  }

  public String getParcelas() {
    return parcelas;
  }

  public void setParcelas(String parcelas) {
    this.parcelas = parcelas;
  }

  public BigDecimal getMediaAval() {
    return mediaAval;
  }

  public void setMediaAval(BigDecimal mediaAval) {
    this.mediaAval = mediaAval;
  }

  public Integer getIdJogo() {
    return idJogo;
  }

  public void setIdJogo(Integer idJogo) {
    this.idJogo = idJogo;
  }

  public String getTitulo() {
    return titulo != null ? titulo.trim() : null;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDesenvolvedora() {
    return desenvolvedora != null ? desenvolvedora.trim() : null;
  }

  public void setDesenvolvedora(String desenvolvedora) {
    this.desenvolvedora = desenvolvedora;
  }

  public String getUrlCapa() {
    return urlCapa;
  }

  public void setUrlCapa(String urlCapa) {
    this.urlCapa = urlCapa;
  }

  public String getDataLancamento() {
    return dataLancamento != null ? dataLancamento.trim() : null;
  }

  public void setDataLancamento(String dataLancamento) {
    this.dataLancamento = dataLancamento;
  }

  public String getGenero() {
    return genero != null ? genero.trim() : null;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDescricao() {
    return descricao != null ? descricao.trim() : null;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getMultijogador() {
    return multijogador != null ? multijogador.trim() : null;
  }

  public void setMultijogador(String multijogador) {
    this.multijogador = multijogador;
  }

  public String getFabricante() {
    return fabricante != null ? fabricante.trim() : null;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public String getMarca() {
    return marca != null ? marca.trim() : null;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public List<Avaliacao> getAvaliacoesClientes() {
    return avaliacoesClientes;
  }

  public void setAvaliacoesClientes(List<Avaliacao> avaliacoesClientes) {
    this.avaliacoesClientes = avaliacoesClientes;
  }

  public List<PerguntaCliente> getPerguntasClientes() {
    return perguntasClientes;
  }

  public void setPerguntasClientes(List<PerguntaCliente> perguntasClientes) {
    this.perguntasClientes = perguntasClientes;
  }

  public List<HistJogoOfertado> getHistJogoOfertadoList() {
    return histJogoOfertadoList;
  }

  public void setHistJogoOfertadoList(List<HistJogoOfertado> histJogoOfertadoList) {
    this.histJogoOfertadoList = histJogoOfertadoList;
  }
}
