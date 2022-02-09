package com.uel.dao;

import com.uel.model.Loja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgLojaDAO implements LojaDAO {

  private final Connection connection;

  private static final String CREATE_QUERY =
      "INSERT INTO integ_preco.loja (nome, nome_secao) VALUES(?, ?)";

  private static final String GETALL_QUERY =
      "SELECT * FROM integ_preco.loja";

  public PgLojaDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(Loja loja) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

      statement.setString(1, loja.getNome());
      statement.setString(2, loja.getNomeSecao());

      statement.executeUpdate();

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir loja: campo em branco.");
      } else {
        throw new SQLException("Erro ao inserir loja.");
      }
    }
  }

  @Override
  public Loja read(Integer id) throws SQLException {
    return null;
  }

  @Override
  public void update(Loja loja) throws SQLException {
  }

  @Override
  public void delete(Integer id) throws SQLException {
  }

  @Override
  public List<Loja> getAll() throws SQLException {

    List<Loja> lojas = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(GETALL_QUERY);
        ResultSet resultSet = statement.executeQuery();) {

      while (resultSet.next()) {
        Loja loja = new Loja();
        loja.setNome(resultSet.getString("nome"));
        loja.setNomeSecao(resultSet.getString("nome_secao"));
        lojas.add(loja);
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao listar todas as lojas.");
    }

    return lojas;
  }
}
