package fiap.com.br.future_stack;

import fiap.com.br.future_stack.moto.Moto;
import fiap.com.br.future_stack.moto.MotoDTO;
import fiap.com.br.future_stack.moto.MotoService;
import fiap.com.br.future_stack.moto.StatusMoto;
import fiap.com.br.future_stack.patio.Patio;
import fiap.com.br.future_stack.zona.TipoZona;
import fiap.com.br.future_stack.zona.Zona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MotoServiceTest {

    private final MotoService service = new MotoService(null, null, null);

    @Test
    void deveConverterMotoParaDTO() {
        Zona zona = new Zona();
        zona.setId(1L);
        zona.setTipoZona(TipoZona.B);

        Patio patio = new Patio();
        patio.setId(2L);

        Moto moto = new Moto();
        moto.setId(3L);
        moto.setModelo("Honda CG");
        moto.setPlaca("ABC1234");
        moto.setStatus(StatusMoto.DISPONIVEL);
        moto.setZona(zona);
        moto.setPatio(patio);

        MotoDTO dto = service.toDTO(moto);

        Assertions.assertEquals(3L, dto.getId());
        Assertions.assertEquals("Honda CG", dto.getModelo());
        Assertions.assertEquals("ABC1234", dto.getPlaca());
        Assertions.assertEquals(1L, dto.getZonaId());
        Assertions.assertEquals(TipoZona.B, dto.getTipoZona());
        Assertions.assertEquals(StatusMoto.DISPONIVEL, dto.getStatus());
        Assertions.assertEquals(2L, dto.getPatioId());
    }
}
