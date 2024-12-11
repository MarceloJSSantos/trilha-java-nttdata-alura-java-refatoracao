package br.com.alura.command;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.AbrigoService;

import java.io.IOException;

public class CadastraNovoAbrigoCommand implements Command{
    @Override
    public void execute() {
        var client = new ClientHttpConfiguration();
        var abrigoService = new AbrigoService(client);

        try {
            abrigoService.cadastraNovoAbrigo();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
