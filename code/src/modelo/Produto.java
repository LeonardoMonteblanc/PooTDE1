package modelo;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private Integer codigo;
    private String descricao;
    private double preco;
    private int qtdEstoque;
    private List<Fornecedor> fornecedores;
    
    public Produto(Integer codigo, String desc, double preco, int qtdEstoque) {
        setCodigo(codigo);
        setDescricao(desc);
        setPreco(preco);
        setEstoque(qtdEstoque);
        this.fornecedores = new ArrayList<>();
    }


// FORNECEDOR ======================================================
    public void adicionarFornecedor(Fornecedor f) {
        if(f == null) {
            throw new IllegalArgumentException("Fornecedor invádlido");
        }

        if(!fornecedores.contains(f)) {
            fornecedores.add(f);
        }
    }

    public void removerFornecedor(Fornecedor f) {
        fornecedores.remove(f);
    }


// ESTOQUE ======================================================
    public boolean temEstoque(int qtd) {
        return qtd > 0 && qtd <= qtdEstoque;
    }

    public void adicionarEstoque(int qtd) {
        if(qtd <= 0 ){
            throw new exceptions.EstoqueException("Quantidade invalida.");
        }
        this.qtdEstoque +=qtd;
    }

    public void diminuirEstoque(int qtd) {
        if (qtd <= 0) {
            throw new exceptions.EstoqueException("Quantidade invalida.");
        }
        if (qtd > qtdEstoque) {
            throw new exceptions.EstoqueException("Estoque insuficiente: disponivel " + qtdEstoque + ", solicitado " + qtd);
        }
        qtdEstoque -= qtd;
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
        return new ArrayList<>(this.fornecedores);
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
