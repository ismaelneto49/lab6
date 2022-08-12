package sapo.entities;

public class Aluno implements Funcao {

    private String matricula;
    private int periodo;

    public Aluno(String matricula, int periodo) {
        this.matricula = matricula;
        this.periodo = periodo;
    }

    @Override
    public String gerarDetalhes() {
        return "Aluno - " + this.matricula + " - " + this.periodo + "\n";
    }
}
