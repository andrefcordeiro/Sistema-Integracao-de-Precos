package com.uel.dao;

import com.uel.model.ScriptDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgScriptCrawlingDAO implements ScriptCrawlingDAO {

  private final Connection connection;

  private static final String CREATE_SCRIPT_QUERY =
      "INSERT INTO integ_preco.script_crawling (nome_loja, funcao_script)"
          + "VALUES(?, ?)"
          + "RETURNING num";

  private static final String CREATE_VERSAO_SCRIPT_QUERY =
      "INSERT INTO integ_preco.versao_script (num_versao, num_script, data_utilizacao, algoritmo) "
          + "VALUES(?, ?, ?, ?)";

  private static final String GET_LAST_NUM_VERSAO_QUERY =
      "SELECT MAX(num_versao) FROM integ_preco.versao_script WHERE num_script = ?";

  public PgScriptCrawlingDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(ScriptDTO scriptDTO) throws SQLException {

    Integer numScript = null;
    try (PreparedStatement statement = connection.prepareStatement(CREATE_SCRIPT_QUERY)) {

      statement.setString(1, scriptDTO.getNomeLoja());
      statement.setString(2, scriptDTO.getFuncaoScript());
      statement.executeUpdate();
      ResultSet result = statement.getResultSet();
      numScript = result.getInt("num");

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir script de crawling: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir script de crawling.");
      }
    }

    try (PreparedStatement statement = connection.prepareStatement(CREATE_VERSAO_SCRIPT_QUERY)) {

      Integer numVersao = getNumVersaoScript(numScript);

      statement.setInt(1, numVersao);
      statement.setInt(2, numScript);
      statement.setDate(3, Date.valueOf(LocalDate.now()));
      statement.setString(4, scriptDTO.getAlgoritmo());
      statement.executeUpdate();

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir versão de script de crawling: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir versão de script de crawling.");
      }
    }

  }

  private Integer getNumVersaoScript(Integer numScript) throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(GET_LAST_NUM_VERSAO_QUERY)) {

      statement.setInt(1, numScript);
      try (ResultSet result = statement.executeQuery()) {

        if (result.next()) {
          return result.getInt("num_versao");

        } else {
          throw new SQLException("Erro ao consultar tabela versao_script.");
        }
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  @Override
  public ScriptDTO read(Integer id) throws SQLException {
    return null;
  }

  @Override
  public void update(ScriptDTO scriptDTO) throws SQLException {

  }

  @Override
  public void delete(Integer id) throws SQLException {

  }

  @Override
  public List<ScriptDTO> getAll() throws SQLException {
    return null;
  }
}
