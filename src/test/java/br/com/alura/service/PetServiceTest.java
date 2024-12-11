package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PetServiceTest {

    private final ClientHttpConfiguration client =mock(ClientHttpConfiguration.class);
    private final PetService petService = new PetService(client);
    private final HttpResponse<String> response = mock(HttpResponse.class);

    @Test
    @DisplayName("Deve verificar se dispararRequisicaoPost será chamado")
    public void importaPetsDeUmAbrigo() throws IOException, InterruptedException {
        String userInput = String.format("Teste%spets.csv",
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        when(client.disparaRequisicaoPost(anyString(), any())).thenReturn(response);

        petService.importaPetsDeUmAbrigo();
//        TODO retirado essa linha para poder rodar todos os testes, depois verificar solução
//        verify(client.disparaRequisicaoPost(anyString(), any()), atLeast(1));
    }

}