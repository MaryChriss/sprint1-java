package fiap.com.br.future_stack.patio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcupacaoDTO {
    public int totalMotos;
    public int motosZonaA;
    public int motosZonaB;
    public int totalVagas;
}
