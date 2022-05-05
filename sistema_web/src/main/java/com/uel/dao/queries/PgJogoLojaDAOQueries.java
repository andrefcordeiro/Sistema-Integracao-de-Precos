package com.uel.dao.queries;

public class PgJogoLojaDAOQueries {

  private PgJogoLojaDAOQueries() {}

  public static final String CREATE_JOGO_QUERY =
      "INSERT INTO integ_preco.jogo (titulo, desenvolvedora, data_lancamento, "
          + "url_capa, fabricante, marca, descricao, multijogador, genero) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"
          + "RETURNING id_jogo";

  public static final String GET_JOGO_QUERY = "SELECT * FROM integ_preco.jogo WHERE titulo=?";

  public static final String GET_ATRIBUTOS_JOGO = "SELECT * FROM integ_preco.jogo WHERE id_jogo=?";

  public static final String GET_JOGOS_LOJA = "SELECT * FROM integ_preco.jogo WHERE nome_loja=?";

  public static final String CREATE_OFERTA_JOGO_QUERY =
      "INSERT INTO integ_preco.oferta_jogo (nome_loja, id_jogo, nome_vendedor, nome_transportadora) "
          + "VALUES(?, ?, ?, ?)";

  public static final String GET_OFERTA_JOGO_QUERY =
      "SELECT * FROM integ_preco.oferta_jogo WHERE nome_loja=? AND id_jogo=?";

  public static final String CREATE_HIST_OFERTA_JOGO_QUERY =
      "INSERT INTO integ_preco.historico_jogo_ofertado (nome_loja, id_jogo, num, data_coleta, preco, "
          + "parcelas, media_aval) VALUES(?, ?, ?, ?, ?, ?, ?)";

  public static final String GET_LAST_NUM_HIST_OFERTA_JOGO_QUERY =
      "SELECT MAX(num)+1 AS num FROM integ_preco.historico_jogo_ofertado WHERE nome_loja=? AND id_jogo=?";

  public static final String GET_HIST_OFERTA_JOGO_QUERY =
      "SELECT * FROM integ_preco.historico_jogo_ofertado WHERE nome_loja=? AND id_jogo=? ";

  public static final String GET_ALL_QUESTIONS =
      "SELECT * FROM integ_preco.pergunta_cliente WHERE id_jogo=? AND nome_loja=?";

  public static final String GET_AVALIACOES_JOGO =
      "SELECT * FROM integ_preco.avaliacao WHERE id_jogo=? AND nome_loja=?;";

  public static final String CREATE_AVALIACAO_QUERY =
      "INSERT INTO integ_preco.avaliacao (num_aval, id_jogo, nome_loja, titulo, texto, "
          + "data_realizacao, qtd_estrelas, votos_aval_util, nome_avaliador, pais_avaliador) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  public static final String GET_LAST_NUM_AVALIACAO_QUERY =
      "SELECT MAX(num_aval)+1 AS num_aval FROM integ_preco.avaliacao WHERE nome_loja=? "
          + "AND id_jogo=?";

  public static final String CREATE_PERGUNTA_CLIENTE_QUERY =
      "INSERT INTO integ_preco.pergunta_cliente (num_perg, id_jogo, nome_loja, texto_pergunta, "
          + "texto_resposta, votos_pergunta_util, data_resposta, data_pergunta) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

  public static final String GET_LAST_NUM_PERGUNTA_QUERY =
      "SELECT MAX(num_perg)+1 AS num_perg FROM integ_preco.pergunta_cliente WHERE nome_loja=? "
          + "AND id_jogo=?";

  public static final String GET_JOGOS_POR_TITULO =
      "SELECT * FROM integ_preco.jogo WHERE position(UPPER(?) IN titulo) != 0 LIMIT 30";

  public static final String GET_OFERTAS_JOGO =
      "SELECT * FROM integ_preco.jogo jogo, integ_preco.oferta_jogo oj "
          + "WHERE oj.id_jogo = ?"
          + "AND oj.id_jogo = jogo.id_jogo";

  public static final String GET_MENOR_PRECO_JOGO =
      "SELECT hist_a.nome_loja, hist_a.data_coleta,  "
          + "hist_a.parcelas, hist_a.preco, "
          + "oferta.nome_transportadora, oferta.nome_vendedor "
          + " "
          + "FROM integ_preco.historico_jogo_ofertado hist_a,  "
          + "integ_preco.oferta_jogo oferta "
          + "WHERE (hist_a.id_jogo, hist_a.preco) IN "
          + "( "
          + "SELECT hist.id_jogo, MIN(hist.preco) min_preco "
          + "FROM ( "
          + "SELECT hist.id_jogo, hist.preco "
          + "FROM integ_preco.historico_jogo_ofertado hist "
          + "WHERE hist.num = ( "
          + "SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado "
          + "WHERE hist.id_jogo = id_jogo "
          + "AND hist.nome_loja = nome_loja "
          + ") "
          + " AND hist.id_jogo = ?"
          + ") hist "
          + "GROUP BY hist.id_jogo "
          + ") "
          + "AND hist_a.num = ( "
          + "SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado "
          + "WHERE hist_a.id_jogo = id_jogo "
          + "AND hist_a.nome_loja = nome_loja "
          + ") "
          + "AND oferta.id_jogo = hist_a.id_jogo "
          + "AND oferta.nome_loja = hist_a.nome_loja";

  public static final String GET_DADOS_JOGO_LOJA_ULTIMO_HIST =
      "SELECT * "
          + "FROM integ_preco.historico_jogo_ofertado hist, "
          + "integ_preco.jogo jogo, "
          + "integ_preco.oferta_jogo oferta "
          + "WHERE hist.num = ( "
          + "SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado "
          + "WHERE hist.id_jogo = id_jogo "
          + "AND hist.nome_loja = nome_loja "
          + ") "
          + "AND hist.id_jogo = jogo.id_jogo "
          + "AND oferta.id_jogo = hist.id_jogo "
          + "AND oferta.nome_loja = hist.nome_loja "
          + "AND hist.nome_loja = ?"
          + "AND hist.id_jogo = ?";

  public static final String GET_MENOR_PRECO_HISTORICO_JOGO_LOJA =
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

  public static final String GET_JOGOS_MAIS_BEM_AVALIADOS =
      "SELECT jogo.id_jogo, jogo.titulo, TRUNC(media_total.media, 2) media_aval "
          + "FROM integ_preco.jogo, ( "
          + "SELECT id_jogo, AVG(media_loja) media "
          + "FROM ( "
          + "SELECT jogo.id_jogo, loja.nome, AVG(aval.qtd_estrelas) media_loja FROM "
          + "integ_preco.jogo jogo, "
          + "integ_preco.loja loja, "
          + "integ_preco.avaliacao aval "
          + "WHERE jogo.id_jogo = aval.id_jogo "
          + "AND aval.nome_loja = loja.nome "
          + "GROUP BY jogo.id_jogo, loja.nome "
          + ") AS media_aval_lojas "
          + "GROUP BY id_Jogo "
          + "LIMIT 10 "
          + ") media_total "
          + "WHERE media_total.id_jogo = jogo.id_jogo "
          + "ORDER BY media_total.media DESC ";
}
