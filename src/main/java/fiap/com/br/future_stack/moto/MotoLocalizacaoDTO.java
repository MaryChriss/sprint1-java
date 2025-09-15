package fiap.com.br.future_stack.moto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoLocalizacaoDTO {
    public Long motoId;
    public String modelo;
    public String placa;
    public Long zonaId;
    public String zonaNome;
    public String tipoZona;
    public Long patioId;
    public String patioNome;
}
