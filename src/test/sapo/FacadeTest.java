package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private Facade facade;
    private String idAtividade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        this.facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{"hab1", "hab2"});
        this.idAtividade = this.facade.cadastrarAtividade("atividade", "uma atividade", "000.000.000-00");
    }

    //########### Tests for Tarefa (remove later) ###########//

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
    void test() {

    }
}
