package com.uel.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class JogoLojaDTO {

  private String nomeLoja;

  private Integer idJogo;

  private String titulo;

  private String desenvolvedora;

  private String urlCapa;

  private String dataLancamento;

  private String genero;

  private String descricao;

  private String multijogador;

  private String fabricante;

  private String marca;

  @SerializedName("vendedora")
  private String nomeVendedor;

  @SerializedName("transportadora")
  private String nomeTransportadora;

  private LocalDate dataColeta;

  private String preco;

  private String parcelas;

  private Double mediaAval;

  private List<Avaliacao> avaliacoesClientes;

  private List<PerguntaCliente> perguntasClientes;

  private List<HistJogoOfertado> histJogoOfertadoList;

  public String getNomeLoja() {
    return nomeLoja.trim();
  }

  public void setNomeLoja(String nomeLoja) {
    this.nomeLoja = nomeLoja;
  }

  public String getNomeVendedor() {
    return nomeVendedor.trim();
  }

  public void setNomeVendedor(String nomeVendedor) {
    this.nomeVendedor = nomeVendedor;
  }

  public String getNomeTransportadora() {
    return nomeTransportadora.trim();
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

  public void setPreco(String preco) {
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

  public Integer getIdJogo() {
    return idJogo;
  }

  public void setIdJogo(Integer idJogo) {
    this.idJogo = idJogo;
  }

  public String getTitulo() {
    return titulo.trim();
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDesenvolvedora() {
    return desenvolvedora.trim();
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
    return dataLancamento.trim();
  }

  public void setDataLancamento(String dataLancamento) {
    this.dataLancamento = dataLancamento;
  }

  public String getGenero() {
    return genero.trim();
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDescricao() {
    return descricao.trim();
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getMultijogador() {
    return multijogador.trim();
  }

  public void setMultijogador(String multijogador) {
    this.multijogador = multijogador;
  }

  public String getFabricante() {
    return fabricante.trim();
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public String getMarca() {
    return marca.trim();
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
