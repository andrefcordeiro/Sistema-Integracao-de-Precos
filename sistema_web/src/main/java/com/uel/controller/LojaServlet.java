package com.uel.controller;

import com.uel.dao.DAO;
import com.uel.dao.DAOFactory;
import com.uel.model.Loja;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "lojaServlet",
    urlPatterns = {
        "/loja",
        "/loja/create",
        "/loja/getAll",
    })
public class LojaServlet extends HttpServlet {

  public void init() {
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    DAO<Loja> dao;
    Loja loja = new Loja();
    HttpSession httpSession = request.getSession();
    RequestDispatcher dispatcher;

    switch (request.getServletPath()) {
      case "/loja/create":

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          dao = daoFactory.getLojaDAO();

          loja.setNome(request.getParameter("nome"));
          loja.setNomeSecao(request.getParameter("nome_secao"));

          dao.create(loja);

          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          request.getSession().setAttribute("error", e.getMessage());
          response.sendRedirect(request.getContextPath() + "/loja/create");
        }
        break;
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");

    RequestDispatcher dispatcher;
    DAO<Loja> dao;

    switch (request.getServletPath()) {

      case "/loja/create":
        dispatcher = request.getRequestDispatcher("/view/loja/create.jsp");
        dispatcher.forward(request, response);
        break;

      case "/loja/getAll":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          dao = daoFactory.getLojaDAO();

          List lojas = dao.getAll();

          request.setAttribute("lojas", lojas);
          dispatcher = request.getRequestDispatcher("/view/loja/getAll.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }

        break;
    }
  }

  public void destroy() {
  }
}