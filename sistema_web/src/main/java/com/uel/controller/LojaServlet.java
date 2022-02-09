package com.uel.controller;

import com.uel.dao.DAO;
import com.uel.dao.DAOFactory;
import com.uel.model.Loja;
import java.io.*;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "lojaServlet",
    urlPatterns = {
        "/loja",
        "/loja/create",
        "/loja/get",
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

      case "/loja/get":
        break;
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");

    RequestDispatcher dispatcher;

    switch (request.getServletPath()) {
      case "/loja/create":
        dispatcher = request.getRequestDispatcher("/view/loja/create.jsp");
        dispatcher.forward(request, response);
        break;

      case "/loja/get":
        dispatcher = request.getRequestDispatcher("/view/loja/get.jsp");
        dispatcher.forward(request, response);
        break;
    }
  }

  public void destroy() {
  }
}