package fiap.com.br.future_stack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
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

    public Moto(String placa, String modelo, String status) {
    this.placa = placa;
    this.modelo = modelo;
    this.status = StatusMoto.valueOf(status);
}

public Moto() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }
}
