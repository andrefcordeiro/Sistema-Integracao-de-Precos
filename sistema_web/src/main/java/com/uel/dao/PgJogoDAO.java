package com.uel.dao;

import com.uel.dao.queries.PgJogoDAOQueries;
import com.uel.model.GeneroJogo;
import com.uel.model.Jogo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgJogoDAO implements JogoDAO {

  private final Connection connection;

  public PgJogoDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<GeneroJogo> getGeneroJogos() throws SQLException {

    try (PreparedStatement st =
        connection.prepareStatement(PgJogoDAOQueries.GET_GENERO_JOGOS_QUERY)) {

      st.executeQuery();
      ResultSet rs = st.getResultSet();
      List<GeneroJogo> generos = new ArrayList<>();

      while (rs.next()) {

        GeneroJogo g = new GeneroJogo();
        g.setGenero(rs.getString("genero"));
        g.setQtdJogos(rs.getInt("qtd_jogos"));
        generos.add(g);
      }

      return generos;

    } catch (SQLException e) {
      Logger.getLogger(PgJogoDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao listar generos de jogos.");
    }
  }

  @Override
  public void create(Jogo jogo) throws SQLException {}

  @Override
  public Jogo read(Integer id) throws SQLException {
    return null;
  }

  @Override
  public void update(Jogo jogo) throws SQLException {}

  @Override
  public void delete(Integer id) throws SQLException {}

  @Override
  public List<Jogo> getAll() throws SQLException {
    return null;
  }
}
