package fiap.com.br.future_stack;

import fiap.com.br.future_stack.moto.Moto;
import fiap.com.br.future_stack.moto.StatusMoto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MotoTest {

    @Test
    void deveCriarMotoCorretamente() {
        Moto moto = new Moto();
        moto.setModelo("Honda CG");
        moto.setPlaca("ABC1234");
        moto.setStatus(StatusMoto.DISPONIVEL);

        Assertions.assertEquals("Honda CG", moto.getModelo());
        Assertions.assertEquals("ABC1234", moto.getPlaca());
        Assertions.assertEquals(StatusMoto.DISPONIVEL, moto.getStatus());
    }
}
