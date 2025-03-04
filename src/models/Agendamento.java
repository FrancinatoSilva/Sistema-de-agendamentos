package models;

public class Agendamento {

    private Cliente cliente;
    private String data;
    private String hora;
    private String servico;

    public Agendamento(Cliente cliente, String data, String hora, String servico) {
        this.cliente = cliente;
        this.data = data;
        this.hora = hora;
        this.servico = servico;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public String getData() {
        return data;
    }
    public String getHora() {
        return hora;
    }
    public String getServico() {
        return servico;
    }
}
