package fiap.com.br.future_stack.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoZona tipo;

    private Double metragem;

    @OneToMany(mappedBy = "zona")
    private List<Moto> motos;

    @Column(nullable = false, unique = true)
    private String nome;

      public Zona() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoZona getTipo() {
        return tipo;
    }

    public void setTipo(TipoZona tipo) {
        this.tipo = tipo;
    }

    public Double getMetragem() {
        return metragem;
    }

    public void setMetragem(Double metragem) {
        this.metragem = metragem;
    }

    public List<Moto> getMotos() {
        return motos;
    }

    public void setMotos(List<Moto> motos) {
        this.motos = motos;
    }

    public Zona(String nome) { this.nome = nome; }
}
