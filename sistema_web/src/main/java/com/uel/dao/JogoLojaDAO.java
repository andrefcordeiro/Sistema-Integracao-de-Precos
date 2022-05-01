package com.uel.dao;

import com.uel.model.Avaliacao;
import com.uel.model.JogoLojaDTO;
import java.sql.SQLException;
import java.util.List;

public interface JogoLojaDAO extends DAO<JogoLojaDTO> {

  List<Avaliacao> getAvaliacoes(int id_jogo, String nome_loja) throws SQLException;

  List<JogoLojaDTO> getJogosPorTitulo(String titulo) throws SQLException;

  JogoLojaDTO getDadosUltimoHistJogoLoja(Integer idJogo, String nomeLoja) throws SQLException;
}
