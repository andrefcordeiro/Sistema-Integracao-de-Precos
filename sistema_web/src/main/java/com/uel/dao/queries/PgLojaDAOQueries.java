package com.uel.dao.queries;

public class PgLojaDAOQueries {

  private PgLojaDAOQueries() {}

  public static final String CREATE_QUERY =
      "INSERT INTO integ_preco.loja (nome, nome_secao) VALUES(?, ?)";

  public static final String GETALL_QUERY = "SELECT * FROM integ_preco.loja";
}
