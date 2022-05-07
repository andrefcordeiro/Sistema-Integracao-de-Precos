package com.uel.dao;

import com.uel.model.Avaliacao;
import com.uel.model.HistJogoOfertado;
import com.uel.model.JogoLojaDTO;
import com.uel.model.OfertaJogo;
import com.uel.model.PerguntaCliente;
import java.sql.SQLException;
import java.util.List;

public interface JogoLojaDAO extends DAO<JogoLojaDTO> {

  List<HistJogoOfertado> getHistoricoJogo(int idJogo, String nomeLoja) throws SQLException;

  List<OfertaJogo> getHistoricoJogoTodasAsLojas(int idJogo) throws SQLException;

  List<PerguntaCliente> getPerguntasOfertaJogo(int idJogo, String nomeLoja) throws SQLException;

  List<Avaliacao> getAvaliacoesOfertaJogo(int idJogo, String nomeLoja) throws SQLException;

  List<JogoLojaDTO> getJogosPorTitulo(String titulo) throws SQLException;

  JogoLojaDTO getDadosUltimoHistJogoLoja(Integer idJogo, String nomeLoja) throws SQLException;

  HistJogoOfertado getMenorPrecoHistoricoJogoLoja(Integer idJogo, String nomeLoja)
      throws SQLException;

  List<JogoLojaDTO> getJogosMaisBemAvaliados() throws SQLException;
}
