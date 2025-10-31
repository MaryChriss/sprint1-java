package fiap.com.br.future_stack;

import fiap.com.br.future_stack.moto.MotoDTO;
import fiap.com.br.future_stack.moto.StatusMoto;
import fiap.com.br.future_stack.zona.TipoZona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MotoDTOTest {

    @Test
    void deveCriarMotoDTOComValoresCorretos() {
        MotoDTO dto = new MotoDTO(1L, "Fan", "XYZ9999", 10L, TipoZona.B, StatusMoto.DISPONIVEL, 5L);

        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("Fan", dto.getModelo());
        Assertions.assertEquals("XYZ9999", dto.getPlaca());
        Assertions.assertEquals(10L, dto.getZonaId());
        Assertions.assertEquals(TipoZona.B, dto.getTipoZona());
        Assertions.assertEquals(StatusMoto.DISPONIVEL, dto.getStatus());
        Assertions.assertEquals(5L, dto.getPatioId());
    }
}
