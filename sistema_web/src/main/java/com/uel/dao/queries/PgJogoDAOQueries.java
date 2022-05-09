package com.uel.dao.queries;

public class PgJogoDAOQueries {

  private PgJogoDAOQueries() {}

  public static final String GET_GENERO_JOGOS_QUERY =
      "SELECT COUNT(genero) qtd_jogos, genero FROM integ_preco.jogo "
          + "WHERE genero IS NOT NULL GROUP BY genero";

  public static final String GET_JOGOS_MAIS_VISITADOS =
      "SELECT jogo.titulo "
          + "FROM integ_preco.jogo, "
          + "(SELECT id_jogo, COUNT(*) c_aval "
          + "FROM integ_preco.avaliacao "
          + "group by id_jogo) aval, "
          + "(SELECT id_jogo, COUNT(*) c_perg "
          + "FROM integ_preco.pergunta_cliente "
          + "group by id_jogo) perg "
          + "WHERE jogo.id_jogo = aval.id_jogo "
          + "AND jogo.id_jogo = perg.id_jogo "
          + "ORDER BY (aval.c_aval + perg.c_perg) DESC "
          + "LIMIT 10";
}
