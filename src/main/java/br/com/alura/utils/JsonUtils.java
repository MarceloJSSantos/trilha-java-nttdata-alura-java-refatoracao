package br.com.alura.utils;

import com.google.gson.JsonObject;

public class JsonUtils {

    public JsonObject getJsonObject(String nome, String telefone, String email) {
        JsonObject json = new JsonObject();
        json.addProperty("nome", nome);
        json.addProperty("telefone", telefone);
        json.addProperty("email", email);
        return json;
    }

    public JsonObject getJsonObject(String[] campos) {
        String tipo = campos[0];
        String nome = campos[1];
        String raca = campos[2];
        int idade = Integer.parseInt(campos[3]);
        String cor = campos[4];
        Float peso = Float.parseFloat(campos[5]);

        JsonObject json = new JsonObject();
        json.addProperty("tipo", tipo.toUpperCase());
        json.addProperty("nome", nome);
        json.addProperty("raca", raca);
        json.addProperty("idade", idade);
        json.addProperty("cor", cor);
        json.addProperty("peso", peso);
        return json;
    }
}
