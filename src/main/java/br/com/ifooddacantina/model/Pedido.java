package br.com.ifooddacantina.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeAluno;

    @Column(nullable = false)
    private String horarioRetirada;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected Pedido() {
    }

    public Pedido(String nomeAluno, String horarioRetirada, BigDecimal total) {
        this.nomeAluno = nomeAluno;
        this.horarioRetirada = horarioRetirada;
        this.total = total;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNomeAluno() { return nomeAluno; }
    public String getHorarioRetirada() { return horarioRetirada; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
