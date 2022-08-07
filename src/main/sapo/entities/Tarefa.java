package sapo.entities;

import java.util.Set;

public class Tarefa {
    private String id;
    private String nome;
    private int duracao;
    private Atividade atividade;
    private boolean isConcluida;
    private String[] habilidades;
    private Set<Pessoa> pessoasResponsaveis;


        public Tarefa(String id, String nome, int duracao, Atividade atividade, String[] habilidades) {
            this.id = id;
            this.nome = nome;
            this.duracao = duracao;
            this.atividade = atividade;
            this.isConcluida = false;
            this.habilidades = habilidades;
        }

        public void setNome(String novoNome) {
            this.nome = novoNome;
        }

        public void setHabilidades(String[] novasHabilidades) {
            this.habilidades = novasHabilidades;
        }

        public void concluirTarefa() {
            this.isConcluida = true;
        }

        public void adicionarPessoaResponsavel(Pessoa pessoa) {
            this.pessoasResponsaveis.add(pessoa);
        }

        public void removerPessoaResponsavel(Pessoa pessoa) {
            this.pessoasResponsaveis.remove(pessoa);
        }

        public void adicionarHoras(int horas) {
            this.duracao = this.duracao + horas;
        }

        public void removerHoras(int horas) {
            this.duracao = this.duracao - horas;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.nome + " - " + this.id + "\n");
            builder.append("- " + this.atividade.getNome() + "\n");
            builder.append(String.join(", ", this.habilidades) + "\n");
            builder.append("===" + "\n");
            builder.append("Equipe" + "\n");
            builder.append("Pessoa.toString(to String for listing)" + "\n");
            return builder.toString();
        }

}
