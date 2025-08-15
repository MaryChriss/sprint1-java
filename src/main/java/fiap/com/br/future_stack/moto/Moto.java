package fiap.com.br.future_stack.moto;

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

    @NotBlank
    private String modelo;

    @NotBlank
    @Column(unique = true)
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    private Zona zona;

    private StatusMoto status;

}
