package fiap.com.br.future_stack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    public EventoAlpr() {}

    public EventoAlpr(Moto moto, String placaLida, String urlImagem, LocalDateTime timestamp) {
        this.moto = moto;
        this.placa_lida = placaLida;
        this.url_imagem = urlImagem;
        this.ts_alpr = timestamp;
    }

    // Getters e Setters
}
