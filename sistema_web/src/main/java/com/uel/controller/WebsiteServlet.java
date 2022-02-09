package com.uel.controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "websiteServlet",
    urlPatterns = {
        "/website",
        "/website/create",
        "/website/get",
    })
public class WebsiteServlet extends HttpServlet {

  public void init() {
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    switch (request.getServletPath()) {
      case "/website/create":
        break;

      case "/website/get":
        break;
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");

    RequestDispatcher dispatcher;

    switch (request.getServletPath()) {
      case "/website/create":
        dispatcher = request.getRequestDispatcher("/view/website/create.jsp");
        dispatcher.forward(request, response);
        break;

      case "/website/get":
        dispatcher = request.getRequestDispatcher("/view/website/get.jsp");
        dispatcher.forward(request, response);
        break;
    }
  }

  public void destroy() {
  }
}