package modelo;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private int codigo;
    private String descricao;
    private double preco;
    private int qtdEstoque;
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

    public int getEstoque() {
        return qtdEstoque;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    
// SETS ======================================================
    public void setCodigo(int cod) {
        if(cod <= 0) {
            throw new IllegalArgumentException("Codigo inválido para um produto. Escolha um código de 0 a 99999");
        }
        this.codigo = cod;
    }

    public void setDescricao(String desc) {
        if(desc.isBlank()) {
            throw new IllegalArgumentException("Descrição do produto nao pode ficar em branco! Insira o nome do produto");
        }
        this.descricao = desc;
    }

    public void setPreco(double preco) {
        if(preco <= 0) {
            throw new IllegalArgumentException("O preço não pode ser 0 ou negativo. Insira um preço válido para o produto.");
        }
        this.preco = preco;
    }

    public void setEstoque(int qtd) {
        if(qtd < 0) {
            throw new IllegalArgumentException("Insira uma quantidade válida para o produto");
        }
        this.qtdEstoque = qtd;
    }

    @Override 
    public String toString() {
        return String.format("[Produto]: codigo: %d%ndescricao: %s%npreco: %.2f", codigo, descricao, preco);
    }

    
}
