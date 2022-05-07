package com.uel.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.uel.dao.DAO;
import com.uel.dao.factory.DAOFactory;
import com.uel.model.JogoLojaDTO;
import com.uel.model.ScriptCrawling;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(
    name = "CrawlingServlet",
    urlPatterns = {
      "/crawling",
      "/crawling/create",
    })
public class CrawlingServlet extends HttpServlet {

  private static final Integer MAX_FILE_SIZE = 1024 * 1024 * 4;
  private static final Integer MAX_MEM_SIZE = 1024 * 1024 * 4;
  private static final String SAVE_DIR_CRAWLING = "file-upload";

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String servletPath = request.getServletPath();
    HttpSession session = request.getSession();
    RequestDispatcher dispatcher;

    String nomeLoja = request.getParameter("nome_loja");

    String pathOutputJson = null;
    String pathScript = null;
    String funcaoScript = null;

    switch (servletPath) {
      case "/crawling/create":
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MAX_MEM_SIZE);
        factory.setRepository(new File("/tmp"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_FILE_SIZE);

        try {

          List<FileItem> fileItems = upload.parseRequest(request);

          for (FileItem fi : fileItems) {

            if (!fi.isFormField()) {
              /* se não for um form normal */
              String fieldName = fi.getFieldName();
              String fileName = null;
              String appPath = request.getServletContext().getRealPath("/");
              String savePath = null;
              new File(appPath + File.separator + SAVE_DIR_CRAWLING).mkdirs();

              if (fieldName.equals("output_crawling")) {
                /* arq de saída do crawling */
                fileName = "games.json";
                savePath = appPath + File.separator + SAVE_DIR_CRAWLING + File.separator + fileName;
                pathOutputJson = savePath;

              } else if (fieldName.equals("script_crawling")) {
                fileName = "script.py";
                savePath = appPath + File.separator + SAVE_DIR_CRAWLING + File.separator + fileName;
                pathScript = savePath;
              }

              File uploadedFile = new File(savePath);
              fi.write(uploadedFile);

            } else {
              funcaoScript = fi.getString();
            }
          }

          inserirJogos(nomeLoja, pathOutputJson);
          inserirScriptCrawling(nomeLoja, funcaoScript, pathScript);

          dispatcher =
              request.getRequestDispatcher("/view/interface-privada/crawling/sucessCreate.jsp");
          dispatcher.forward(request, response);

        } catch (JsonIOException e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", "Erro ao ler arquivo JSON");
          dispatcher =
              request.getRequestDispatcher("/view/interface-privada/crawling/errorCreate.jsp");
          dispatcher.forward(request, response);

        } catch (ConstraintViolationException | SQLException e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", e.getMessage());
          dispatcher =
              request.getRequestDispatcher("/view/interface-privada/crawling/errorCreate.jsp");
          dispatcher.forward(request, response);

        } catch (Exception e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", "Erro ao fazer upload do arquivo.");
          dispatcher =
              request.getRequestDispatcher("/view/interface-privada/crawling/errorCreate.jsp");
          dispatcher.forward(request, response);
        }

        break;
    }
  }

  private void inserirJogos(String nomeLoja, String pathOutputJson)
      throws SQLException, ConstraintViolationException {

    List<JogoLojaDTO> jogos = lerArquivoJson(pathOutputJson);

    DAO<JogoLojaDTO> dao;
    try (DAOFactory daoFactory = DAOFactory.getInstance()) {
      dao = daoFactory.getJogoLojaDAO();

      for (JogoLojaDTO jogo : jogos) {
        jogo.setTitulo(formatarTituloJogo(jogo.getTitulo()));
        if (jogo.getUrlCapa() == null) {
          jogo.setUrlCapa(
              "https://images.tcdn.com.br/img/img_prod/886766/arq"
                  + "uivo_de_corte_controle_video_game_696921381_1_"
                  + "501b343a3803e46191272feea5ea1cb9.jpg");
        }
        jogo.setNomeLoja(nomeLoja);

        validarJogoLoja(jogo);
        dao.create(jogo);
      }

    } catch (SQLException e) {
      throw new SQLException("Erro ao inserir jogos no banco de dados: " + e.getMessage());

    } catch (ClassNotFoundException | IOException e) {
      throw new SQLException("Erro ao instanciar objeto DAO");
    }
  }

  private void validarJogoLoja(JogoLojaDTO jogo) throws ConstraintViolationException {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<JogoLojaDTO>> violations = validator.validate(jogo);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private List<JogoLojaDTO> lerArquivoJson(String pathOutputJson) {

    try (FileInputStream fis = new FileInputStream(pathOutputJson);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr)) {

      Type listType = new TypeToken<List<JogoLojaDTO>>() {}.getType();
      Gson gson = new GsonBuilder().registerTypeAdapter(listType, new JogoDeserializer()).create();

      return gson.fromJson(br, listType);

    } catch (JsonIOException | IOException e) {
      throw new JsonIOException("Erro ao ler arquivo JSON ");
    }
  }

  private String formatarTituloJogo(String tituloJogo) {

    String[] palavras =
        new String[] {
          "JOGO",
          "GAME",
          "MÍDIA FÍSICA",
          "MIDIA FISICA",
          "PARA PS4",
          "PS4",
          "PARA PS5",
          "PS5",
          "-",
          "PARA PLAYSTATION 4",
          "PLAYSTATION 4",
          "LACRADO",
          "MOSTRUÁRIO",
          "MOSTRUARIO",
          "(PLAYSTATION 4)",
          "()",
          "ORIGINAL",
          "NOVO"
        };

    String result = tituloJogo;

    for (String palavra : palavras) {
      result = result.replace(palavra, "");
    }
    result = result.trim();
    return result;
  }

  private void inserirScriptCrawling(String nomeLoja, String funcaoScript, String pathScript)
      throws SQLException, IOException, ConstraintViolationException {

    ScriptCrawling scriptCrawling = new ScriptCrawling();
    scriptCrawling.setFuncaoScript(funcaoScript);
    scriptCrawling.setNomeLoja(nomeLoja);

    scriptCrawling.setAlgoritmo(lerArquivoScript(pathScript));

    validarScriptCrawling(scriptCrawling);

    try (DAOFactory daoFactory = DAOFactory.getInstance()) {

      DAO<ScriptCrawling> dao = daoFactory.getScriptCrawlingDAO();
      dao.create(scriptCrawling);

    } catch (SQLException e) {
      throw new SQLException(
          "Erro ao inserir script de crawling no banco de dados: " + e.getMessage());

    } catch (ClassNotFoundException | IOException e) {
      throw new SQLException("Erro ao instaciar objeto DAO");
    }
  }

  private void validarScriptCrawling(ScriptCrawling scriptCrawling) {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<ScriptCrawling>> violations = validator.validate(scriptCrawling);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private String lerArquivoScript(String pathScript) throws IOException {

    try (FileInputStream fis = new FileInputStream(pathScript);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr)) {

      StringBuilder conteudo = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        conteudo.append(line);
      }
      return conteudo.toString();

    } catch (IOException e) {
      throw new IOException("Erro ao ler script de crawling");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    RequestDispatcher dispatcher;

    switch (request.getServletPath()) {
      case "/crawling/create":
        String nome = request.getParameter("nome_loja");
        request.setAttribute("nome_loja", nome);

        dispatcher = request.getRequestDispatcher("/view/interface-privada/crawling/create.jsp");
        dispatcher.forward(request, response);
        break;
    }
  }
}
