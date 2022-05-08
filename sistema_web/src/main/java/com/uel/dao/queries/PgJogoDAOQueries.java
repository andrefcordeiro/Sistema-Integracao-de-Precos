package com.uel.dao.queries;

public class PgJogoDAOQueries {

  private PgJogoDAOQueries() {}

  public static final String GET_GENERO_JOGOS_QUERY =
      "SELECT COUNT(genero) qtd_jogos, genero FROM integ_preco.jogo "
          + "WHERE genero IS NOT NULL GROUP BY genero";
}
