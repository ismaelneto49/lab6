package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtividadeControllerTest {

    private Facade facade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{});
        facade.cadastrarPessoa("111.111.111-11", "Cristovo", new String[]{});
    }

    // ########### Tests for Atividade (remove later) ###########//

    @Test
    void testCadastrarAtividade() {
        String id1 = this.facade.cadastrarAtividade("legal", "descricao", "111.111.111-11");
        assertEquals("LGL-0", id1);
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
        String idAtv = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        this.facade.cadastrarTarefa(idAtv, "tarefa", new String[]{"hab1", "hab3"});
        assertThrows(IllegalStateException.class, () -> {
            this.facade.encerrarAtividade(idAtv);
        });
        try {
            this.facade.encerrarAtividade(idAtv);
        } catch (IllegalStateException ise) {
            assertEquals("A atividade " + idAtv + " possui tarefas pendentes.", ise.getMessage());
        }
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
        this.facade.cadastrarTarefa(id, "tarefa", new String[]{"hab1", "hab3"});
        assertThrows(IllegalStateException.class, () -> {
            this.facade.desativarAtividade(id);
        });
        try {
            this.facade.desativarAtividade(id);
        } catch (IllegalStateException ise) {
            assertEquals("A atividade " + id + " possui tarefas pendentes.", ise.getMessage());
        }
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
        String id = this.facade.cadastrarAtividade("atividadeDaora", "descricao", "000.000.000-00");
        String esperado = "TVD-0: atividadeDaora\n" + "Responsável: Irmael – 000.000.000-00\n" + "===\n" + "descricao\n"
                + "===\n" + "Tarefas: 0/0\n";
        assertEquals(esperado, this.facade.exibirAtividade(id));
    }

    @Test
    void testExibirAtividadeTarefas() {
        String id = this.facade.cadastrarAtividade("atividadeDaora", "descricao", "000.000.000-00");
        this.facade.cadastrarTarefa(id, "tarefa", new String[]{"hab1", "hab3"});

        String esperado = "TVD-0: atividadeDaora\n" + "Responsável: Irmael – 000.000.000-00\n" + "===\n" + "descricao\n"
                + "===\n" + "Tarefas: 0/1\n- tarefa - TVD-0-0\n";
        assertEquals(esperado, this.facade.exibirAtividade(id));
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

}
