package fiap.com.br.future_stack.model;

import jakarta.persistence.*;

@Entity
public class Gateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gateway;

    @Column(unique = true, nullable = false)
    private String mac_address;

    private String descricao;

    @ManyToOne
    private Zona localid_zona;

    public Gateway() {}

    public Gateway(String mac_address, String descricao, Zona zona) {
        this.mac_address = mac_address;
        this.descricao = descricao;
        this.localid_zona = zona;
    }

}
