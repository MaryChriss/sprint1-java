package fiap.com.br.future_stack.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoWifi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evento_wifi;

    @ManyToOne
    private Moto moto;

    @ManyToOne
    private Gateway gateway;

    private Integer rssits_evento;

}
