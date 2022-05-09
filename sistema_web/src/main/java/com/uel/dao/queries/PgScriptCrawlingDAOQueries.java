package com.uel.dao.queries;

public class PgScriptCrawlingDAOQueries {

  private PgScriptCrawlingDAOQueries() {}

  public static final String CREATE_SCRIPT_QUERY =
      "INSERT INTO integ_preco.script_crawling (nome_loja, funcao_script) "
          + "VALUES(?, ?) "
          + "RETURNING num";

  public static final String GET_SCRIPT_QUERY =
      "SELECT * FROM integ_preco.script_crawling WHERE nome_loja=? AND funcao_script=?";

  public static final String CREATE_VERSAO_SCRIPT_QUERY =
      "INSERT INTO integ_preco.versao_script (num_versao, num_script, data_utilizacao, algoritmo) "
          + "VALUES(?, ?, ?, ?)";

  public static final String GET_LAST_NUM_VERSAO_QUERY =
      "SELECT MAX(num_versao)+1 AS num_versao FROM integ_preco.versao_script WHERE num_script = ?";

  public static final String GET_SCRIPT_VERSIONS_QUERY =
      "SELECT num_versao, data_utilizacao, algoritmo FROM integ_preco.versao_script WHERE num_script=?";

  public static final String GET_ALGORITMO =
      "SELECT algoritmo FROM integ_preco.versao_script WHERE num_versao=? and num_script=?";

  public static final String GET_LOJA_SCRIPTS =
      "SELECT num, funcao_script FROM integ_preco.script_crawling WHERE nome_loja=?";
}
