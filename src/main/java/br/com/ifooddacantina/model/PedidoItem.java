package br.com.ifooddacantina.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedido_itens")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(nullable = false)
    private String nomeItem;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    protected PedidoItem() {
    }

    public PedidoItem(Pedido pedido, String nomeItem, int quantidade, BigDecimal subtotal) {
        this.pedido = pedido;
        this.nomeItem = nomeItem;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public Long getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public String getNomeItem() { return nomeItem; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getSubtotal() { return subtotal; }
}
