package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public enum Tipo { ALUNO, PROFESSOR, BIBLIOTECARIO, ADMINISTRADOR }

    private String nome;
    private Tipo tipo;
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    public Usuario(String nome, Tipo tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public Tipo getTipo() { return tipo; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }
    public List<Reserva> getReservas() { return reservas; }
}