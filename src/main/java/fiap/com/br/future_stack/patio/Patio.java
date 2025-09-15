package fiap.com.br.future_stack.patio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;

    @Size(min = 60, max = 255, message = "{patio.qntvagas.size}")
    private Integer quantidadeVagas;

    @Size(min =350, max =1400 , message = "{patio.qntvagasA.size}")
    private Double metragemZonaA;

    @Size(min =350, max =1400 , message = "{patio.qntvagasB.size}")
    private Double metragemZonaB;

}
