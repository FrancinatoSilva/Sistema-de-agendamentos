package services;

import models.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class GestorDeAgendamentos {

    private List<Agendamento> listaDeAgendamentos;

    public GestorDeAgendamentos() {
        this.listaDeAgendamentos = new ArrayList<>();

    }

    public void agendarHorario(Agendamento agendamento) {
        listaDeAgendamentos.add(agendamento);
        System.out.println("Agendamento realizado com sucesso!");
    }

    public void listarAgendamentos() {
        if (listaDeAgendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
        } else {
            System.out.println("\nLista de Agendamentos: ");
            for (int i = 0; i < listaDeAgendamentos.size(); i++) {
                Agendamento agendamento = listaDeAgendamentos.get(i);
                System.out.printf("%d - Cliente: %s | Data: %s | Hora: %s | Serviço: %s\n",
                        (i + 1),
                        agendamento.getCliente().getNome(),
                        agendamento.getData(),
                        agendamento.getHora(),
                        agendamento.getServico()
                );
            }
        }
    }
}
