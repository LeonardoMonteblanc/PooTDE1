package modelo;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private int codigo;
    private String descricao;
    private double preco;
    private List<Fornecedor> fornecedores = new ArrayList<>();
    
    public Produto(int codigo, String desc, double preco) {
        this.codigo = codigo;
        this.descricao = desc;
        this.preco = preco;
        this.fornecedores = new ArrayList<>();
    }

    public Produto(int codigo, String desc, double p, Fornecedor... fLista) {
        this(codigo, desc, p);
        for( Fornecedor f : fLista) {
            adicionarFornecedor(f);
        }
    }

    public void adicionarFornecedor(Fornecedor f) {
        fornecedores.add(f);
    }


// GETS ======================================================
    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
// SETS ======================================================
    public void setCodigo(int cod) {
        this.codigo = cod;
    }

    public void setDescricao(String desc) {
        this.descricao = desc;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setFornecedores(Fornecedor f) {
        fornecedores.add(f);
    }

    @Override 
    public String toString() {
        return String.format("[Produto]: codigo: %d%ndescricao: %s%npreco: %.2f", codigo, descricao, preco);
    }

    
}
