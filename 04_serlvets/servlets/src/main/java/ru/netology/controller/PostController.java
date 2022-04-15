package ru.netology.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@AllArgsConstructor
@Controller
public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  public void all(HttpServletResponse response) throws IOException {
    Gson gson = new Gson();

    response.setContentType(APPLICATION_JSON);
    response.getWriter().print(gson.toJson(service.all()));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    createResponse(() -> service.getById(id), response);
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    Post post = service.save(gson.fromJson(body, Post.class));

    response.setContentType(APPLICATION_JSON);
    response.getWriter().print(gson.toJson(post));
  }

  public void removeById(long id, HttpServletResponse response) {
    service.removeById(id);
    response.setStatus(HttpServletResponse.SC_OK);
  }

  private <T> void createResponse(ResponseHandler<T> handler, HttpServletResponse response) throws IOException{
    Gson gson = new Gson();

    response.setContentType(APPLICATION_JSON);
    response.getWriter().print(gson.toJson(handler.makeResponse()));
  }

  @FunctionalInterface
  interface ResponseHandler<T>{
    T makeResponse();
  }
}
