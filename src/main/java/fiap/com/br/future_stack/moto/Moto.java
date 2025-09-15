package fiap.com.br.future_stack.moto;

import fiap.com.br.future_stack.patio.Patio;
import fiap.com.br.future_stack.zona.Zona;
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
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    @NotBlank
    @Column(unique = true)
    @Size(min = 7, max = 7, message = "{moto.placa.size}")
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    private Zona zona;

    private StatusMoto status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patio_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_MOTO_PATIO"))
    private Patio patio;

}
