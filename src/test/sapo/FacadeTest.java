package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

public class FacadeTest {

    private Facade facade;
    private String idAtividade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{});
        facade.cadastrarPessoa("111.111.111-11", "Cristovo", new String[]{});
        this.idAtividade = this.facade.cadastrarAtividade("atividade", "uma atividade", "000.000.000-00");
    }

    // ########### Tests for Pessoa (remove later) ###########//

    // ########### Tests for Atividade (remove later) ###########//

    @Test
    void testCadastrarAtividade() {
        String id1 = this.facade.cadastrarAtividade("legal", "descricao", "111.111.111-11");
        assertEquals("LGL-1", id1);
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
        // TODO
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
        // TODO
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
        String expected = "TVD-1:atividadeDaora\n" + "Responsável: Irmael – 000.000.000-00\n" + "===\n" + "descricao\n" + "===\n" + "Tarefas: 0/0\n";
        assertEquals(expected, this.facade.exibirAtividade(id));
    }

    @Test
    void testExibirAtividadeTarefas() {
        String id = this.facade.cadastrarAtividade("atividade", "descricao", "000.000.000-00");
        // TODO
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

    // ########### Tests for Tarefa (remove later) ###########//

    @Test
    void testCadastrarTarefa() {
        String id1 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String id2 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa2", new String[]{"hab1"});
        assertEquals("TVD-0-0", id1);
        assertEquals("TVD-0-1", id2);
    }

    @Test
    void testAlterarNomeTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String novoNome = "tarefa daora";
        this.facade.alterarNomeTarefa(id, novoNome);
        assertTrue(this.facade.exibirTarefa(id).contains(novoNome));
    }

    @Test
    void testAlterarHabilidadesTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String[] novasHabs = new String[]{"hab2"};
        this.facade.alterarHabilidadesTarefa(id, novasHabs);
        assertTrue(this.facade.exibirTarefa(id).contains(novasHabs[0]));
    }

    @Test
    void testAlterarHabilidadeVaziaTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String[] novasHabs = new String[]{};
        this.facade.alterarHabilidadesTarefa(id, novasHabs);
    }

    @Test
    void testAdicionarHorasTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        int horas = 10;
        this.facade.adicionarHorasTarefa(id, horas);
        assertTrue(this.facade.exibirTarefa(id).contains(horas + " hora(s)"));
    }

    @Test
    void testAdicionarHorasNegativasTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        int horas = -10;
        assertThrows(IllegalArgumentException.class, () -> {
            this.facade.adicionarHorasTarefa(id, horas);
        });
        try {
            this.facade.adicionarHorasTarefa(id, horas);
        } catch (IllegalArgumentException iae) {
            assertEquals("Não é possível adicionar horas negativas em uma tarefa", iae.getMessage());
        }
    }

    @Test
    void testAdicionarHorasTarefaConcluida() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.concluirTarefa(id);

        assertThrows(IllegalStateException.class, () -> {
            this.facade.adicionarHorasTarefa(id, 10);
        });
        try {
            this.facade.adicionarHorasTarefa(id, 10);
        } catch (IllegalStateException ise) {
            assertEquals("Não é possível adicionar horas em uma tarefa já concluída", ise.getMessage());
        }
    }

    @Test
    void testRemoverHorasTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.adicionarHorasTarefa(id, 50);

        int horas = 10;
        this.facade.removerHorasTarefa(id, horas);
        assertTrue(this.facade.exibirTarefa(id).contains(50 - horas + " hora(s)"));
    }

    @Test
    void testRemoverHorasNegativasTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.adicionarHorasTarefa(id, 50);

        int horas = -10;
        assertThrows(IllegalArgumentException.class, () -> {
            this.facade.removerHorasTarefa(id, horas);
        });
        try {
            this.facade.removerHorasTarefa(id, horas);
        } catch (IllegalArgumentException iae) {
            assertEquals("Não é possível remover horas negativas em uma tarefa", iae.getMessage());
        }
    }

    @Test
    void testRemoverHorasTarefaConcluida() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.concluirTarefa(id);

        assertThrows(IllegalStateException.class, () -> {
            this.facade.removerHorasTarefa(id, 10);
        });
        try {
            this.facade.removerHorasTarefa(id, 10);
        } catch (IllegalStateException ise) {
            assertEquals("Não é possível remover horas em uma tarefa já concluída", ise.getMessage());
        }
    }

    @Test
    void testConcluirTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.concluirTarefa(id);
    }

    @Test
    void testRemoverTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.removerTarefa(id);
    }

    @Test
    void testExibirTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String resultado = this.facade.exibirTarefa(id);
        String esperado = "tarefa - TVD-0-0\n" + "- atividade\n" + "hab1, hab3\n" + "(0 hora(s) executada(s))\n" + "===\n" + "Equipe\n";
        assertEquals(esperado, resultado);
    }

    @Test
    void testExibirTarefaPessoaAssociada() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.associarPessoaTarefa("000.000.000-00", id);
        String resultado = this.facade.exibirTarefa(id);
        String esperado = "tarefa - TVD-0-0\n" + "- atividade\n" + "hab1, hab3\n" + "(0 hora(s) executada(s))\n" + "===\n" + "Equipe\n" + "Irmael – 000.000.000-00\n";
        assertEquals(esperado, resultado);
    }

    @Test
    void testAssociarPessoaTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.associarPessoaTarefa("000.000.000-00", id);
    }

    @Test
    void testAssociarPessoaTarefaConcluida() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.concluirTarefa(id);
        assertThrows(IllegalStateException.class, () -> {
            this.facade.associarPessoaTarefa("111.111.111-11", id);
        });
        try {
            this.facade.associarPessoaTarefa("111.111.111-11", id);
        } catch (IllegalStateException ise) {
            assertEquals("Não é possível adicionar pessoa responsável em uma tarefa já concluída", ise.getMessage());
        }
    }

    @Test
    void testRemoverPessoaTarefa() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.associarPessoaTarefa("000.000.000-00", id);
        this.facade.removerPessoaTarefa("000.000.000-00", id);
    }

    @Test
    void testRemoverPessoaTarefaConcluida() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.associarPessoaTarefa("111.111.111-11", id);
        this.facade.concluirTarefa(id);
        assertThrows(IllegalStateException.class, () -> {
            this.facade.removerPessoaTarefa("111.111.111-11", id);
        });
        try {
            this.facade.removerPessoaTarefa("111.111.111-11", id);
        } catch (IllegalStateException ise) {
            assertEquals("Não é possível remover pessoa responsável em uma tarefa já concluída", ise.getMessage());
        }
    }

    @Test
    void test() {
    }
}
