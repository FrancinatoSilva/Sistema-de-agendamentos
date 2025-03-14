package services;

import models.Cliente;
import java.util.ArrayList;
import java.util.List;

public class GestorDeClientes {

    private List<Cliente> listaDeClientes;

    public GestorDeClientes() {
        this.listaDeClientes = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente cliente) {
        listaDeClientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
        System.out.println();
    }

    public void listarClientes() {
        if (listaDeClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
        } else {
            System.out.println("Lista de Clientes: ");
            for (int i = 0; i < listaDeClientes.size(); i++) {
                Cliente cliente = listaDeClientes.get(i);
                System.out.println((i + 1) + " - Nome: " + cliente.getNome() + " | Telefone: " + cliente.getTelefone());
            }
        }
    }

    public List<Cliente> getListaDeClientes() {
        return listaDeClientes;
    }

    public void setListaDeClientes(List<Cliente> clientes) {
        this.listaDeClientes = clientes;
    }

    public boolean removerCliente(String telefone) {
        return listaDeClientes.removeIf(cliente -> cliente.getTelefone().equals(telefone));
    }

    public Cliente buscarClientePorTelefone(String telefone) {
        return listaDeClientes.stream()
                .filter(c -> c.getTelefone().equals(telefone))
                .findFirst()
                .orElse(null);
    }
}