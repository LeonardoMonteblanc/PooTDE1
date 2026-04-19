package modelo;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return this.produto;

    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setProduto(Produto p) {
        this.produto = p;
    }

    public void setQuantidade(int q) {
        this.quantidade = q;
    }

    @Override
    public String toString() {
        return String.format("[ItemPedido]: produto: %s%nquantidade: %d", produto, quantidade);
    }

}
