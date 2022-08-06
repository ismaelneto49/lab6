package sapo.entities;

import java.time.LocalDate;

public class Comentario {

    private String texto;
    private LocalDate data;
    private Pessoa autor;
    private Pessoa destinatario;

    public Comentario(String texto, LocalDate data, Pessoa autor, Pessoa destinatario) {
        this.texto = texto;
        this.data = data;
        this.autor = autor;
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return this.texto + "(" + this.autor.getNome() + ")";
    }
}
