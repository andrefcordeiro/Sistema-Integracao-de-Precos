package com.uel.controller;


import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uel.model.Avaliacao;
import com.uel.model.JogoLojaDTO;
import com.uel.model.PerguntaCliente;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JogoDeserializer implements JsonDeserializer<List<JogoLojaDTO>> {

  @Override
  public List<JogoLojaDTO> deserialize(JsonElement je, Type type,
      JsonDeserializationContext jdc) throws JsonParseException {

    JsonArray jogosArray = je.getAsJsonArray();
    ArrayList<JogoLojaDTO> myList = new ArrayList<>();

    for (JsonElement e : jogosArray) {

      JsonObject jogo = e.getAsJsonObject();

      /* avaliacoes */
      ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
      JsonElement eAval = jogo.get("avaliacoes");
      JsonArray avalArray = eAval.getAsJsonArray();

      for (JsonElement eAv : avalArray) {
        avaliacoes.add(jdc.deserialize(eAv, Avaliacao.class));
      }

      /* perguntas */
      ArrayList<PerguntaCliente> perguntas = new ArrayList<>();
      JsonElement ePerg = jogo.get("perguntas");
      JsonArray pergArray = ePerg.getAsJsonArray();

      for (JsonElement pAv : pergArray) {
        perguntas.add(jdc.deserialize(pAv, PerguntaCliente.class));
      }

      JogoLojaDTO jogoLojaDTO = jdc.deserialize(e, JogoLojaDTO.class);
      jogoLojaDTO.setAvaliacoesClientes(avaliacoes);
      jogoLojaDTO.setPerguntasClientes(perguntas);

      myList.add(jogoLojaDTO);
    }

    return myList;
  }
}
