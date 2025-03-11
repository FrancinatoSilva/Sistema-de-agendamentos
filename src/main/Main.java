package main;

import models.Agendamento;
import models.Cliente;
import services.GestorDeAgendamentos;
import services.GestorDeClientes;
import services.GestorDeRegras;
import utils.ArquivoUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestorDeClientes gestorDeClientes = new GestorDeClientes();
        GestorDeAgendamentos gestorDeAgendamentos = new GestorDeAgendamentos();
        GestorDeRegras gestorDeRegras = new GestorDeRegras();


        try {
            List<Cliente> clientesCarregados = ArquivoUtils.carregarClientes("clientes.csv");
            List<Agendamento> agendamentosCarregados = ArquivoUtils.carregarAgendamentos("agendamentos.csv");

            gestorDeClientes.setListaDeClientes(clientesCarregados);
            gestorDeAgendamentos.setListaDeAgendamentos(agendamentosCarregados);

            System.out.println("Dados carregados com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }

        int opcaoDesejada;

        do {
            System.out.println("-------Serviços disponíveis-------");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Agendar Horário");
            System.out.println("4 - Consultar Agendamentos");
            System.out.println("5 - Cancelar Agendamentos");
            System.out.println("0 - Sair");
            System.out.println();
            System.out.print("Opção desejada: ");

            opcaoDesejada = sc.nextInt();
            sc.nextLine();

            switch (opcaoDesejada) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    String nome = sc.nextLine();

                    System.out.print("Telefone do cliente: ");
                    String telefone = sc.nextLine();

                    Cliente novoCliente = new Cliente(nome, telefone);
                    gestorDeClientes.cadastrarCliente(novoCliente);
                    salvarDados(gestorDeClientes, gestorDeAgendamentos);
                    break;

                case 2:
                    gestorDeClientes.listarClientes();
                    System.out.println();
                    break;

                case 3:
                    System.out.println("Escolha um cliente para o agendamento: ");
                    gestorDeClientes.listarClientes();

                    System.out.print("Nome do cliente: ");
                    String nomeCliente = sc.nextLine();

                    if (!gestorDeRegras.clienteExiste(nomeCliente, gestorDeClientes.getListaDeClientes())) {
                        System.out.println("Erro: Cliente não encontrado! Cadastre o cliente antes de agendar.");
                        break;
                    }

                    System.out.print("Data (dd/mm/aaaa): ");
                    String data = sc.nextLine();

                    if (!gestorDeRegras.dataValida(data)) {
                        System.out.println("Erro: Data inválida! Escolha uma data a partir de hoje.");
                        break;
                    }

                    System.out.print("Hora (hh:mm): ");
                    String hora = sc.nextLine();

                    if (gestorDeRegras.horarioOcupado(data, hora, gestorDeAgendamentos.getListaDeAgendamentos())) {
                        System.out.println("Erro: Horário já está ocupado! Escolha outro horário.");
                        break;
                    }

                    System.out.print("Serviço: ");
                    String servico = sc.nextLine();

                    Cliente clienteSelecionado = null;
                    for (Cliente c : gestorDeClientes.getListaDeClientes()) {
                        if (c.getNome().equalsIgnoreCase(nomeCliente)) {
                            clienteSelecionado = c;
                            break;
                        }
                    }

                    if (clienteSelecionado != null) {
                        Agendamento novoAgendamento = new Agendamento(clienteSelecionado, data, hora, servico);
                        gestorDeAgendamentos.agendarHorario(novoAgendamento);
                        salvarDados(gestorDeClientes, gestorDeAgendamentos);
                    }
                    break;

                case 4:
                    gestorDeAgendamentos.listarAgendamentos();
                    System.out.println();
                    break;

                case 5:
                    System.out.print("Informe o nome do cliente para cancelar o agendamento: ");
                    nomeCliente = sc.nextLine();

                    System.out.print("Informe a data do agendamento a cancelar (dd/mm/aaaa): ");
                    String dataAgendamento = sc.nextLine();

                    boolean sucesso = gestorDeRegras.cancelarAgendamento(
                            nomeCliente, dataAgendamento, gestorDeAgendamentos.getListaDeAgendamentos()
                    );

                    if (sucesso) {
                        System.out.println("Agendamento de " + nomeCliente + " foi cancelado.");
                        salvarDados(gestorDeClientes, gestorDeAgendamentos);
                    } else {
                        System.out.println("Nenhum agendamento encontrado para " + nomeCliente + " na data " + dataAgendamento);
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    salvarDados(gestorDeClientes, gestorDeAgendamentos);
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcaoDesejada != 0);

        sc.close();
    }

    private static void salvarDados(GestorDeClientes gestorDeClientes, GestorDeAgendamentos gestorDeAgendamentos) {
        try {
            ArquivoUtils.salvarClientes(gestorDeClientes.getListaDeClientes(), "clientes.csv");
            ArquivoUtils.salvarAgendamentos(gestorDeAgendamentos.getListaDeAgendamentos(), "agendamentos.csv");
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}