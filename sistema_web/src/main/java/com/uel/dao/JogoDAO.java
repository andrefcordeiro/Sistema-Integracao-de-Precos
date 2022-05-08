package com.uel.dao;

import com.uel.model.GeneroJogo;
import com.uel.model.Jogo;
import java.sql.SQLException;
import java.util.List;

public interface JogoDAO extends DAO<Jogo> {

  public List<GeneroJogo> getGeneroJogos() throws SQLException;
}
