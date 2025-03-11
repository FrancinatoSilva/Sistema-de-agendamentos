package utils;

import models.Cliente;
import models.Agendamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtils {

    public static void salvarClientes(List<Cliente> clientes, String arquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("nome,telefone\n");
            for (Cliente cliente : clientes) {
                writer.write(cliente.getNome() + "," + cliente.getTelefone() + "\n");
            }
        }
    }

    public static List<Cliente> carregarClientes(String arquivo) throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(arquivo);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha;
                reader.readLine();
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(",");
                    if (dados.length == 2) {
                        Cliente cliente = new Cliente(dados[0], dados[1]);
                        clientes.add(cliente);
                    }
                }
            }
        }
        return clientes;
    }

    public static void salvarAgendamentos(List<Agendamento> agendamentos, String arquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("cliente_nome,data,hora,servico\n");
            for (Agendamento agendamento : agendamentos) {
                writer.write(agendamento.getCliente().getNome() + "," +
                        agendamento.getData() + "," +
                        agendamento.getHora() + "," +
                        agendamento.getServico() + "\n");
            }
        }
    }

    public static List<Agendamento> carregarAgendamentos(String arquivo) throws IOException {
        List<Agendamento> agendamentos = new ArrayList<>();
        File file = new File(arquivo);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha;
                reader.readLine();
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(",");
                    if (dados.length == 4) {
                        Cliente cliente = new Cliente(dados[0], "");
                        Agendamento agendamento = new Agendamento(cliente, dados[1], dados[2], dados[3]);
                        agendamentos.add(agendamento);
                    }
                }
            }
        }
        return agendamentos;
    }
}