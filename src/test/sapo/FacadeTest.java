package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapo.entities.Atividade;
import sapo.entities.Pessoa;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private Facade facade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{});
        facade.cadastrarPessoa("111.111.111-11", "Cristovo", new String[]{});
    }

    //########### Tests for Pessoa (remove later) ###########//

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
        assertEquals("Irmael – 000.000.000-00", pessoa.toString());
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
        assertEquals("Irmael – 000.000.000-00", pessoaRepresentacao);
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

    //########### Tests for Atividade (remove later) ###########//

    @Test
    void testCadastrarAtividade() {
        String id1 = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        String id2 = this.facade.cadastrarAtividade("legal", "descricao", "111.111.111-11");
        assertEquals("TVD-0", id1);
        assertEquals("LGL-1", id2);
    }

    @Test
    void testEncerrarAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.encerrarAtividade(id);
    }

    @Test
    void testEncerrarAtividadeEncerrada() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.encerrarAtividade(id);
        assertThrows(IllegalStateException.class, () -> {
            this.facade.encerrarAtividade(id);
        });
        try {
            this.facade.desativarAtividade(id);
        } catch (IllegalStateException ise) {
            assertEquals("A atividade " + id + " já está encerrada.", ise.getMessage());
        }
    }

    @Test
    void testEncerrarAtividadeDesativada() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.desativarAtividade(id);
        assertThrows(IllegalStateException.class, () -> {
            this.facade.encerrarAtividade(id);
        });
        try {
            this.facade.encerrarAtividade(id);
        } catch (IllegalStateException ise) {
            assertEquals("A atividade " + id + " não pode ser encerrada pois está desativada.", ise.getMessage());
        }
    }

    @Test
    void testEncerrarAtividadeTarefasPendentes() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        //TODO
    }

    @Test
    void testDesativarAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.desativarAtividade(id);
    }

    @Test
    void testDesativarAtividadeDesativada() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.desativarAtividade(id);
        assertThrows(IllegalStateException.class, () -> {
            this.facade.desativarAtividade(id);
        });
        try {
            this.facade.desativarAtividade(id);
        } catch (IllegalStateException ise) {
            assertEquals("A atividade " + id + " já está desativada.", ise.getMessage());
        }
    }

    @Test
    void testDesativarAtividadeTarefasPendentes() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        //TODO
    }

    @Test
    void testReabrirAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.reabrirAtividade(id);
    }

    @Test
    void testReabrirAtividadeAberta() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.reabrirAtividade(id);
        assertThrows(IllegalStateException.class, () -> {
            this.facade.reabrirAtividade(id);
        });
        try {
            this.facade.reabrirAtividade(id);
        } catch (IllegalStateException ise) {
            assertEquals("A atividade " + id + " já está aberta", ise.getMessage());
        }
    }

    @Test
    void testExibirAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        String expected = "TVD-0:atividade\n" + "Responsável: Irmael – 000.000.000-00\n" + "===\n" + "descricao\n" + "===\n" + "Tarefas: 0/0\n";
        assertEquals(expected, this.facade.exibirAtividade(id));
    }

    @Test
    void testExibirAtividadeTarefas() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        //TODO
    }

    @Test
    void testAlterarDescricaoAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.alterarDescricaoAtividade(id, "essa atividade é muito legal!");
    }

    @Test
    void testAlterarResponsavelAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.alterarResponsavelAtividade(id, "000.000.000-00");
    }

    @Test
    void testAlterarResponsavelInexistenteAtividade() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        assertThrows(NoSuchElementException.class, () -> {
            this.facade.alterarResponsavelAtividade(id, "cê pê éfe");
        });
        try {
            this.facade.alterarResponsavelAtividade(id, "cê pê éfe");
        } catch (NoSuchElementException nsee) {
            assertEquals("CPF fornecido não pertence a nenhuma Pessoa", nsee.getMessage());
        }
    }

    //########### Tests for Tarefa (remove later) ###########//

    @Test
    void test() {

    }
}
