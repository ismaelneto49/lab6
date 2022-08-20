package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TarefaControllerTest {
    private Facade facade;
    private String idAtividade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{});
        facade.cadastrarPessoa("111.111.111-11", "Cristovo", new String[]{});
        this.idAtividade = this.facade.cadastrarAtividade("atividade", "uma atividade", "000.000.000-00");
    }

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
        String esperado = "tarefa - TVD-0-0\n" + "- atividade\n" + "hab1, hab3\n" + "(0 hora(s) executada(s))\n"
                + "===\n" + "Equipe\n";
        assertEquals(esperado, resultado);
    }

    @Test
    void testExibirTarefaPessoaAssociada() {
        String id = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        this.facade.associarPessoaTarefa("000.000.000-00", id);
        String resultado = this.facade.exibirTarefa(id);
        String esperado = "tarefa - TVD-0-0\n" + "- atividade\n" + "hab1, hab3\n" + "(0 hora(s) executada(s))\n"
                + "===\n" + "Equipe\n" + "Irmael – 000.000.000-00\n";
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
    void testCadastrarTarefaGerencial() {
        String t1 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String t2 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa2", new String[]{"hab1"});
        String tg1 = this.facade.cadastrarTarefaGerencial(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"}, new String[]{t1, t2});
        assertEquals("TVD-0-2", tg1);
    }

    @Test
    void testAdicionarNaTarefaGerencial() {
        String t1 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String tg1 = this.facade.cadastrarTarefaGerencial(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"}, new String[]{});
        this.facade.adicionarNaTarefaGerencial(tg1, t1);
        assertEquals(1, this.facade.contarTodasTarefasNaTarefaGerencial(tg1));
    }

    @Test
    void testRemoverDaTarefaGerencial() {
        String t1 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String tg1 = this.facade.cadastrarTarefaGerencial(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"}, new String[]{t1});

        assertEquals(1, this.facade.contarTodasTarefasNaTarefaGerencial(tg1));
        this.facade.removerDaTarefaGerencial(tg1, t1);
        assertEquals(0, this.facade.contarTodasTarefasNaTarefaGerencial(tg1));
    }

    @Test
    void testContarTodasTarefasNaTarefaGerencial() {
        String t1 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String t2 = this.facade.cadastrarTarefa(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"});
        String tg1 = this.facade.cadastrarTarefaGerencial(this.idAtividade, "tarefa", new String[]{"hab1", "hab3"}, new String[]{t1, t2});

        assertEquals(2, this.facade.contarTodasTarefasNaTarefaGerencial(tg1));
    }

}
