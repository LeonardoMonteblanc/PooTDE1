package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modelo.enums.StatusPedido;

public class Remessa {
    private Integer codigo;
    private Transportadora transportadora;
    private List<Pedido> pedidos;
    private LocalDateTime dataRemessa;
    
    public Remessa(Transportadora t) {
        this.transportadora = t;
        this.pedidos = new ArrayList<>();
        this.dataRemessa = LocalDateTime.now();
    }

    public void adicionarPedido(Pedido p) {
        if(p == null || p.getStatus() != StatusPedido.EM_PROCESSAMENTO) {
            throw new IllegalArgumentException("Pedido vazio ou não confirmado no carrinho");
        }
        if(!this.pedidos.contains(p)){
            somarFrete(p);
            this.pedidos.add(p);
        }
    }

    private void somarFrete(Pedido p) {
        p.setFrete(transportadora.getTaxaFrete());
    }


// GETS ======================================================
    public int getCodigo() {
        return this.codigo;
    }

    public LocalDateTime getData() {
        return this.dataRemessa;
    }

    public Transportadora getTransportadora() {
        return this.transportadora;
    }


    public List<Pedido> getPedidos() {
        return new ArrayList<>(this.pedidos);    
    }

// SETS ======================================================
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setData(LocalDateTime d) {
        this.dataRemessa = d;
    }

    public void setTransportadora(Transportadora t) {
        this.transportadora = t;
    }

    @Override
    public String toString() {
    return String.format("[Remessa]: codigo: %d%ndata: %s%ntransportadora: %s%nedidos: %s",
            codigo, dataRemessa, transportadora, pedidos);
}
}
