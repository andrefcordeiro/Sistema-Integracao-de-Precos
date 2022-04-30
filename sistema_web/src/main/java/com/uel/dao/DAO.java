package com.uel.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

  void create(T t) throws SQLException;

  T read(Integer id) throws SQLException;

  void update(T t) throws SQLException;

  void delete(Integer id) throws SQLException;

  List<T> getAll() throws SQLException;
}
