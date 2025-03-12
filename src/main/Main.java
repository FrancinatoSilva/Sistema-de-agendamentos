package main;

import models.Agendamento;
import models.Cliente;
import services.GestorDeAgendamentos;
import services.GestorDeClientes;
import services.GestorDeRegras;
import utils.ArquivoUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final GestorDeClientes gestorDeClientes = new GestorDeClientes();
    private static final GestorDeAgendamentos gestorDeAgendamentos = new GestorDeAgendamentos();
    private static final GestorDeRegras gestorDeRegras = new GestorDeRegras();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        carregarDados();
        menuPrincipal();
        sc.close();
    }

    private static void carregarDados() {
        try {
            List<Cliente> clientesCarregados = ArquivoUtils.carregarClientes("clientes.csv");
            List<Agendamento> agendamentosCarregados = ArquivoUtils.carregarAgendamentos("agendamentos.csv");
            gestorDeClientes.setListaDeClientes(clientesCarregados);
            gestorDeAgendamentos.setListaDeAgendamentos(agendamentosCarregados);
            System.out.println("Dados carregados com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private static void salvarDados() {
        try {
            ArquivoUtils.salvarClientes(gestorDeClientes.getListaDeClientes(), "clientes.csv");
            ArquivoUtils.salvarAgendamentos(gestorDeAgendamentos.getListaDeAgendamentos(), "agendamentos.csv");
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void menuPrincipal() {
        int opcao;
        do {
            exibirMenu("------- Sistema de Agendamentos da Barbearia -------",
                    "1 - Cadastrar Cliente",
                    "2 - Listar Clientes",
                    "3 - Agendar Horário",
                    "4 - Consultar Agendamentos",
                    "5 - Cancelar Agendamentos",
                    "6 - Consultar Agenda do Dia",
                    "7 - Remover Cliente",
                    "0 - Sair");
            System.out.print("Opção desejada: ");

            try {
                opcao = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                continue;
            }

            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: agendarHorario(); break;
                case 4: consultarAgendamentos(); break;
                case 5: cancelarAgendamento(); break;
                case 6: consultarAgendaDoDia(); break;
                case 7: removerCliente(); break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    salvarDados();
                    return;
                default:
                    System.out.println("Erro: Opção inválida! Tente novamente.");
            }
            System.out.println("------------------------------------------------");
        } while (true);
    }

    private static void exibirMenu(String... linhas) {
        for (String linha : linhas) {
            System.out.println(linha);
        }
        System.out.println();
    }

    private static void cadastrarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = sc.nextLine();

        System.out.print("Telefone do cliente (ex.: 11987654321): ");
        String telefone = sc.nextLine();
        if (!telefone.matches("\\d{10,11}")) {
            System.out.println("Erro: Telefone deve ter 10 ou 11 dígitos!");
            return;
        }

        Cliente novoCliente = new Cliente(nome, telefone);
        gestorDeClientes.cadastrarCliente(novoCliente);
        salvarDados();
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        gestorDeClientes.listarClientes();
        aguardarVoltar();
    }

    private static void agendarHorario() {
        exibirMenu("Escolha um cliente para o agendamento:");
        gestorDeClientes.listarClientes();

        System.out.print("Nome do cliente: ");
        String nomeCliente = sc.nextLine();
        if (!gestorDeRegras.clienteExiste(nomeCliente, gestorDeClientes.getListaDeClientes())) {
            System.out.println("Erro: Cliente não encontrado! Cadastre o cliente antes de agendar.");
            return;
        }

        System.out.print("Data (dd/mm/aaaa): ");
        String dataStr = sc.nextLine();
        LocalDate data;
        try {
            data = LocalDate.parse(dataStr, DATE_FORMAT);
            if (!gestorDeRegras.dataValida(dataStr)) {
                System.out.println("Erro: Data inválida! Escolha uma data a partir de hoje.");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use dd/mm/aaaa.");
            return;
        }

        System.out.print("Hora (hh:mm): ");
        String horaStr = sc.nextLine();
        LocalTime hora;
        try {
            hora = LocalTime.parse(horaStr, TIME_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de hora inválido! Use hh:mm.");
            return;
        }

        if (gestorDeRegras.horarioOcupado(dataStr, horaStr, gestorDeAgendamentos.getListaDeAgendamentos())) {
            System.out.println("Erro: Horário já está ocupado! Escolha outro horário.");
            return;
        }

        System.out.print("Serviço: ");
        String servico = sc.nextLine();

        Cliente clienteSelecionado = gestorDeClientes.getListaDeClientes().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nomeCliente))
                .findFirst()
                .orElse(null);

        if (clienteSelecionado != null) {
            Agendamento novoAgendamento = new Agendamento(clienteSelecionado, dataStr, horaStr, servico);
            gestorDeAgendamentos.agendarHorario(novoAgendamento);
            salvarDados();
            System.out.println("Horário agendado com sucesso!");
        }
    }

    private static void consultarAgendamentos() {
        gestorDeAgendamentos.listarAgendamentos();
        aguardarVoltar();
    }

    private static void cancelarAgendamento() {
        System.out.print("Nome do cliente: ");
        String nomeCliente = sc.nextLine();

        System.out.print("Data do agendamento (dd/mm/aaaa): ");
        String dataAgendamento = sc.nextLine();

        boolean sucesso = gestorDeRegras.cancelarAgendamento(
                nomeCliente, dataAgendamento, gestorDeAgendamentos.getListaDeAgendamentos()
        );

        if (sucesso) {
            System.out.println("Agendamento de " + nomeCliente + " foi cancelado.");
            salvarDados();
        } else {
            System.out.println("Nenhum agendamento encontrado para " + nomeCliente + " na data " + dataAgendamento);
        }
    }

    private static void consultarAgendaDoDia() {
        LocalDate hoje = LocalDate.now();
        System.out.println("Agenda do dia " + hoje.format(DATE_FORMAT) + ":");
        gestorDeAgendamentos.getListaDeAgendamentos().stream()
                .filter(a -> a.getData().equals(hoje.format(DATE_FORMAT)))
                .forEach(System.out::println);
        aguardarVoltar();
    }

    private static void removerCliente() {
        exibirMenu("Lista de clientes:");
        gestorDeClientes.listarClientes();

        System.out.print("Telefone do cliente a remover (ex.: 11987654321): ");
        String telefone = sc.nextLine();

        Cliente cliente = gestorDeClientes.buscarClientePorTelefone(telefone);
        if (cliente == null) {
            System.out.println("Erro: Cliente não encontrado!");
            return;
        }

        if (gestorDeRegras.clienteTemAgendamentos(telefone, gestorDeAgendamentos.getListaDeAgendamentos())) {
            System.out.println("Erro: Não é possível remover o cliente pois ele possui agendamentos ativos!");
            return;
        }

        if (gestorDeClientes.removerCliente(telefone)) {
            salvarDados();
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Erro: Não foi possível remover o cliente.");
        }
    }

    private static void aguardarVoltar() {
        System.out.println("\n0 - Voltar ao menu");
        System.out.print("Opção: ");
        sc.nextLine();
    }
}