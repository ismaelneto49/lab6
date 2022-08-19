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

    @Test
    void testCadastrarPessoa() {
        this.facade.cadastrarPessoa("123.123.123-12", "Teobaldo", new String[]{"hab1"});
    }

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
}
