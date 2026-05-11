package modelo;
import java.util.ArrayList;
import java.util.Iterator;

public class Pedido {
    private int codigo;
    private ArrayList<ItemPedido> itens = new ArrayList<>();
    
    public Pedido(int codigo) {
        this.codigo = codigo;
    }

    public void adicionarItem(Produto p, int qtd) {
        for(ItemPedido i : itens ) {
            if(i.getProduto().getCodigo() == p.getCodigo()) {
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

    public int getCodigo() {
        return codigo;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public ItemPedido getItemByCodigoProduto(int codigoProduto) {
        for (ItemPedido i : itens) {
            if (i.getProduto().getCodigo() == codigoProduto) {
                return i;
            }
        }
        return null;
    }
// SETS ======================================================
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }
    
    @Override 
    public String toString() {
        return String.format("[Pedido #%d]: itens: %s", codigo, itens);
    }
}
