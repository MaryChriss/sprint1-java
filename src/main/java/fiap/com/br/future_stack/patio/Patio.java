package fiap.com.br.future_stack.patio;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank()
    @Column(unique = true)
    private String nome;

    @NotNull
    @Min(value = 60)
    @Max(value = 255)
    private Integer quantidadeVagas;

    @NotNull
    @DecimalMin(value = "350", inclusive = true)   // era @Size
    @DecimalMax(value = "1400", inclusive = true)  // era @Size
    private Double metragemZonaA;

    @NotNull
    @DecimalMin(value = "350", inclusive = true)   // era @Size
    @DecimalMax(value = "1400", inclusive = true)  // era @Size
    private Double metragemZonaB;
}
