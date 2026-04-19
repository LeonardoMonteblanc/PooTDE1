package modelo;
import java.util.ArrayList;
import java.util.Iterator;

public class Pedido {
    private ArrayList<ItemPedido> itens;
    
    public Pedido(){

    }

    public void adicionarItem(Produto p, int qtd) {
        for(ItemPedido i : itens ) {
            if(i.getProduto().equals(p)) {
                i.setQuantidade(i.getQuantidade() + qtd);
            } else {
                itens.add(new ItemPedido(p, qtd));
            }
        }
    }

    public void removerItem(Produto p, int qtd) {
        Iterator<ItemPedido> it = itens.iterator();
        while(it.hasNext()) {
            it.next().getProduto().equals(p);
            it.remove();
        }
    }

// GETS ======================================================

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }
// SETS ======================================================
    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }
    
    @Override 
    public String toString() {
        return String.format("[Pedido]: itens: %s",itens);
    }
}
