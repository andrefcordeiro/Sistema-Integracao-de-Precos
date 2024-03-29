package com.uel.dao;

import com.uel.dao.queries.PgScriptCrawlingDAOQueries;
import com.uel.model.ScriptCrawling;
import com.uel.model.VersaoScript;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgScriptCrawlingDAO implements ScriptCrawlingDAO {

  private final Connection connection;

  public PgScriptCrawlingDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(ScriptCrawling scriptCrawling) throws SQLException {

    Integer numScript = null;
    try (PreparedStatement stIns =
            connection.prepareStatement(PgScriptCrawlingDAOQueries.CREATE_SCRIPT_QUERY);
        PreparedStatement stGet =
            connection.prepareStatement(PgScriptCrawlingDAOQueries.GET_SCRIPT_QUERY); ) {

      stGet.setString(1, scriptCrawling.getNomeLoja());
      stGet.setString(2, scriptCrawling.getFuncaoScript());
      stGet.executeQuery();
      ResultSet r = stGet.getResultSet();

      if (!r.next()) {
        stIns.setString(1, scriptCrawling.getNomeLoja());
        stIns.setString(2, scriptCrawling.getFuncaoScript());
        stIns.executeQuery();
        ResultSet result = stIns.getResultSet();
        result.next();
        numScript = result.getInt("num");

      } else {
        numScript = r.getInt("num");
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir script de crawling: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir script de crawling.");
      }
    }

    try (PreparedStatement statement =
        connection.prepareStatement(PgScriptCrawlingDAOQueries.CREATE_VERSAO_SCRIPT_QUERY)) {

      Integer numVersao = getNumVersaoScript(numScript);

      statement.setInt(1, numVersao);
      statement.setInt(2, numScript);
      statement.setDate(3, Date.valueOf(LocalDate.now()));
      statement.setString(4, scriptCrawling.getAlgoritmo());
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

    try (PreparedStatement statement =
        connection.prepareStatement(PgScriptCrawlingDAOQueries.GET_LAST_NUM_VERSAO_QUERY)) {

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

  public String getAlgoritmo(int num_versao, int num_script) throws SQLException {

    try (PreparedStatement statement =
        connection.prepareStatement(PgScriptCrawlingDAOQueries.GET_ALGORITMO)) {

      statement.setInt(1, num_versao);
      statement.setInt(2, num_script);

      try (ResultSet result = statement.executeQuery()) {

        if (result.next()) {
          return result.getString("algoritmo");

        } else {
          throw new SQLException("Erro ao consultar tabela versao_script.");
        }
      }
    }
  }

  public List<VersaoScript> getVersoesScript(int num_script) throws SQLException {

    try (PreparedStatement statement =
        connection.prepareStatement(PgScriptCrawlingDAOQueries.GET_SCRIPT_VERSIONS_QUERY)) {

      statement.setInt(1, num_script);
      try (ResultSet result = statement.executeQuery()) {

        List<VersaoScript> listaVersoes = new ArrayList<VersaoScript>();

        while (result.next()) {
          VersaoScript script = new VersaoScript();
          script.setNumVersao(result.getInt("num_versao"));
          script.setNumScript(result.getString("num_script"));
          script.setAlgoritmo(result.getString("algoritmo"));
          //          script.setDataUtilizacao(result.getDate("data_utilizacao"));

          listaVersoes.add(script);
        }

        return listaVersoes;
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  public List<ScriptCrawling> getScriptLojas(String nome_lojas) throws SQLException {

    try (PreparedStatement statement =
        connection.prepareStatement(PgScriptCrawlingDAOQueries.GET_LOJA_SCRIPTS)) {

      statement.setString(1, nome_lojas);
      try (ResultSet result = statement.executeQuery()) {

        List<ScriptCrawling> listaScripts = new ArrayList<ScriptCrawling>();

        while (result.next()) {
          ScriptCrawling script = new ScriptCrawling();
          script.setNum(result.getInt("num"));
          script.setNomeLoja("nome_loja");
          script.setFuncaoScript(result.getString("funcao_script"));

          listaScripts.add(script);
        }

        return listaScripts;
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  @Override
  public ScriptCrawling read(Integer id) throws SQLException {
    return null;
  }

  @Override
  public void update(ScriptCrawling scriptCrawling) throws SQLException {}

  @Override
  public void delete(Integer id) throws SQLException {}

  @Override
  public List<ScriptCrawling> getAll() throws SQLException {
    return null;
  }
}
