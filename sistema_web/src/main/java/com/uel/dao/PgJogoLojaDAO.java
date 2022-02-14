package com.uel.dao;

import com.uel.model.JogoLojaDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgJogoLojaDAO implements JogoLojaDAO {

  private final Connection connection;

  private static final String CREATE_JOGO_QUERY =
      "INSERT INTO integ_preco.jogo (titulo, desenvolvedora, data_lancamento, "
          + "url_capa, fabricante, marca, descricao, multijogador, genero) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"
          + "RETURNING id_jogo";

  private static final String GET_JOGO_QUERY =
      "SELECT id_jogo FROM integ_preco.jogo WHERE titulo=?";

  private static final String CREATE_OFERTA_JOGO_QUERY =
      "INSERT INTO integ_preco.oferta_jogo (nome_loja, id_jogo, nome_vendedor, nome_transportadora) "
          + "VALUES(?, ?, ?, ?)";

  private static final String GET_OFERTA_JOGO_QUERY =
      "SELECT * FROM integ_preco.oferta_jogo WHERE nome_loja=? AND id_jogo=?";

  private static final String CREATE_HIST_OFERTA_JOGO_QUERY =
      "INSERT INTO integ_preco.historico_jogo_ofertado (nome_loja, id_jogo, num, data_coleta, preco, "
          + "parcelas, media_aval) VALUES(?, ?, ?, ?, ?, ?, ?)";

  private static final String GET_LAST_NUM_HIST_OFERTA_JOGO_QUERY =
      "SELECT MAX(num)+1 AS num FROM integ_preco.historico_jogo_ofertado WHERE nome_loja=? AND id_jogo=?";

  private static final String GET_HIST_OFERTA_JOGO_QUERY =
      "SELECT * FROM integ_preco.historico_jogo_ofertado WHERE nome_loja=? AND id_jogo=? "
          + "AND data_coleta=? ";

  public PgJogoLojaDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(JogoLojaDTO jogoLoja) throws SQLException {

    Integer idJogo;
    try (PreparedStatement statement = connection.prepareStatement(GET_JOGO_QUERY)) {
      statement.setString(1, jogoLoja.getTitulo());
      statement.executeQuery();

      ResultSet r = statement.getResultSet();
      if (!r.next()) {
        idJogo = inserirJogo(jogoLoja);

      } else {
        idJogo = r.getInt("id_jogo");
      }

      inserirOfertaJogo(jogoLoja, idJogo);

      inserirHistOfertaJogo(jogoLoja, idJogo);

//      inserirAvaliacoes();

//      inserirPerguntasCliente();
    }

  }

  private Integer inserirJogo(JogoLojaDTO jogo) throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(CREATE_JOGO_QUERY)) {

      statement.setString(1, jogo.getTitulo());
      statement.setString(2, jogo.getDesenvolvedora());
      statement.setString(3, jogo.getDataLancamento());
      statement.setString(4, jogo.getUrlCapa());
      statement.setString(5, jogo.getFabricante());
      statement.setString(6, jogo.getMarca());
      statement.setString(7, jogo.getDescricao());
      statement.setString(8, jogo.getMultijogador());
      statement.setString(9, jogo.getGenero());
      statement.executeQuery();

      ResultSet result = statement.getResultSet();
      result.next();
      return result.getInt("id_jogo");

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir jogo: campo em branco.");

      }
      if (e.getMessage().contains("un_jogo")) {
        throw new SQLException("Erro ao inserir jogo: titulo já existente.");

      } else {
        throw new SQLException("Erro ao inserir jogo.");
      }
    }
  }

  private void inserirOfertaJogo(JogoLojaDTO jogoLoja, Integer idJogo) throws SQLException {

    try (PreparedStatement stGet = connection.prepareStatement(GET_OFERTA_JOGO_QUERY);
        PreparedStatement stIns = connection.prepareStatement(CREATE_OFERTA_JOGO_QUERY)) {

      stGet.setString(1, jogoLoja.getNomeLoja());
      stGet.setInt(2, idJogo);
      stGet.executeQuery();
      ResultSet r = stGet.getResultSet();

      if (!r.next()) {
        stIns.setString(1, jogoLoja.getNomeLoja());
        stIns.setInt(2, idJogo);
        stIns.setString(3, jogoLoja.getNomeVendedor());
        stIns.setString(4, jogoLoja.getNomeTransportadora());

        stIns.executeUpdate();
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir oferta_jogo: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir oferta_jogo.");
      }
    }
  }

  private void inserirHistOfertaJogo(JogoLojaDTO jogoLoja, Integer idJogo) throws SQLException {

    try (PreparedStatement stGet = connection.prepareStatement(GET_HIST_OFERTA_JOGO_QUERY);
        PreparedStatement stGetLst = connection.prepareStatement(
            GET_LAST_NUM_HIST_OFERTA_JOGO_QUERY);
        PreparedStatement stIns = connection.prepareStatement(CREATE_HIST_OFERTA_JOGO_QUERY);) {

      stGet.setString(1, jogoLoja.getNomeLoja());
      stGet.setInt(2, idJogo);
      stGet.setDate(3, Date.valueOf(LocalDate.now()));
      stGet.executeQuery();
      ResultSet r = stGet.getResultSet();

      if (!r.next()) { /* histórico daquela data ainda não inserido */

        /* recebendo o último valor da coluna "num" */
        stGetLst.setString(1, jogoLoja.getNomeLoja());
        stGetLst.setInt(2, idJogo);
        stGetLst.executeQuery();
        ResultSet rGetlst = stGetLst.getResultSet();
        rGetlst.next();
        int num = rGetlst.getInt("num");

        /* inserindo histórico */
        stIns.setString(1, jogoLoja.getNomeLoja());
        stIns.setInt(2, idJogo);
        stIns.setInt(3, num);
        stIns.setDate(4, Date.valueOf(LocalDate.now()));
        stIns.setBigDecimal(5, jogoLoja.getPrecoBigDecimal());
        stIns.setString(6, jogoLoja.getParcelas());
        stIns.setBigDecimal(7, jogoLoja.getMediaAval());

        stIns.executeUpdate();
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir historico_oferta_jogo: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir historico_oferta_jogo.");
      }
    }
  }

  private void inserirAvaliacoes() {

  }

  private void inserirPerguntasCliente() {

  }

  @Override
  public JogoLojaDTO read(Integer id) throws SQLException {
    return null;
  }

  @Override
  public void update(JogoLojaDTO jogoLoja) throws SQLException {

  }

  @Override
  public void delete(Integer id) throws SQLException {

  }

  @Override
  public List<JogoLojaDTO> getAll() throws SQLException {
    return null;
  }
}
