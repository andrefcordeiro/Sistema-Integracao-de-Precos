package com.uel.dao;

import com.uel.model.Avaliacao;
import com.uel.model.HistJogoOfertado;
import com.uel.model.Jogo;
import com.uel.model.JogoLojaDTO;
import com.uel.model.OfertaJogo;
import com.uel.model.PerguntaCliente;
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

public class PgJogoLojaDAO implements JogoLojaDAO {

  private final Connection connection;

  private static final String CREATE_JOGO_QUERY =
      "INSERT INTO integ_preco.jogo (titulo, desenvolvedora, data_lancamento, "
          + "url_capa, fabricante, marca, descricao, multijogador, genero) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"
          + "RETURNING id_jogo";

  private static final String GET_JOGO_QUERY = "SELECT * FROM integ_preco.jogo WHERE titulo=?";

  private static final String GET_ATRIBUTOS_JOGO = "SELECT * FROM integ_preco.jogo WHERE id_jogo=?";

  private static final String GET_JOGOS_LOJA = "SELECT * FROM integ_preco.jogo WHERE nome_loja=?";

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

  private static final String GET_ALL_QUESTIONS =
      "SELECT * FROM integ_preco.pergunta_cliente WHERE id_jogo=? AND nome_loja=?";

  private static final String GET_AVALIACOES_JOGO =
      "SELECT * FROM integ_preco.avaliacao WHERE id_jogo=? AND nome_loja=?;";

  private static final String CREATE_AVALIACAO_QUERY =
      "INSERT INTO integ_preco.avaliacao (num_aval, id_jogo, nome_loja, titulo, texto, "
          + "data_realizacao, qtd_estrelas, votos_aval_util, nome_avaliador, pais_avaliador) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String GET_LAST_NUM_AVALIACAO_QUERY =
      "SELECT MAX(num_aval)+1 AS num_aval FROM integ_preco.avaliacao WHERE nome_loja=? "
          + "AND id_jogo=?";

  private static final String CREATE_PERGUNTA_CLIENTE_QUERY =
      "INSERT INTO integ_preco.pergunta_cliente (num_perg, id_jogo, nome_loja, texto_pergunta, "
          + "texto_resposta, votos_pergunta_util, data_resposta, data_pergunta) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String GET_LAST_NUM_PERGUNTA_QUERY =
      "SELECT MAX(num_perg)+1 AS num_perg FROM integ_preco.pergunta_cliente WHERE nome_loja=? "
          + "AND id_jogo=?";

  private static final String GET_JOGOS_POR_TITULO =
      "SELECT * FROM integ_preco.jogo WHERE position(UPPER(?) IN titulo) != 0";

  private static final String GET_OFERTAS_JOGO =
      "SELECT * FROM integ_preco.jogo jogo, integ_preco.oferta_jogo oj "
          + "WHERE oj.id_jogo = ?"
          + "AND oj.id_jogo = jogo.id_jogo";

  private static final String GET_MENOR_PRECO_JOGO =
      "SELECT nome_loja, data_coleta, parcelas, preco "
          + "FROM integ_preco.historico_jogo_ofertado hist_a, "
          + "(SELECT hist.id_jogo, MIN(hist.preco) min_preco "
          + "FROM integ_preco.historico_jogo_ofertado hist "
          + "WHERE hist.id_jogo = ? "
          + "GROUP BY hist.id_jogo) hist_b "
          + "WHERE hist_a.id_jogo = hist_b.id_jogo "
          + "AND hist_a.preco = hist_b.min_preco";

  private static final String GET_MENOR_PRECO_JOGO_LOJA_ULTIMO_HIST =
      "SELECT * "
          + "FROM integ_preco.historico_jogo_ofertado hist, "
          + "integ_preco.jogo jogo, "
          + "integ_preco.oferta_jogo oferta "
          + "WHERE hist.data_coleta = ( "
          + "SELECT MAX(data_coleta) FROM integ_preco.historico_jogo_ofertado "
          + "WHERE hist.id_jogo = id_jogo "
          + "AND hist.nome_loja = nome_loja "
          + ") "
          + "AND hist.id_jogo = jogo.id_jogo "
          + "AND oferta.id_jogo = hist.id_jogo "
          + "AND oferta.nome_loja = hist.nome_loja "
          + "AND hist.nome_loja = ?"
          + "AND hist.id_jogo = ?";

  private static final String GET_MENOR_PRECO_HISTORICO_JOGO_LOJA =
      "SELECT preco, parcelas, data_coleta "
          + "FROM integ_preco.historico_jogo_ofertado hist_a,  "
          + "( "
          + "SELECT hist.nome_loja, hist.id_jogo, MIN(hist.preco) min_preco "
          + "FROM integ_preco.historico_jogo_ofertado hist "
          + "WHERE hist.nome_loja = ? "
          + "AND hist.id_jogo = ? "
          + "GROUP BY hist.nome_loja, hist.id_jogo) hist_b  "
          + "WHERE hist_a.nome_loja = hist_b.nome_loja "
          + "AND hist_a.preco = hist_b.min_preco ";

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

        atualizarJogo(jogoLoja, idJogo, r);
      }

      inserirOfertaJogo(jogoLoja, idJogo);

      inserirHistOfertaJogo(jogoLoja, idJogo);
    }
  }

  private void atualizarJogo(JogoLojaDTO jogo, Integer idJogo, ResultSet r) throws SQLException {

    StringBuilder updateJogoQuery = new StringBuilder("UPDATE integ_preco.jogo SET");

    boolean achouNulo = false;

    if (r.getString("descricao") == null && jogo.getDescricao() != null) {
      updateJogoQuery.append(" descricao= '").append(jogo.getDescricao()).append("',");
      achouNulo = true;
    }
    if (r.getString("desenvolvedora") == null && jogo.getDesenvolvedora() != null) {
      updateJogoQuery.append(" desenvolvedora= '").append(jogo.getDesenvolvedora()).append("',");
      achouNulo = true;
    }
    if (r.getString("data_lancamento") == null && jogo.getDataLancamento() != null) {
      updateJogoQuery.append(" data_lancamento= '").append(jogo.getDataLancamento()).append("',");
      achouNulo = true;
    }
    if (r.getString("fabricante") == null && jogo.getFabricante() != null) {
      updateJogoQuery.append(" fabricante= '").append(jogo.getFabricante()).append("',");
      achouNulo = true;
    }
    if (r.getString("marca") == null && jogo.getMarca() != null) {
      updateJogoQuery.append(" marca= '").append(jogo.getMarca()).append("',");
      achouNulo = true;
    }
    if (r.getString("multijogador") == null && jogo.getMultijogador() != null) {
      updateJogoQuery.append(" multijogador= '").append(jogo.getMultijogador()).append("',");
      achouNulo = true;
    }
    if (r.getString("genero") == null && jogo.getGenero() != null) {
      updateJogoQuery.append(" genero= '").append(jogo.getGenero()).append("',");
      achouNulo = true;
    }

    updateJogoQuery.deleteCharAt(updateJogoQuery.length() - 1); /* excluindo última vírgula */

    if (achouNulo) {
      updateJogoQuery.append(" WHERE id_jogo=' ").append(idJogo).append(" '");

      PreparedStatement st = connection.prepareStatement(updateJogoQuery.toString());
      st.executeUpdate();
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

        inserirAvaliacoes(jogoLoja, idJogo);

        inserirPerguntasCliente(jogoLoja, idJogo);
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
        PreparedStatement stGetLst =
            connection.prepareStatement(GET_LAST_NUM_HIST_OFERTA_JOGO_QUERY);
        PreparedStatement stIns = connection.prepareStatement(CREATE_HIST_OFERTA_JOGO_QUERY); ) {

      stGet.setString(1, jogoLoja.getNomeLoja());
      stGet.setInt(2, idJogo);
      stGet.setDate(3, Date.valueOf(LocalDate.now()));
      stGet.executeQuery();
      ResultSet r = stGet.getResultSet();

      if (!r.next()) {
        /* histórico daquela data ainda não inserido */

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
        stIns.setBigDecimal(5, jogoLoja.getPreco());
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

  private void inserirAvaliacoes(JogoLojaDTO jogoLoja, Integer idJogo) throws SQLException {

    try (PreparedStatement stGet = connection.prepareStatement(GET_LAST_NUM_AVALIACAO_QUERY);
        PreparedStatement stIns = connection.prepareStatement(CREATE_AVALIACAO_QUERY); ) {

      for (Avaliacao aval : jogoLoja.getAvaliacoesClientes()) {

        stGet.setString(1, jogoLoja.getNomeLoja());
        stGet.setInt(2, idJogo);
        stGet.executeQuery();
        ResultSet rGet = stGet.getResultSet();
        rGet.next();

        int numAval = rGet.getInt("num_aval");
        aval.setNumAval(numAval);
        aval.setNomeLoja(jogoLoja.getNomeLoja());
        aval.setIdJogo(idJogo);
        inserirAvaliacao(aval, stIns);
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir avaliacao: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir avaliacao.");
      }
    }
  }

  private void inserirAvaliacao(Avaliacao aval, PreparedStatement stIns) throws SQLException {

    stIns.setInt(1, aval.getNumAval());
    stIns.setInt(2, aval.getIdJogo());
    stIns.setString(3, aval.getNomeLoja());
    stIns.setString(4, aval.getTitulo());
    stIns.setString(5, aval.getTexto());
    stIns.setString(6, aval.getDataRealizacao());
    stIns.setInt(7, aval.getEstrelas());
    stIns.setInt(8, aval.getVotosAvalUtil());
    stIns.setString(9, aval.getNomeAvaliador());
    stIns.setString(10, aval.getPaisAvaliador());

    stIns.executeUpdate();
  }

  private void inserirPerguntasCliente(JogoLojaDTO jogoLoja, Integer idJogo) throws SQLException {

    try (PreparedStatement stGet = connection.prepareStatement(GET_LAST_NUM_PERGUNTA_QUERY);
        PreparedStatement stIns = connection.prepareStatement(CREATE_PERGUNTA_CLIENTE_QUERY); ) {

      for (PerguntaCliente perg : jogoLoja.getPerguntasClientes()) {

        stGet.setString(1, jogoLoja.getNomeLoja());
        stGet.setInt(2, idJogo);
        stGet.executeQuery();
        ResultSet rGet = stGet.getResultSet();
        rGet.next();

        int numPerg = rGet.getInt("num_perg");
        perg.setNum(numPerg);
        perg.setNomeLoja(jogoLoja.getNomeLoja());
        perg.setIdJogo(idJogo);
        inserirPergunta(perg, stIns);
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);

      if (e.getMessage().contains("not-null")) {
        throw new SQLException("Erro ao inserir pergunta_cliente: campo em branco.");

      } else {
        throw new SQLException("Erro ao inserir pergunta_cliente.");
      }
    }
  }

  private void inserirPergunta(PerguntaCliente perg, PreparedStatement stIns) throws SQLException {

    stIns.setInt(1, perg.getNum());
    stIns.setInt(2, perg.getIdJogo());
    stIns.setString(3, perg.getNomeLoja());
    stIns.setString(4, perg.getTextoPergunta());
    stIns.setString(5, perg.getTextoResposta());
    stIns.setInt(6, perg.getVotosPergUtil());
    stIns.setString(7, perg.getDataPergunta());
    stIns.setString(8, perg.getDataResposta());

    stIns.executeUpdate();
  }

  public Jogo getAtributos_jogo(int id_jogo) throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(GET_ATRIBUTOS_JOGO)) {

      statement.setInt(1, id_jogo);

      try (ResultSet result = statement.executeQuery()) {

        if (result.next()) {

          Jogo jogo = new Jogo();

          jogo.setIdJogo(id_jogo);
          jogo.setTitulo(result.getString("titulo"));
          jogo.setDesenvolvedora(result.getString("desenvolvedora"));
          jogo.setUrlCapa(result.getString("capa"));
          //          jogo.setAnoLancamento(result.getInt("ano_lancamento"));
          jogo.setGenero(result.getString("genero"));
          jogo.setDescricao(result.getString("descricao"));
          jogo.setMultijogador(result.getString("multijogador"));
          jogo.setFabricante(result.getString("fabricante"));
          jogo.setMarca(result.getString("marca"));

          return jogo;

        } else {
          throw new SQLException("Erro ao consultar tabela versao_script.");
        }
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  public List<Jogo> getJogos_loja(String nome_loja) throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(GET_JOGOS_LOJA)) {

      statement.setString(1, nome_loja);

      try (ResultSet result = statement.executeQuery()) {

        List<Jogo> listaJogos = new ArrayList<Jogo>();

        while (result.next()) {

          Jogo jogo = new Jogo();

          jogo.setIdJogo(result.getInt("id_jogo"));
          jogo.setTitulo(result.getString("titulo"));
          jogo.setDesenvolvedora(result.getString("desenvolvedora"));
          jogo.setUrlCapa(result.getString("capa"));
          //          jogo.setAnoLancamento(result.getInt("ano_lancamento"));
          jogo.setGenero(result.getString("genero"));
          jogo.setDescricao(result.getString("descricao"));
          jogo.setMultijogador(result.getString("multijogador"));
          jogo.setFabricante(result.getString("fabricante"));
          jogo.setMarca(result.getString("marca"));

          listaJogos.add(jogo);
        }

        return listaJogos;
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  public List<HistJogoOfertado> get_Historico_jogo(int id_jogo, String nome_loja)
      throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(GET_HIST_OFERTA_JOGO_QUERY)) {

      statement.setString(1, nome_loja);
      statement.setInt(2, id_jogo);

      List<HistJogoOfertado> histCompleto = new ArrayList<HistJogoOfertado>();

      try (ResultSet result = statement.executeQuery()) {

        while (result.next()) {

          HistJogoOfertado historico = new HistJogoOfertado();

          historico.setNomeLoja(nome_loja);
          historico.setIdJogo(id_jogo);
          //          historico.setDataColeta( result.getDate("data_coleta"));
          historico.setPreco(result.getBigDecimal("preco"));
          //          historico.setQtdParcelas(result.getInt("qtd_parcelas"));
          historico.setValorParcela(result.getBigDecimal("valor_parcela"));
          historico.setMediaAval(result.getDouble("media_aval"));

          histCompleto.add(historico);
        }

        return histCompleto;
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  public List<PerguntaCliente> getPerguntasOfertaJogo(int idJogo, String nomeLoja)
      throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(GET_ALL_QUESTIONS)) {

      statement.setInt(1, idJogo);
      statement.setString(2, nomeLoja);
      try (ResultSet result = statement.executeQuery()) {

        List<PerguntaCliente> listaPerguntas = new ArrayList<>();

        while (result.next()) {
          PerguntaCliente pergunta = new PerguntaCliente();
          pergunta.setNum(result.getInt("num_perg"));
          pergunta.setIdJogo(idJogo);
          pergunta.setNomeLoja(nomeLoja);
          pergunta.setTextoPergunta(result.getString("texto_pergunta"));
          pergunta.setTextoResposta(result.getString("texto_resposta"));
          pergunta.setVotosPergUtil(result.getInt("votos_pergunta_util"));
          //                    pergunta.setDataPergunta(result.getDate("data_pergunta"));
          //          pergunta.setDataResposta(result.getDate("data_resposta"));

          listaPerguntas.add(pergunta);
        }

        return listaPerguntas;
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  @Override
  public List<Avaliacao> getAvaliacoesOfertaJogo(int idJogo, String nomeLoja) throws SQLException {

    try (PreparedStatement statement = connection.prepareStatement(GET_AVALIACOES_JOGO)) {

      statement.setInt(1, idJogo);
      statement.setString(2, nomeLoja);
      try (ResultSet result = statement.executeQuery()) {

        List<Avaliacao> listaAval = new ArrayList<>();

        while (result.next()) {
          Avaliacao aval = new Avaliacao();

          aval.setTitulo(result.getString("titulo"));
          aval.setIdJogo(result.getInt("id_jogo"));
          aval.setNomeLoja(result.getString("nome_loja"));
          aval.setTexto(result.getString("texto"));
          aval.setDataRealizacao(result.getString("data_realizacao"));
          aval.setEstrelas(result.getInt("qtd_estrelas"));
          aval.setVotosAvalUtil(result.getInt("votos_aval_util"));
          aval.setNomeAvaliador(result.getString("nome_avaliador"));
          aval.setPaisAvaliador(result.getString("pais_avaliador"));

          listaAval.add(aval);
        }

        return listaAval;
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao consultar tabela versao_script.");
    }
  }

  @Override
  public List<JogoLojaDTO> getJogosPorTitulo(String titulo) throws SQLException {

    List<JogoLojaDTO> jogos = new ArrayList<>();

    try (PreparedStatement st1 = connection.prepareStatement(GET_JOGOS_POR_TITULO);
        PreparedStatement st2 = connection.prepareStatement(GET_OFERTAS_JOGO)) {

      st1.setString(1, titulo);
      ResultSet rs1 = st1.executeQuery();

      while (rs1.next()) {
        JogoLojaDTO jogo = new JogoLojaDTO();

        jogo.setIdJogo(rs1.getInt("id_jogo"));
        jogo.setTitulo(rs1.getString("titulo"));
        jogo.setUrlCapa(rs1.getString("url_capa"));

        /* buscando ofertas do jogo */
        st2.setInt(1, jogo.getIdJogo());
        ResultSet rs2 = st2.executeQuery();
        List<OfertaJogo> ofertasJogo = new ArrayList<>();

        while (rs2.next()) {
          OfertaJogo o = new OfertaJogo();
          o.setNomeLoja(rs2.getString("nome_loja"));
          o.setNomeVendedor(rs2.getString("nome_vendedor"));
          o.setNomeTransportadora(rs2.getString("nome_transportadora"));

          ofertasJogo.add(o);
        }
        jogo.setOfertasJogo(ofertasJogo);

        /* buscando menor preço do jogo em todas as lojas */
        HistJogoOfertado h = getMenorPrecoJogo(jogo.getIdJogo());
        jogo.setMenorPrecoJogo(h);

        jogos.add(jogo);
      }

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException("Erro ao listar jogos com nome = " + titulo + ".");
    }

    return jogos;
  }

  private HistJogoOfertado getMenorPrecoJogo(Integer idJogo) throws SQLException {

    try (PreparedStatement st = connection.prepareStatement(GET_MENOR_PRECO_JOGO)) {
      st.setInt(1, idJogo);
      ResultSet rs1 = st.executeQuery();

      HistJogoOfertado hj = new HistJogoOfertado();
      while (rs1.next()) {

        hj.setNomeLoja(rs1.getString("nome_loja"));
        //      hj.setDataColeta(rs1.getDate("data_coleta"));
        hj.setParcelas(rs1.getString("parcelas"));
        hj.setPreco(rs1.getBigDecimal("preco"));
      }
      return hj;
    }
  }

  @Override
  public JogoLojaDTO getDadosUltimoHistJogoLoja(Integer idJogo, String nomeLoja)
      throws SQLException {

    JogoLojaDTO jogo = new JogoLojaDTO();

    try (PreparedStatement st =
        connection.prepareStatement(GET_MENOR_PRECO_JOGO_LOJA_ULTIMO_HIST)) {

      st.setString(1, nomeLoja);
      st.setInt(2, idJogo);
      ResultSet rs = st.executeQuery();
      rs.next();

      jogo.setTitulo(rs.getString("titulo"));
      jogo.setDesenvolvedora(rs.getString("desenvolvedora"));
      jogo.setUrlCapa(rs.getString("url_capa"));
      jogo.setDataLancamento(rs.getString("data_lancamento"));
      jogo.setGenero(rs.getString("genero"));
      jogo.setDescricao(rs.getString("descricao"));
      jogo.setMultijogador(rs.getString("multijogador"));
      jogo.setFabricante(rs.getString("fabricante"));
      jogo.setMarca(rs.getString("marca"));

      jogo.setNomeVendedor(rs.getString("nome_vendedor"));
      jogo.setNomeTransportadora(rs.getString("nome_transportadora"));

      HistJogoOfertado hj = new HistJogoOfertado();
      hj.setNomeLoja(rs.getString("nome_loja"));
      //      hj.setDataColeta(rs.getDate("data_coleta"));
      hj.setParcelas(rs.getString("parcelas"));
      hj.setPreco(rs.getBigDecimal("preco"));

      jogo.setMenorPrecoJogo(hj);

    } catch (SQLException e) {
      Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", e);
      throw new SQLException(
          "Erro ao listar dados do jogo com id = " + idJogo + " na loja " + nomeLoja + ".");
    }

    return jogo;
  }

  @Override
  public HistJogoOfertado getMenorPrecoHistoricoJogoLoja(Integer idJogo, String nomeLoja)
      throws SQLException {

    try (PreparedStatement st = connection.prepareStatement(GET_MENOR_PRECO_HISTORICO_JOGO_LOJA)) {
      st.setString(1, nomeLoja);
      st.setInt(2, idJogo);
      ResultSet rs1 = st.executeQuery();

      HistJogoOfertado hj = new HistJogoOfertado();
      while (rs1.next()) {

        //      hj.setDataColeta(rs1.getDate("data_coleta"));
        hj.setParcelas(rs1.getString("parcelas"));
        hj.setPreco(rs1.getBigDecimal("preco"));
      }
      return hj;
    }
  }

  @Override
  public JogoLojaDTO read(Integer id) throws SQLException {
    return null;
  }

  @Override
  public void update(JogoLojaDTO jogoLoja) throws SQLException {}

  @Override
  public void delete(Integer id) throws SQLException {}

  @Override
  public List<JogoLojaDTO> getAll() throws SQLException {
    return null;
  }
}
