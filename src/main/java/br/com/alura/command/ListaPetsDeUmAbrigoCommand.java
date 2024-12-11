package br.com.alura.command;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.PetService;

import java.io.IOException;

public class ListaPetsDeUmAbrigoCommand implements Command{
    @Override
    public void execute() {
        var client = new ClientHttpConfiguration();
        var petService = new PetService(client);

        try {
            petService.listaPetsDeUmAbrigo();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
