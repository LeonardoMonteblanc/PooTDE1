package modelo;
import java.util.ArrayList;
import java.util.List;

public class Fornecedor extends Pessoa {
    private List<Produto> listaProdutos;

    public Fornecedor(Integer codigo, String nome, String cnpj) {
        super(codigo, nome, cnpj);
        this.listaProdutos = new ArrayList<>();
    }

// GETS ======================================================
    public List<Produto> getProdutos() {
        return new ArrayList<>(this.listaProdutos);
    }

// SETS ======================================================
    public void vincularProduto(Produto p) {
        if(p != null && !this.listaProdutos.contains(p)) {
            this.listaProdutos.add(p);
        }
    }

    public void desvincularProduto(Produto p) {
        if(p != null && !this.listaProdutos.contains(p)) {
            this.listaProdutos.remove(p);
        }
    }

}
