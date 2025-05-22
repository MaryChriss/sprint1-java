package fiap.com.br.future_stack.model;

import jakarta.persistence.*;

@Entity
public class EventoWifi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evento_wifi;

    @ManyToOne
    private Moto moto;

    @ManyToOne
    private Gateway gateway;

    private Integer rssits_evento;

    public EventoWifi() {}

    public EventoWifi(Moto moto, Gateway gateway, int rssi) {
        this.moto = moto;
        this.gateway = gateway;
        this.rssits_evento = rssi;
    }

}
