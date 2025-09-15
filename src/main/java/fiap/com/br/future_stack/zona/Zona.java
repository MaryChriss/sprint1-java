package fiap.com.br.future_stack.zona;

import fiap.com.br.future_stack.moto.Moto;
import fiap.com.br.future_stack.patio.Patio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "zona",
        uniqueConstraints = @UniqueConstraint(name = "uq_zona_patio_tipo", columnNames = {"patio_id","tipo_zona"})
)
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_zona", nullable=false)
    private TipoZona tipoZona;

    private Double metragem;

    @OneToMany(mappedBy = "zona")
    private List<Moto> motos;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patio_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_zona_patio"))
    private Patio patio;

}
