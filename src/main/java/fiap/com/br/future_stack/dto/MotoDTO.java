package fiap.com.br.future_stack.dto;

import fiap.com.br.future_stack.model.StatusMoto;

public record MotoDTO(Long id, String modelo, String placa, Long zonaId, StatusMoto status) {
    
}
