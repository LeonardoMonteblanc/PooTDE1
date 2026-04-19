package modelo;

import java.time.LocalDate;
import java.util.List;

public class Remessa {
    private int codigo;
    private LocalDate data;
    private Transportadora transportadora;
    private Usuario cliente;
    private List<Pedido> pedidos;
    
    public Remessa(int cod, Transportadora t, Usuario cliente) {
        this.codigo = cod;
        this.transportadora = t;
        this.cliente = cliente;
    }

    public void adicionarPedido(Pedido p) {
        pedidos.add(p);
    }

    public void removerPedido(Pedido p) {
        pedidos.remove(p);
    }
// GETS ======================================================
    public int getCodigo() {
        return this.codigo;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Transportadora getTransportadora() {
        return this.transportadora;
    }

    public Usuario getCliente() {
        return this.cliente;
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

// SETS ======================================================
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setData(LocalDate d) {
        this.data = d;
    }

    public void setTransportadora(Transportadora t) {
        this.transportadora = t;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public void setPedidos(List<Pedido> p) {
        this.pedidos = p;
    }

    @Override
    public String toString() {
    return String.format("[Remessa]: codigo: %d%ndata: %s%ntransportadora: %s%ncliente: %s%nedidos: %s",
            codigo, data, transportadora, cliente, pedidos);
}
}
