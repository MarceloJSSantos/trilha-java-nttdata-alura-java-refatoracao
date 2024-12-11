package br.com.alura.command;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.PetService;

import java.io.IOException;

public class ImportaPetsDeUmAbrigoCommand implements Command{
    @Override
    public void execute() {
        var client = new ClientHttpConfiguration();
        var petService = new PetService(client);

        try {
            petService.importaPetsDeUmAbrigo();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
