package com.uel.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.uel.model.JogoLojaDTO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "CrawlingServlet", urlPatterns = {
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

            if (!fi.isFormField()) { /* se não for um form normal */
              String fieldName = fi.getFieldName();
              String fileName = null;
              String appPath = request.getServletContext().getRealPath("/");
              String savePath = null;
              new File(appPath + File.separator + SAVE_DIR_CRAWLING).mkdirs();

              if (fieldName.equals("output_crawling")) { /* arq de saída do crawling */
                fileName = "games.json";
                savePath =
                    appPath + File.separator + SAVE_DIR_CRAWLING + File.separator + fileName;
                pathOutputJson = savePath;

              } else if (fieldName.equals("script_crawling")) {
                fileName = "script.py";
                savePath =
                    appPath + File.separator + SAVE_DIR_CRAWLING + File.separator + fileName;
                pathScript = savePath;
              }

              File uploadedFile = new File(savePath);
              fi.write(uploadedFile);

            } else {
              String funcaoScript = fi.getString();
            }
          }

          inserirJogos(nomeLoja, pathOutputJson);
//          inserirScriptCrawling(pathScript);

          dispatcher = request.getRequestDispatcher("/view/crawling/sucessCreate.jsp");
          dispatcher.forward(request, response);

        } catch (JsonIOException e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", "Erro ao ler arquivo JSON");
          dispatcher = request.getRequestDispatcher("/view/crawling/errorCreate.jsp");
          dispatcher.forward(request, response);

        } catch (Exception e) {
          Logger.getLogger(CrawlingServlet.class.getName()).log(Level.SEVERE, "Controller", e);
          session.setAttribute("error", "Erro ao fazer upload do arquivo.");
          dispatcher = request.getRequestDispatcher("/view/crawling/errorCreate.jsp");
          dispatcher.forward(request, response);
        }

        break;
    }
  }

  private void inserirJogos(String nomeLoja, String pathOutputJson) {

    try (FileInputStream fis = new FileInputStream(new File(pathOutputJson));
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr)) {

      Type listType = new TypeToken<List<JogoLojaDTO>>() {
      }.getType();
      Gson gson = new GsonBuilder().registerTypeAdapter(listType, new JogoDeserializer())
          .create();

      List<JogoLojaDTO> jogos = gson.fromJson(br, listType);

      for (
          JogoLojaDTO jogo : jogos) {
        jogo.setTitulo(retirarPalavrasIndesejadasTituloJogo(jogo.getTitulo()));
      }

    } catch (JsonIOException | IOException e) {
      throw new JsonIOException("Erro ao ler arquivo JSON ");
    }
  }

  private String retirarPalavrasIndesejadasTituloJogo(String tituloJogo) {
    String[] palavras = new String[]{
        "JOGO", "MÍDIA FÍSICA", "MIDIA FISICA", "PARA PS4", "PS4", "PARA PS5", "PS5", "-",
        "PARA PLAYSTATION 4", "PLAYSTATION 4", "LACRADO", "MOSTRUARIO", "(PLAYSTATION 4)",
        "()", "ORIGINAL", "NOVO"};

    String result = tituloJogo;

    for (String palavra : palavras) {
      result = result.replace(palavra, "");
    }
    result = result.trim();
    return result;
  }

//  private void inserirScriptCrawling(String pathScript) {
//
//  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    RequestDispatcher dispatcher;

    switch (request.getServletPath()) {
      case "/crawling/create":

        String nome = request.getParameter("nome_loja");
        request.setAttribute("nome_loja", nome);

        dispatcher = request.getRequestDispatcher("/view/crawling/create.jsp");
        dispatcher.forward(request, response);
        break;
    }
  }

}
