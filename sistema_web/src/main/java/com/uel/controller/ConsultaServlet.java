package com.uel.controller;

import com.uel.dao.DAO;
import com.uel.dao.factory.DAOFactory;
import com.uel.dao.PgJogoLojaDAO
import com.uel.dao.JogoLojaDAO
import com.uel.model.Avaliacao;
import com.uel.model.Jogo;
import com.uel.model.HistJogoOfertado;
import com.uel.model.PerguntaCliente;
import com.uel.model.ScriptCrawling;
import com.uel.model.VersaoScript;

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



@WebServlet(name = "ConsultaServlet", urlPatterns = {
    "/consultas",
    "/consultas/avaliacao",
    "/consultas/getAvaliacoes",
    "/consultas/getHistorico",
    "/consultas/getJogos",
    "/consultas/getPerguntas",
    "/consultas/getScripts",
    "/consultas/getVersoes",
    "/consultas/historico",
    "/consultas/pergunta",
})
public class ConsultaServlet extends HttpServlet{

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String servletPath = request.getServletPath();
    HttpSession session = request.getSession();
    RequestDispatcher dispatcher;

    switch (servletPath){
        

}

}

protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    
    response.setContentType("text/html");
    RequestDispatcher dispatcher;
    String servletPath = request.getServletPath();

    PgJogoLojaDAO<Jogo> daoJogo;

    PgScriptCrawlingDAO<ScriptCrawling> daoScript;

    
    
    switch (servletPath){
      
      case "/consultas/avaliacao":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoJogo = daoFactory.JogoLojaDAO();

          int id_jogo = request.getParameter("avaliacao.idJogo");

          Jogo jogo = daoJogo.getAtributos_jogo(id_jogo);

          request.setAttribute("jogo", jogo);
          dispatcher = request.getRequestDispatcher("/consultas/avaliacao.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;
    case "/consultas/getAvaliacoes":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoJogo = daoFactory.JogoLojaDAO();

          
          int id_jogo = request.getParameter("id");
          String nome_loja = request.getParameter("loja");
     
          Lista<Avaliacao> avaliacoes = daoJogo.getAvaliacoes(id_jogo, nome_loja);

          request.setAttribute("avaliacoes", avaliacoes);
          
          
          Jogo jogo = daoJogo.getAtributos_jogo(id_jogo);

          request.setAttribute("jogo", jogo);

          dispatcher = request.getRequestDispatcher("/consultas/avaliacao.jsp");
          

          dispatcher.forward(request, response);

          

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;

}

    case "/consultas/getHistorico":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoJogo = daoFactory.JogoLojaDAO();

          
          int id_jogo = request.getParameter("id");
          String nome_loja = request.getParameter("loja");
     
          Lista<HistJogoOfertado> historico = daoJogo.get_Historico_jogo(id_jogo, nome_loja);

          request.setAttribute("historico_preco", historico);
          

          Jogo jogo = daoJogo.getAtributos_jogo(id_jogo);

          request.setAttribute("jogo", jogo);

          dispatcher = request.getRequestDispatcher("/consultas/avaliacao.jsp");
          

          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;
    case "/consultas/getJogos":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoJogo = daoFactory.JogoLojaDAO();

          
          
          String nome_loja = request.getParameter("nome_loja");
     
          Lista<Jogo> listaJogos = daoJogo.getJogos_loja(id_jogo, nome_loja);

          request.setAttribute("jogos", listaJogos);
          dispatcher = request.getRequestDispatcher("/consultas/getJogos.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;



    case "/consultas/getPerguntas":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoJogo = daoFactory.JogoLojaDAO();

          
          int id_jogo = request.getParameter("id");
          String nome_loja = request.getParameter("loja");
     
          Lista<PerguntaCliente> listaPerguntas = daoJogo.getPerguntas_Oferta(id_jogo, nome_loja);

          request.setAttribute("perguntas", listaPerguntas);
          dispatcher = request.getRequestDispatcher("/consultas/getPerguntas.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;

    case "/consultas/getScripts":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoScript = daoFactory.getScriptCrawlingDAO();

          
          
          String nome_loja = request.getParameter("nome_loja");
     
          Lista<ScriptCrawling> listaScripts = daoScript.getScriptLojas(nome_loja);

          request.setAttribute("scripts", listaScripts);
          dispatcher = request.getRequestDispatcher("/consultas/getScripts.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;

    case "/consultas/getVersoes":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoScript = daoFactory.getScriptCrawlingDAO();

          
          int num_script = request.getParameter("num_script");
          
     
          Lista<VersaoScript> listaVersoes = daoScript.getVersoesScript(num_script);

          request.setAttribute("versoes", listaScripts);
          dispatcher = request.getRequestDispatcher("/consultas/getVersoes.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;

    case "/consultas/historico":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoScript = daoFactory.getScriptCrawlingDAO();

          
          int id_jogo = request.getParameter("historico.idJogo");

          Jogo jogo = daoJogo.getAtributos_jogo(id_jogo);

          request.setAttribute("jogo", jogo);
          dispatcher = request.getRequestDispatcher("/consultas/historico.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;

    case "/consultas/pergunta":
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
          daoScript = daoFactory.getScriptCrawlingDAO();

          
          int id_jogo = request.getParameter("pergunta.idJogo");

          Jogo jogo = daoJogo.getAtributos_jogo(id_jogo);

          request.setAttribute("jogo", jogo);
          dispatcher = request.getRequestDispatcher("/consultas/pergunta.jsp");
          dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
          dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
        }
        
        break;

    

}

}


