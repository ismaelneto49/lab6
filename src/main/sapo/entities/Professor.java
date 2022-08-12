package sapo.entities;

public class Professor implements Funcao {

    private String siape;
    private String[] disciplinas;

    public Professor(String siape, String[] disciplinas) {
        this.siape = siape;
        this.disciplinas = disciplinas;
    }

    @Override
    public String gerarDetalhes() {
        return "Professor - " + this.siape + " - " + String.join(", ", this.disciplinas);
    }
}
