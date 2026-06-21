package modelo;

import exceptions.SistemaException;
import modelo.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Integer codigo;
    private Usuario cliente;
    private List<ItemPedido> itens;
    private StatusPedido status;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataCriado;
    private LocalDateTime dataCancelamento;
    private double valorFrete;
    
    public Pedido(Usuario cliente) {
        this.setCliente(cliente);
        this.itens = new ArrayList<>();
        this.status = StatusPedido.PENDENTE;
        this.dataCriado = LocalDateTime.now();
        this.valorFrete = 0.0;
    }

// PEDIDO ======================================================
    public void concluirPedido(Carrinho carrinho) {
        List<ItemPedido> itensCarrinho = carrinho.getItens();

        if(itensCarrinho.isEmpty()) {
            throw new IllegalArgumentException("Não há itens no carrinho para concluir o pedido");
        }

        for(ItemPedido i : itensCarrinho) {
            i.getProduto().diminuirEstoque(i.getQuantidade());
            this.itens.add(i);
        }

        this.status = StatusPedido.EM_PROCESSAMENTO;
        carrinho.limparCarrinho();
    }

    public void setFrete(double v) {
        this.valorFrete = v;
    }

    public void enviarPedido() throws SistemaException {
        if(this.status != StatusPedido.EM_PROCESSAMENTO) {
            throw new SistemaException("Esse pedido está cancelado ou já foi enviado!");
        }

        this.status = StatusPedido.ENVIADO;
        this.dataEnvio = LocalDateTime.now();
    }

    public void cancelarPedido() throws SistemaException {
        if(this.status == StatusPedido.ENVIADO) {
            throw new SistemaException("Pedido já enviados não podem cancelados");
        }
        if(this.status == StatusPedido.CANCELADO) {
            throw new SistemaException("Pedido já cancelado, impossivel cancelar novamente");
        }

        this.status = StatusPedido.CANCELADO;
        this.dataCancelamento = LocalDateTime.now();

        for(ItemPedido i : this.itens) {
            i.getProduto().adicionarEstoque(i.getQuantidade());
        }
    }

    public double calcularTotalPedido() {
        double total = 0.0;

        for(ItemPedido i : this.itens) {
            total += i.getSubTotal();
        }

        return total + this.getValorFrete();
    }

// GETS ======================================================
    public double getValorFrete() {
        return this.valorFrete;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Usuario getCliente() {
        return this.cliente;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(this.itens);
    }

    public StatusPedido getStatus() {
        return this.status;
    }

    public LocalDateTime getDataEnvio() {
        return this.dataEnvio;
    }

    public LocalDateTime getDataCriado() {
        return this.dataCriado;
    }

    public LocalDateTime getDataCancelamento() {
        return this.dataCancelamento;
    }

// SETS ======================================================
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public void setStatus(StatusPedido st) {
        this.status = st;
    }
    public void setDataCancelamento(LocalDateTime dt) {
        this.dataCancelamento = dt;
    }

    public void setDataEnvio(LocalDateTime dt) {
        this.dataEnvio = dt;
    }

    public void setDataCriado(LocalDateTime dt ) {
        this.dataCriado = dt;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = (itens != null) ? new ArrayList<>(itens) : new ArrayList<>();

    }



    @Override 
    public String toString() {
        return String.format("[Pedido #%d]: itens: %s", codigo, itens);
    }
}
