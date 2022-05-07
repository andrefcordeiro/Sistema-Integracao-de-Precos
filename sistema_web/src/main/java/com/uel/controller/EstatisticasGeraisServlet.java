package com.uel.controller;

import com.uel.dao.JogoLojaDAO;
import com.uel.dao.factory.DAOFactory;
import com.uel.model.JogoLojaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
    name = "estatisticasGeraisServlet",
    urlPatterns = {"/estatisticasGerais"})
public class EstatisticasGeraisServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    RequestDispatcher dispatcher;
    HttpSession session = request.getSession();
    JogoLojaDAO dao;

    switch (request.getServletPath()) {
      case "/estatisticasGerais":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          dao = daoFactory.getJogoLojaDAO();

          List<JogoLojaDTO> jogos = dao.getJogosMaisBemAvaliados();
          request.setAttribute("maisBemAvaliados", jogos);

          JogoLojaDTO jogoMaisBarato = dao.getJogoMaisBaratoAtualmente();
          request.setAttribute("jogoMaisBarato", jogoMaisBarato);

          dispatcher =
              request.getRequestDispatcher("/view/interface-publica/estatisticasGerais.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException | IOException e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", e.getMessage());

          dispatcher = request.getRequestDispatcher("/view/interface-publica/main.jsp");
          dispatcher.forward(request, response);
        }
        break;
    }
  }
}
