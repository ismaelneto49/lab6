package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapo.entities.Pessoa;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PessoaControllerTest {
    private Facade facade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{});
        facade.cadastrarPessoa("111.111.111-11", "Cristovo", new String[]{});

    }

    // ########### Tests for Pessoa (remove later) ###########//

    @Test
    void testCadastrarPessoaCpfInvalido() {
        String nome = "Teobaldo";
        assertThrows(IllegalArgumentException.class, () -> {
            this.facade.cadastrarPessoa("", nome, new String[1]);
        });

        try {
            this.facade.cadastrarPessoa("", nome, new String[1]);
        } catch (IllegalArgumentException iae) {
            assertEquals("Campo cpf não pode ser vazio", iae.getMessage());
        }
    }

    @Test
    void testCadastrarPessoaNomeInvalido() {
        String cpf = "111.111.111-11";
        assertThrows(IllegalArgumentException.class, () -> {
            this.facade.cadastrarPessoa(cpf, "", new String[1]);
        });

        try {
            this.facade.cadastrarPessoa(cpf, "", new String[1]);
        } catch (IllegalArgumentException iae) {
            assertEquals("Campo nome não pode ser vazio", iae.getMessage());
        }
    }

    @Test
    void testRecuperarPessoaCpfExistente() {
        Pessoa pessoa = this.facade.recuperarPessoa("000.000.000-00");
        assertEquals("Irmael – 000.000.000-00\n", pessoa.toString());
    }

    @Test
    void testRecuperarPessoaCpfInexistente() {
        assertThrows(NoSuchElementException.class, () -> {
            this.facade.recuperarPessoa("cpfinexistente");
        });

        try {
            this.facade.recuperarPessoa("cpfinexistente");
        } catch (NoSuchElementException nsee) {
            assertEquals("CPF fornecido não pertence a nenhuma Pessoa", nsee.getMessage());
        }
    }

    @Test
    void testExibirPessoa() {
        String pessoaRepresentacao = this.facade.exibirPessoa("000.000.000-00");
        assertEquals("Irmael – 000.000.000-00\n", pessoaRepresentacao);
    }

    @Test
    void testAlterarNomePessoa() {
        String cpf = "111.111.111-11";
        this.facade.alterarNomePessoa(cpf, "novoNome");
        assertEquals("novoNome", this.facade.recuperarPessoa(cpf).getNome());
    }

    @Test
    void testAlterarHabilidadesPessoa() {
        String[] habilidades = new String[2];
        habilidades[0] = "programar";
        habilidades[1] = "jogar lol";
        String cpf = "000.000.000-00";
        this.facade.alterarHabilidadesPessoa(cpf, habilidades);
        String habilidadesStr = "- " + habilidades[0] + "\n- " + habilidades[1];
        String representacaoPessoa = this.facade.recuperarPessoa(cpf).toString();
        assertTrue(representacaoPessoa.contains(habilidadesStr));
    }

    @Test
    void testRemoverPessoa() {
        String cpf = "111.111.111-11";
        this.facade.removerPessoa(cpf);
        assertThrows(NoSuchElementException.class, () -> {
            this.facade.recuperarPessoa(cpf);
        });
    }

    @Test
    void testAdicionarComentario() {
        String cpfDestinatario = "000.000.000-00";
        String comentario = "Major, tu é o cara";
        this.facade.adicionarComentario(cpfDestinatario, comentario, "111.111.111-11");
        String comentarios = this.facade.recuperarPessoa(cpfDestinatario).listarComentarios();
        assertTrue(comentarios.contains(comentario));
    }

    @Test
    void testListarComentarios() {
        String cpfDestinatario = "000.000.000-00";
        String comentario1 = "Major, tu é o cara";
        String comentario2 = "Major, tu é fei visse";
        this.facade.adicionarComentario(cpfDestinatario, comentario1, "111.111.111-11");
        this.facade.adicionarComentario(cpfDestinatario, comentario2, "111.111.111-11");
        String comentarios = this.facade.recuperarPessoa(cpfDestinatario).listarComentarios();

        String resultadoEsperado = "Irmael – 000.000.000-00\nComentários:\n-- Major, tu é o cara(Cristovo)\n-- Major, tu é fei visse(Cristovo)";

        assertEquals(resultadoEsperado, comentarios);
    }

    @Test
    void testCadastrarAluno() {
        String cpf = "222.222.222-22";
        String matricula = "121110199";
        int periodo = 4;
        this.facade.cadastrarAluno(cpf, "Teobaldo", matricula, periodo, new String[]{"jogar LoL"});
        Pessoa pessoa = this.facade.recuperarPessoa(cpf);
        assertTrue(pessoa.toString().contains("Aluno"));
        assertTrue(pessoa.toString().contains(cpf));
        assertTrue(pessoa.toString().contains(periodo + ""));
        assertTrue(pessoa.toString().contains(matricula));
    }

    @Test
    void testDefinirFuncaoAluno() {
        String cpf = "111.111.111-11";
        String matricula = "121110199";
        int periodo = 4;
        Pessoa p = this.facade.recuperarPessoa(cpf);
        assertFalse(p.toString().contains("Aluno"));
        this.facade.definirFuncaoAluno(cpf, matricula, periodo);
        assertTrue(p.toString().contains("Aluno"));
    }

    @Test
    void testDefinirFuncaoAlunoJaEhAluno() {
        String cpf = "111.111.111-11";
        String matricula = "121110199";
        int periodo = 4;
        Pessoa p = this.facade.recuperarPessoa(cpf);
        this.facade.definirFuncaoAluno(cpf, matricula, periodo);
        assertTrue(p.toString().contains("Aluno"));
        assertThrows(IllegalStateException.class, () -> {
            this.facade.definirFuncaoAluno(cpf, matricula, periodo);
        });

        try {
            this.facade.definirFuncaoAluno(cpf, matricula, periodo);
        } catch (IllegalStateException ise) {
            assertEquals("Essa pessoa já é um Aluno", ise.getMessage());
        }

    }

    @Test
    void testCadastrarProfessor() {
        String cpf = "333.333.333-33";
        String siap = "1234";
        this.facade.cadastrarProfessor(cpf, "Gaudensso", siap, new String[]{"LP2"}, new String[]{"Didática"});
        Pessoa pessoa = this.facade.recuperarPessoa(cpf);
        assertTrue(pessoa.toString().contains("Professor"));
        assertTrue(pessoa.toString().contains(cpf));
        assertTrue(pessoa.toString().contains(siap));
    }

    @Test
    void testDefinirFuncaoProfessor() {
        String cpf = "000.000.000-00";
        String siape = "1234";
        Pessoa p = this.facade.recuperarPessoa(cpf);
        assertFalse(p.toString().contains("Professor"));
        this.facade.definirFuncaoProfessor(cpf, siape, new String[]{"P2"});
        assertTrue(p.toString().contains("Professor"));
    }

    @Test
    void testDefinirFuncaoProfessorJaEhProfessor() {
        String cpf = "000.000.000-00";
        String siape = "1234";
        Pessoa p = this.facade.recuperarPessoa(cpf);
        this.facade.definirFuncaoProfessor(cpf, siape, new String[]{"P2"});
        assertTrue(p.toString().contains("Professor"));
        assertThrows(IllegalStateException.class, () -> {
            this.facade.definirFuncaoProfessor(cpf, siape, new String[]{"P2"});
        });

        try {
            this.facade.definirFuncaoProfessor(cpf, siape, new String[]{"P2"});
        } catch (IllegalStateException ise) {
            assertEquals("Essa pessoa já é um Professor", ise.getMessage());
        }

    }

    @Test
    void testRemoverFuncao() {
        String cpf = "000.000.000-00";
        String siape = "1234";
        Pessoa p = this.facade.recuperarPessoa(cpf);
        this.facade.definirFuncaoProfessor(cpf, siape, new String[]{"P2"});
        assertTrue(p.toString().contains("Professor"));
        this.facade.removerFuncao(cpf);
        assertFalse(p.toString().contains("Professor"));
    }

    @Test
    void testRemoverFuncaoJaEstaSemFuncao() {
        String cpf = "000.000.000-00";
        Pessoa p = this.facade.recuperarPessoa(cpf);
        assertFalse(p.toString().contains("Professor"));
        assertFalse(p.toString().contains("Aluno"));

        assertThrows(IllegalStateException.class, () -> {
            this.facade.removerFuncao(cpf);
        });

        try {
            this.facade.removerFuncao(cpf);
        } catch (IllegalStateException ise) {
            assertEquals("Essa pessoa já está sem função", ise.getMessage());
        }
    }

    @Test
    void testPegarNivel() {
        String cpf = "333.333.333-33";
        this.facade.cadastrarPessoa(cpf, "Pedro", new String[]{"Programar"});
        int nivelA = this.facade.pegarNivel(cpf);
        assertEquals(0, nivelA);
        String idAtividade = this.facade.cadastrarAtividade("Lab6", "Laboratorio 6", cpf);
        String idTarefaA = this.facade.cadastrarTarefa(idAtividade, "A", new String[]{"Ler"});
        String idTarefaB = this.facade.cadastrarTarefa(idAtividade, "B", new String[]{"Ler"});
        String idTarefaC = this.facade.cadastrarTarefa(idAtividade, "C", new String[]{"Ler"});

        this.facade.associarPessoaTarefa(cpf, idTarefaA);
        this.facade.associarPessoaTarefa(cpf, idTarefaB);
        this.facade.associarPessoaTarefa(cpf, idTarefaC);

        int nivelB = this.facade.pegarNivel(cpf);
        assertEquals(1, nivelB);

        this.facade.concluirTarefa(idTarefaA);
        int nivelC = this.facade.pegarNivel(cpf);
        assertEquals(2, nivelC);

        this.facade.definirFuncaoAluno(cpf, "1234", 5);
        int nivelD = this.facade.pegarNivel(cpf);
        assertEquals(2, nivelD);

        String idTarefaD = this.facade.cadastrarTarefa(idAtividade, "D", new String[]{"Ler"});
        String idTarefaE = this.facade.cadastrarTarefa(idAtividade, "E", new String[]{"Ler"});
        String idTarefaF = this.facade.cadastrarTarefa(idAtividade, "F", new String[]{"Programar"});

        this.facade.associarPessoaTarefa(cpf, idTarefaD);
        this.facade.associarPessoaTarefa(cpf, idTarefaE);
        this.facade.associarPessoaTarefa(cpf, idTarefaF);

        int nivelE = this.facade.pegarNivel(cpf);
        assertEquals(3, nivelE);

        this.facade.concluirTarefa(idTarefaF);

        int nivelF = this.facade.pegarNivel(cpf);
        assertEquals(5, nivelF);

        this.facade.removerFuncao(cpf);

        int nivelG = this.facade.pegarNivel(cpf);
        assertEquals(5, nivelG);

    }

    @Test
    void testPegarNivelProfessor() {
        String cpf = "333.333.333-33";
        this.facade.cadastrarPessoa(cpf, "Gaudencio", new String[]{"programar", "lecionar"});
        this.facade.definirFuncaoProfessor(cpf, "12345", new String[]{"lp2", "p2"});
        int nivelA = this.facade.pegarNivel(cpf);
        assertEquals(0, nivelA);
        String idAtividade = this.facade.cadastrarAtividade("Lab6", "Laboratorio 6", cpf);
        String idTarefaA = this.facade.cadastrarTarefa(idAtividade, "A", new String[]{"programar"});
        String idTarefaC = this.facade.cadastrarTarefa(idAtividade, "C", new String[]{"programar","ler"});
        String idTarefaB = this.facade.cadastrarTarefa(idAtividade, "B", new String[]{"lp2"});

        this.facade.associarPessoaTarefa(cpf, idTarefaA);
        this.facade.associarPessoaTarefa(cpf, idTarefaB);
        this.facade.associarPessoaTarefa(cpf, idTarefaC);

        int nivelB = this.facade.pegarNivel(cpf);
        assertEquals(0, nivelB);

        String idTarefaD = this.facade.cadastrarTarefa(idAtividade, "D", new String[]{"ler"});
        this.facade.associarPessoaTarefa(cpf, idTarefaD);

        int nivelC = this.facade.pegarNivel(cpf);
        assertEquals(1, nivelC);

        this.facade.concluirTarefa(idTarefaA);
        int nivelD = this.facade.pegarNivel(cpf);
        assertEquals(1, nivelD);

        this.facade.concluirTarefa(idTarefaB);
        this.facade.concluirTarefa(idTarefaC);
        this.facade.concluirTarefa(idTarefaD);

        int nivelE = this.facade.pegarNivel(cpf);
        assertEquals(2, nivelE);

    }

    @Test
    void testListarPessoas() {
        String[] pessoas = this.facade.listarPessoas();
        assertEquals("Cristovo – 111.111.111-11\n", pessoas[0]);
        assertEquals("Irmael – 000.000.000-00\n", pessoas[1]);
    }
}
