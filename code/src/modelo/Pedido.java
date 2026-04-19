package modelo;
import java.util.ArrayList;
import java.util.Iterator;

public class Pedido {
    private ArrayList<ItemPedido> itens = new ArrayList<>();
    
    public Pedido(){

    }

    public void adicionarItem(Produto p, int qtd) {
        for(ItemPedido i : itens ) {
            if(i.getProduto().equals(p)) {
                i.setQuantidade(i.getQuantidade() + qtd);
                return;
            } 
        }
        itens.add(new ItemPedido(p, qtd));
    }

    public void removerItem(Produto p) {
        Iterator<ItemPedido> it = itens.iterator();
        while(it.hasNext()) {
        if(it.next().getProduto().equals(p)) {
            it.remove();
            break; 
        }
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
