package com.uel.controller;

import com.uel.dao.DAO;
import com.uel.dao.factory.DAOFactory;
import com.uel.model.Loja;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


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

          validarLoja(loja);

          dao.create(loja);

          dispatcher = request.getRequestDispatcher("/view/interface-privada/loja/sucessCreate.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException | ConstraintViolationException e) {
          httpSession.setAttribute("error", e.getMessage());
          dispatcher = request.getRequestDispatcher("/view/interface-privada/loja/errorCreate.jsp");
          dispatcher.forward(request, response);
        }
        break;
    }
  }

  private void validarLoja(Loja loja) throws ConstraintViolationException {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<Loja>> violations = validator.validate(loja);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
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
        dispatcher = request.getRequestDispatcher("/view/interface-privada/loja/create.jsp");
        dispatcher.forward(request, response);
        break;

      case "/loja/getAll":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          dao = daoFactory.getLojaDAO();

          List lojas = dao.getAll();

          request.setAttribute("lojas", lojas);
          dispatcher = request.getRequestDispatcher("/view/interface-privada/loja/getAll.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/main.jsp");
          dispatcher.forward(request, response);
        }

        break;
    }
  }

  public void destroy() {
  }
}