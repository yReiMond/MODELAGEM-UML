package biblioteca.app;

import biblioteca.modelo.*;
import biblioteca.servico.BibliotecaServico;

public class Main {
    public static void main(String[] args) {
        BibliotecaServico serv = new BibliotecaServico();

        // Cadastro inicial
        Livro l1 = new Livro("Java Básico", "Fulano", "Programação");
        serv.adicionarLivro(l1);
        Usuario u1 = new Usuario("João", Usuario.Tipo.ALUNO);

        // Operações de exemplo
        serv.emprestar(l1, u1);
        double multa = serv.devolver(u1.getEmprestimos().get(0));
        System.out.println("Multa: R$ " + multa);

        serv.reservar(l1, u1);
        serv.devolver(u1.getEmprestimos().get(0));
        serv.notificarReservas(l1);

        serv.relatorioMensal();
    }
}