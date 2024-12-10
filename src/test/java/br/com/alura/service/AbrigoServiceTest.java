package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.model.Abrigo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AbrigoServiceTest {

    private final ClientHttpConfiguration client =mock(ClientHttpConfiguration.class);
    private final AbrigoService abrigoService = new AbrigoService(client);
    private final HttpResponse<String> response = mock(HttpResponse.class);

    @Test
    @DisplayName("Deve Retornar a mensagem 'Abrigos cadastrados:' e o primeiro abrigo cadastrado quando houver abrigo cadastrado")
    public void listaAbrigosCenario01() throws IOException, InterruptedException {
        Abrigo abrigoCadastrado = new Abrigo("Teste", "21988887777", "teste@email.com");
        abrigoCadastrado.setId(1L);
        var mensagemEsperada = "Abrigos cadastrados:";
        var abrigoEsperado = "1 - Teste";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        when(client.disparaRequisicaoGet(anyString())).thenReturn(response);
        when(response.body()).thenReturn(abrigoJsonResponse(abrigoCadastrado));

        abrigoService.listaAbrigos();

        String[] lines = baos.toString().split(System.lineSeparator());
        var mensagemRetornada = lines[0];
        var abrigoRetornado = lines[1];

        assertEquals(mensagemEsperada, mensagemRetornada);
        assertEquals(abrigoEsperado, abrigoRetornado);
    }

    @Test
    @DisplayName("Deve Retornar a mensagem 'Não há abrigos cadastrados' quando não houver abrigo cadastrado")
    public void listaAbrigosCenario02() throws IOException, InterruptedException {
        var mensagemEsperada = "Não há abrigos cadastrados";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        when(client.disparaRequisicaoGet(anyString())).thenReturn(response);
        when(response.body()).thenReturn("[]");

        abrigoService.listaAbrigos();

        String[] lines = baos.toString().split(System.lineSeparator());
        var mensagemRetornada = lines[0];

        assertEquals(mensagemEsperada, mensagemRetornada);
    }

    private String abrigoJsonResponse(Abrigo abrigo){
        return """
            [
                {
                    "id": "%s",
                    "nome": "%s",
                    "telefone": "%s",
                    "email": "$s"
                }
            ]
            """.formatted(abrigo.getId(), abrigo.getNome(),
                abrigo.getTelefone(), abrigo.getEmail());
    }

}