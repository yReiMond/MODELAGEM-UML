package biblioteca.modelo;

import java.time.LocalDate;

public class Reserva {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataReserva;

    public Reserva(Livro livro, Usuario usuario, LocalDate dataReserva) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataReserva = dataReserva;
    }

    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataReserva() { return dataReserva; }
}