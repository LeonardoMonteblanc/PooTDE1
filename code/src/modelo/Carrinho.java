package modelo;
import exceptions.EstoqueException;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<ItemPedido> itens; 

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItemCarrinho(Produto p, int qtd) throws EstoqueException {
        ItemPedido itemNoCarrinho = buscarProdutoCarrinho(p);

        if(itemNoCarrinho != null) {
            int qtdAtt = itemNoCarrinho.getQuantidade() + qtd;
            if(p.getEstoque() < qtdAtt) {
                throw new EstoqueException("Para esse produto há apenas" + p.getEstoque() + " unidades em estoque. Quantidade não foi atualizada");
            }
            itemNoCarrinho.setQuantidade(qtdAtt);
        } else {
            this.itens.add(new ItemPedido(p, qtd));
        }
    }

    public void removerItem(Produto p) {
        ItemPedido itemNoCarrinho = buscarProdutoCarrinho(p);

        if(itemNoCarrinho != null) {
            this.itens.remove(itemNoCarrinho);

        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubTotal();
        }
        return total;
    }

    public ItemPedido buscarProdutoCarrinho(Produto p ) {
        for(ItemPedido i : this.itens) {
            if(i.getProduto() == p) {
                return i;
            }
        }
        return null;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(this.itens);
    }

    public void limparCarrinho() {
        this.itens.clear();
    }


}
