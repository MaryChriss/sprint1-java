package fiap.com.br.future_stack.alpr;

import fiap.com.br.future_stack.moto.Moto;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoAlpr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_alpr;

    @ManyToOne
    private Moto moto;

    @Column(nullable = false)
    private String placa_lida;

    private String url_imagem;

    private LocalDateTime ts_alpr;

}
