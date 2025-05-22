package fiap.com.br.future_stack.model;

import jakarta.persistence.*;

@Entity
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_patio;

    @Column(nullable = false, unique = true)
    private String nome;

    private String localizacao;

    public Patio() {}

    public Patio(String nome, String localizacao) {
        this.nome = nome;
        this.localizacao = localizacao;
    }

    // Getters e Setters
    public Long getId_patio() { return id_patio; }
    public void setId_patio(Long id_patio) { this.id_patio = id_patio; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}
