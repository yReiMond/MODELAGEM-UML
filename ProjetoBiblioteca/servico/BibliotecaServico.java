package biblioteca.servico;

import biblioteca.modelo.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaServico {
    private List<Livro> acervo = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    // Adicionar livro
    public void adicionarLivro(Livro livro) {
        acervo.add(livro);
    }

    // Empréstimo
    public boolean emprestar(Livro livro, Usuario usuario) {
        long ativos = usuario.getEmprestimos().size();
        if (!livro.isDisponivel() || ativos >= 5) return false;
        Emprestimo emp = new Emprestimo(livro, usuario, LocalDate.now());
        emprestimos.add(emp);
        usuario.getEmprestimos().add(emp);
        livro.setDisponivel(false);
        return true;
    }

    // Devolução
    public double devolver(Emprestimo emp) {
        LocalDate hoje = LocalDate.now();
        long diasAtraso = ChronoUnit.DAYS.between(emp.getDataDevolucaoPrevista(), hoje);
        double multa = diasAtraso > 0 ? diasAtraso * 2.0 : 0.0;
        emp.getLivro().setDisponivel(true);
        emprestimos.remove(emp);
        emp.getUsuario().getEmprestimos().remove(emp);
        return multa;
    }

    // Reserva
    public boolean reservar(Livro livro, Usuario usuario) {
        if (livro.isDisponivel()) return false;
        Reserva res = new Reserva(livro, usuario, LocalDate.now());
        reservas.add(res);
        usuario.getReservas().add(res);
        return true;
    }

    // Notificar reservas ao devolver
    public void notificarReservas(Livro livro) {
        reservas.stream()
            .filter(r -> r.getLivro().equals(livro))
            .forEach(r -> System.out.printf("Notificar %s: livro '%s' disponível.%n",
                r.getUsuario().getNome(), livro.getTitulo()));
        reservas.removeIf(r -> r.getLivro().equals(livro));
    }

    // Renovação
    public boolean renovar(Emprestimo emp) {
        boolean temReserva = reservas.stream()
            .anyMatch(r -> r.getLivro().equals(emp.getLivro()));
        if (emp.isRenovado() || temReserva) return false;
        emp.setRenovado(true);
        emp.getDataDevolucaoPrevista().plusDays(7);
        return true;
    }

    // Busca
    public List<Livro> buscar(String termo) {
        return acervo.stream()
            .filter(l -> l.getTitulo().contains(termo)
                      || l.getAutor().contains(termo)
                      || l.getCategoria().contains(termo))
            .collect(Collectors.toList());
    }

    // Relatório mensal
    public void relatorioMensal() {
        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        long totalEmp = emprestimos.stream()
            .filter(e -> !e.getDataEmprestimo().isBefore(inicio))
            .count();
        long totalRes = reservas.stream()
            .filter(r -> !r.getDataReserva().isBefore(inicio))
            .count();
        double totalMulta = 0;
        System.out.printf("Relatório %s - Empréstimos: %d, Reservas: %d, Multas: R$ %.2f%n",
            inicio.getMonth(), totalEmp, totalRes, totalMulta);
    }
}
