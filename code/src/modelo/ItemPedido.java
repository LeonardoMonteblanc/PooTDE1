package modelo;

import exceptions.EstoqueException;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private Integer codigo;

    public ItemPedido(Produto produto, int quantidade) {
        this.setProduto(produto);
        this.setQuantidade(quantidade);
        this.validarQuantidade();
    }

    public void validarQuantidade() throws EstoqueException {
        if(this.produto.getEstoque() < this.quantidade) {
            throw new EstoqueException("Estoque insuficiente para" + this.produto.getDescricao() + ". Quantidade disponivel no momento: " + this.produto.getEstoque());
        }
    }

    public double getSubTotal() {
        return this.produto.getPreco() * this.quantidade;
    }
// GETS ======================================================

    public Produto getProduto() {
        return this.produto;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

// SETS ======================================================
    public void setProduto(Produto p) {
        if(p == null ) {
            throw new IllegalArgumentException("Escolha um produto válido");
        }
        this.produto = p;
    }

    public void setQuantidade(int q) {
        if(q <= 0) {
            throw new IllegalArgumentException("A quantidade escolhida é inválida");
        }
        this.quantidade = q;
    }
    
    public void setCodigo(Integer c) {
        this.codigo = c;
    }
    @Override
    public String toString() {
        return String.format("%d x %s (Subtotal: R$ %.2f)", this.quantidade, this.produto.getDescricao(), this.getSubTotal());
    }

}
