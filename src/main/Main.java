package main;

import models.Agendamento;
import models.Cliente;
import services.GestorDeAgendamentos;
import services.GestorDeClientes;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestorDeClientes gestorDeClientes = new GestorDeClientes();
        GestorDeAgendamentos gestorDeAgendamentos = new GestorDeAgendamentos();

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
                break;

            case 2:
                gestorDeClientes.listarClientes();
                System.out.println();
                break;

            case 3:
                System.out.println("Escolha um cliente para o agendamento: ");
                gestorDeClientes.listarClientes();

                int indiceCliente = sc.nextInt() - 1;
                sc.nextLine();

                if (indiceCliente < 0 || indiceCliente >= gestorDeClientes.getListaDeClientes().size()) {
                    System.out.println("Cliente inválido!");
                } else {
                    Cliente clienteSelecionado = gestorDeClientes.getListaDeClientes().get(indiceCliente);

                    System.out.print("Data (dd/mm/aaaa): ");
                    String data = sc.nextLine();

                    System.out.print("Hora (hh:mm): ");
                    String hora = sc.nextLine();

                    System.out.print("Serviço: ");
                    String servico = sc.nextLine();

                    Agendamento novoAgendamento = new Agendamento(clienteSelecionado, data, hora, servico);
                    gestorDeAgendamentos.agendarHorario(novoAgendamento);
                }
                break;

            case 4:
                gestorDeAgendamentos.listarAgendamentos();
                System.out.println();
                break;

            case 5:
                System.out.println("Escolha um cliente para cancelar seu agendamento: ");
                gestorDeClientes.listarClientes();

                indiceCliente = sc.nextInt() - 1;
                sc.nextLine();

                if (indiceCliente < 0 || indiceCliente >= gestorDeClientes.getListaDeClientes().size()) {
                    System.out.println("Cliente inválido!");
                } else {
                    String nomeCliente = gestorDeClientes.getListaDeClientes().get(indiceCliente).getNome();

                    boolean removido = false;
                    for (int i = 0; i < gestorDeAgendamentos.getListaDeAgendamentos().size(); i++) {
                        Agendamento agendamento = gestorDeAgendamentos.getListaDeAgendamentos().get(i);

                        if (agendamento.getCliente().getNome().equalsIgnoreCase(nomeCliente)) {
                            gestorDeAgendamentos.getListaDeAgendamentos().remove(i);
                            removido = true;
                            System.out.println("Agendamento de " + nomeCliente + " foi cancelado.");
                            break;
                        }
                    }

                    if (!removido) {
                        System.out.println("Nenhum agendamento encontrado para " + nomeCliente);
                    }
                }


            case 0:
                System.out.println("Encerrando o sistema...");
                break;

            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    } while (opcaoDesejada != 0);

        sc.close();
    }
}
