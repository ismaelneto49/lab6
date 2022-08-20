package sapo;

import org.junit.jupiter.api.BeforeEach;

public class BuscaControllerTest {

    private Facade facade;

    @BeforeEach
    void init() {
        this.facade = new Facade();
    }

}
