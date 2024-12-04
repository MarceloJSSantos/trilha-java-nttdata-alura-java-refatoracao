package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PetService {

    private ClientHttpConfiguration client;
    private JsonUtils jsonUtils;

    public PetService(ClientHttpConfiguration client, JsonUtils jsonUtils) {
        this.client = client;
        this.jsonUtils = jsonUtils;
    }

    public void listaPetsDeUmAbrigo() throws IOException, InterruptedException {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        String uri = "http://localhost:8080/abrigos/" +idOuNome +"/pets";
        HttpResponse<String> response = client.disparaRequisicaoGet(uri);
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
        } else {
            String responseBody = response.body();
            JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
            System.out.println("Pets cadastrados:");
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                long id = jsonObject.get("id").getAsLong();
                String tipo = jsonObject.get("tipo").getAsString();
                String nome = jsonObject.get("nome").getAsString();
                String raca = jsonObject.get("raca").getAsString();
                int idade = jsonObject.get("idade").getAsInt();
                System.out.println(id +" - " +tipo +" - " +nome +" - " +raca +" - " +idade +" ano(s)");
            }
        }
    }

    public void importaPetsDeUmAbrigo() throws InterruptedException {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = new Scanner(System.in).nextLine();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                String nome = campos[1];
                JsonObject json = jsonUtils.getJsonObject(campos);

                String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";
                HttpResponse<String> response = client.disparaRequisicaoPost(uri, json);
                int statusCode = response.statusCode();
                String responseBody = response.body();
                if (statusCode == 200) {
                    System.out.println("Pet cadastrado com sucesso: " + nome);
                } else if (statusCode == 404) {
                    System.out.println("Id ou nome do abrigo não encontado!");
                    break;
                } else if (statusCode == 400 || statusCode == 500) {
                    System.out.println("Erro ao cadastrar o pet: " + nome);
                    System.out.println(responseBody);
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " +nomeArquivo);
        }
    }
}
