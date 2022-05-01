package com.uel.controller;

import com.uel.dao.JogoLojaDAO;
import com.uel.dao.factory.DAOFactory;
import com.uel.model.Avaliacao;
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
    name = "jogoServlet",
    urlPatterns = {"/jogo", "/jogo/buscarPorTitulo", "/jogo/buscarDadosJogoLoja"})
public class JogoServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    RequestDispatcher dispatcher;
    HttpSession session = request.getSession();
    JogoLojaDAO dao;

    switch (request.getServletPath()) {
      case "jogo":
        break;

      case "/jogo/buscarPorTitulo":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          dao = daoFactory.getJogoLojaDAO();

          String titulo = request.getParameter("jogo_titulo");
          List<JogoLojaDTO> jogos = dao.getJogosPorTitulo(titulo);

          request.setAttribute("jogos", jogos);
          dispatcher = request.getRequestDispatcher("/view/interface-publica/main.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException | IOException e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", e.getMessage());

          dispatcher = request.getRequestDispatcher("/view/interface-publica/main.jsp");
          dispatcher.forward(request, response);
        }
        break;

      case "/jogo/buscarDadosJogoLoja":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          dao = daoFactory.getJogoLojaDAO();

          String nomeLoja = request.getParameter("nome_loja");
          Integer idJogo = Integer.valueOf(request.getParameter("id_jogo"));

          JogoLojaDTO jogo = dao.getDadosUltimoHistJogoLoja(idJogo, nomeLoja);
          jogo.setNomeLoja(nomeLoja);
          jogo.setIdJogo(idJogo);

          List<Avaliacao> avaliacoes = dao.getAvaliacoes(idJogo, nomeLoja);
          jogo.setAvaliacoesClientes(avaliacoes);

          request.setAttribute("jogo", jogo);
          dispatcher = request.getRequestDispatcher("/view/interface-publica/jogoLoja.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException | IOException e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", e.getMessage());

          dispatcher = request.getRequestDispatcher("/view/interface-publica/jogoLoja.jsp");
          dispatcher.forward(request, response);
        }
        break;
    }
  }
}
