/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uel.dao.factory;

import com.uel.dao.JogoDAO;
import com.uel.dao.JogoLojaDAO;
import com.uel.dao.LojaDAO;
import com.uel.dao.PgJogoDAO;
import com.uel.dao.PgJogoLojaDAO;
import com.uel.dao.PgLojaDAO;
import com.uel.dao.PgScriptCrawlingDAO;
import com.uel.dao.ScriptCrawlingDAO;
import java.sql.Connection;

public class PgDAOFactory extends DAOFactory {

  public PgDAOFactory(Connection connection) {
    this.connection = connection;
  }

  @Override
  public LojaDAO getLojaDAO() {
    return new PgLojaDAO(this.connection);
  }

  @Override
  public JogoLojaDAO getJogoLojaDAO() {
    return new PgJogoLojaDAO(this.connection);
  }

  @Override
  public ScriptCrawlingDAO getScriptCrawlingDAO() {
    return new PgScriptCrawlingDAO(this.connection);
  }

  @Override
  public JogoDAO getJogoDAO() {
    return new PgJogoDAO(this.connection);
  }
}
