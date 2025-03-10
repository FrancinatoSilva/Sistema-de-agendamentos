package services;

import models.Agendamento;
import models.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestorDeRegras {

    public boolean clienteExiste(String nome, List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    public  boolean dataValida(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();
        LocalDate dataAgendamento = LocalDate.parse(data, formatter);
        return !dataAgendamento.isBefore(hoje);
    }

    public boolean horarioOcupado(String data, String hora, List<Agendamento> agendamentos) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getData().equals(data) && agendamento.getHora().equals(hora)) {
                return true;
            }
        }
        return false;
    }

    public boolean cancelarAgendamento(String nomeCliente, String data, List<Agendamento> agendamentos) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getCliente().getNome().equalsIgnoreCase(nomeCliente) && agendamento.getData().equals(data)) {
                agendamentos.remove(agendamento);
                return true;
            }
        }
        return false;
    }

}

