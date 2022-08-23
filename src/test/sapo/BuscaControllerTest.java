package sapo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuscaControllerTest {

    private Facade facade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
        facade.cadastrarPessoa("000.000.000-00", "Irmael", new String[]{"hab1"});
        facade.cadastrarPessoa("111.111.111-11", "Cristovo", new String[]{"hab1", "hab2"});
        this.facade.cadastrarAtividade("atividade1", "descricao1", "000.000.000-00");
        this.facade.cadastrarAtividade("atividade2", "descricao2", "111.111.111-11");
        this.facade.cadastrarTarefa("TVD-0", "tarefa1", new String[]{"hab1", "hab3"});
        this.facade.cadastrarTarefa("TVD-1", "tarefa2", new String[]{"hab1"});
    }

    @Test
    void testExibirPessoas() {
        String resultado = this.facade.exibirPessoas("111.111.111-11 Cristovo")[0];
        assertEquals("Cristovo – 111.111.111-11\n" + "\n- hab1\n- hab2", resultado);
    }

    @Test
    void testExibirPessoasHabilidade() {
        String p1 = this.facade.exibirPessoas("Hab1 hab1")[0];
        String p2 = this.facade.exibirPessoas("hab1 Hab1")[1];
        assertEquals("Cristovo – 111.111.111-11\n" + "\n- hab1\n- hab2", p1);
        assertEquals("Irmael – 000.000.000-00\n" + "\n- hab1", p2);
    }

    @Test
    void testExibirPessoasMultiplasHabilidades() {
        String resultado = this.facade.exibirPessoas("Hab1 hab2")[0];
        assertEquals("Cristovo – 111.111.111-11\n" + "\n- hab1\n- hab2", resultado);
    }

    @Test
    void testHistoricoBusca() {
        this.facade.exibirPessoas("111.111.111-11 Cristovo");
        String tipoBusca = this.facade.exibirHistoricoBusca(0)[0];
        String resultado = this.facade.exibirHistoricoBusca(0)[1];
        assertEquals("PESSOA", tipoBusca);
        assertEquals("Cristovo – 111.111.111-11\n" + "\n- hab1\n- hab2", resultado);
    }

    @Test
    void testHistoricoBuscaMultiplo() {
        this.facade.exibirPessoas("111.111.111-11 Cristovo");
        this.facade.exibirPessoas("000.000.000-00 Irmael");
        String tipoBusca1 = this.facade.exibirHistoricoBusca(0)[0];
        String resultado1 = this.facade.exibirHistoricoBusca(0)[1];
        String tipoBusca2 = this.facade.exibirHistoricoBusca(1)[0];
        String resultado2 = this.facade.exibirHistoricoBusca(1)[1];

        assertEquals("PESSOA", tipoBusca1);
        assertEquals("Cristovo – 111.111.111-11\n" + "\n- hab1\n- hab2", resultado1);
        assertEquals("PESSOA", tipoBusca2);
        assertEquals("Irmael – 000.000.000-00\n" + "\n- hab1", resultado2);
    }

    @Test
    void testHistoricoExibirMaisRecentes() {
        this.facade.exibirPessoas("111.111.111-11 Cristovo");
        this.facade.exibirPessoas("000.000.000-00 Irmael");
        String[] resultado = this.facade.buscasMaisRecentes(2);
        assertEquals("PESSOA Irmael – 000.000.000-00\n" + "\n- hab1", resultado[0]);
        assertEquals("PESSOA Cristovo – 111.111.111-11\n" + "\n- hab1\n- hab2", resultado[1]);
    }

    @Test
    void testBuscaAtividade() {
        String[] retorno = this.facade.buscarAtividade("atividade");
        assertEquals("TVD-0: atividade1\nResponsável: Irmael – 000.000.000-00\n===\ndescricao1\n===\nTarefas: 0/1\n- tarefa1 - TVD-0-0\n", retorno[0]);
        assertEquals("TVD-1: atividade2\nResponsável: Cristovo – 111.111.111-11\n===\ndescricao2\n===\nTarefas: 0/1\n- tarefa2 - TVD-1-1\n", retorno[1]);
    }

    @Test
    void testBuscaMultiplasAtividades() {
        String[] retorno = this.facade.buscarAtividade("TVD-1 atividade");
        assertEquals("TVD-0: atividade1\nResponsável: Irmael – 000.000.000-00\n===\ndescricao1\n===\nTarefas: 0/1\n- tarefa1 - TVD-0-0\n", retorno[0]);
        assertEquals("TVD-1: atividade2\nResponsável: Cristovo – 111.111.111-11\n===\ndescricao2\n===\nTarefas: 0/1\n- tarefa2 - TVD-1-1\n", retorno[1]);
    }

    @Test
    void testBuscarTarefasNome() {
        String[] resultado =  this.facade.buscarTarefas("tarefa");
        assertEquals("tarefa2 - TVD-1-1\n- atividade2\nhab1\n(0 hora(s) executada(s))\n===\nEquipe\n", resultado[0]);
        assertEquals("tarefa1 - TVD-0-0\n- atividade1\nhab1, hab3\n(0 hora(s) executada(s))\n===\nEquipe\n", resultado[1]);
    }

    @Test
    void testBuscarTarefasAtividadeNome() {
        String[] resultado = this.facade.buscarTarefas("TVD-0", "tarefa");
        assertEquals("tarefa1 - TVD-0-0\n- atividade1\nhab1, hab3\n(0 hora(s) executada(s))\n===\nEquipe\n", resultado[0]);
    }

    @Test
    void testSugerirTarefas() {
        String[] resultado = this.facade.sugerirTarefas("111.111.111-11");
        assertEquals("tarefa1 - TVD-0-0\n- atividade1\nhab1, hab3\n(0 hora(s) executada(s))\n===\nEquipe\n", resultado[0]);
        assertEquals("tarefa2 - TVD-1-1\n- atividade2\nhab1\n(0 hora(s) executada(s))\n===\nEquipe\n", resultado[1]);
    }

}