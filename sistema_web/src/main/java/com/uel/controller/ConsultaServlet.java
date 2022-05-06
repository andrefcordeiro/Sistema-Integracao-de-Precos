package com.uel.controller;

import com.uel.dao.PgJogoLojaDAO;
import com.uel.dao.PgScriptCrawlingDAO;
import com.uel.dao.factory.DAOFactory;
import com.uel.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;




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



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        RequestDispatcher dispatcher;
        String servletPath = request.getServletPath();

        PgJogoLojaDAO daoJogo;

        PgScriptCrawlingDAO daoScript;



        switch (servletPath){

            case "/consultas/avaliacao":
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    daoJogo = (PgJogoLojaDAO) daoFactory.getJogoLojaDAO();

                    int id_jogo = Integer.parseInt(request.getParameter("avaliacao.idJogo"));

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
                    daoJogo = (PgJogoLojaDAO) daoFactory.getJogoLojaDAO();


                    int id_jogo = Integer.parseInt(request.getParameter("id"));
                    String nome_loja = request.getParameter("loja");

                    List<Avaliacao> avaliacoes = daoJogo.getAvaliacoes(id_jogo, nome_loja);

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



            case "/consultas/getHistorico":
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    daoJogo = (PgJogoLojaDAO) daoFactory.getJogoLojaDAO();


                    int id_jogo = Integer.parseInt(request.getParameter("id"));
                    String nome_loja = request.getParameter("loja");

                    List<HistJogoOfertado> historico = daoJogo.get_Historico_jogo(id_jogo, nome_loja);

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
                    daoJogo = (PgJogoLojaDAO) daoFactory.getJogoLojaDAO();



                    String nome_loja = request.getParameter("nome_loja");

                    List<Jogo> listaJogos = daoJogo.getJogos_loja(nome_loja);

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
                    daoJogo =(PgJogoLojaDAO) daoFactory.getJogoLojaDAO();


                    int id_jogo = Integer.parseInt(request.getParameter("id"));
                    String nome_loja = request.getParameter("loja");

                    List<PerguntaCliente> listaPerguntas = daoJogo.getPerguntas_Oferta(id_jogo, nome_loja);

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
                    daoScript = (PgScriptCrawlingDAO) daoFactory.getScriptCrawlingDAO();



                    String nome_loja = request.getParameter("nome_loja");

                    List<ScriptCrawling> listaScripts = daoScript.getScriptLojas(nome_loja);

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
                    daoScript = (PgScriptCrawlingDAO) daoFactory.getScriptCrawlingDAO();


                    int num_script = Integer.parseInt(request.getParameter("num_script"));


                    List<VersaoScript> listaVersoes = daoScript.getVersoesScript(num_script);


                    request.setAttribute("versoes", listaVersoes);
                    dispatcher = request.getRequestDispatcher("/consultas/getVersoes.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | SQLException e) {
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }

                break;

            case "/consultas/historico":
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    daoJogo = (PgJogoLojaDAO) daoFactory.getJogoLojaDAO();


                    int id_jogo = Integer.parseInt(request.getParameter("historico.idJogo"));

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
                    daoJogo = (PgJogoLojaDAO) daoFactory.getJogoLojaDAO();


                    int id_jogo = Integer.parseInt(request.getParameter("pergunta.idJogo"));

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

}



