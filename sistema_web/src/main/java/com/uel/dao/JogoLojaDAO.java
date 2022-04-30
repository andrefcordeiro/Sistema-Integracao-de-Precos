package com.uel.dao;

import com.uel.model.JogoLojaDTO;
import java.sql.SQLException;
import java.util.List;

public interface JogoLojaDAO extends DAO<JogoLojaDTO> {

  List<JogoLojaDTO> getJogosPorTitulo(String titulo) throws SQLException;
}
