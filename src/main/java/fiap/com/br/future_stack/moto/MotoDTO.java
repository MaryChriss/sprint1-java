package fiap.com.br.future_stack.moto;

import fiap.com.br.future_stack.moto.StatusMoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoDTO {
    public Long id;

    @NotBlank(message = "{moto.modelo.notblank}")
    public String modelo;

    @NotBlank(message = "Placa é obrigatória")
    @Size(min = 7, max = 7, message = "{moto.placa.size}")
    public String placa;

    public Long zonaId;

    @NotNull(message = "Status é obrigatório")
    public StatusMoto status;

    public Long patioId;
}
