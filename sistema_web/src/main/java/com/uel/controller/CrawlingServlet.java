package com.uel.controller;

import java.io.File;
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

              if (fieldName.equals("output_crawling")) { /* arq de saída do crawling */
                fileName = "games.json";

              } else if (fieldName.equals("script_crawling")) {
                fileName = "script.py";
              }

              String appPath = request.getServletContext().getRealPath("/");
              String savePath =
                  appPath + File.separator + SAVE_DIR_CRAWLING + File.separator + fileName;
              File uploadedFile = new File(savePath);
              fi.write(uploadedFile);

            } else {
              String funcaoScript = fi.getString();
            }
          }

          inserirJogos();
          inserirScriptCrawling();

          dispatcher = request.getRequestDispatcher("/view/crawling/sucessCreate.jsp");
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

  private void inserirJogos() {

  }

  private void inserirScriptCrawling() {

  }

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
