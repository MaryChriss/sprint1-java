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
public class Gateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gateway;

    @Column(unique = true, nullable = false)
    private String mac_address;

    private String descricao;

    @ManyToOne
    private Zona localid_zona;

}
