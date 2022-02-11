/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uel.dao.factory;

import com.uel.dao.LojaDAO;
import com.uel.dao.PgLojaDAO;
import java.sql.Connection;

public class PgDAOFactory extends DAOFactory {

  public PgDAOFactory(Connection connection) {
    this.connection = connection;
  }

  @Override
  public LojaDAO getLojaDAO() {
    return new PgLojaDAO(this.connection);
  }
}
