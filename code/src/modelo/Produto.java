package modelo;

public class Produto {
    private int codigo;
    private String descricao;
    private double preco;
    
    public Produto(int codigo, String desc, double preco) {
        this.codigo = codigo;
        this.descricao = desc;
        this.preco = preco;
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

    @Override 
    public String toString() {
        return String.format("[Produto]: codigo: %d%ndescricao: %s%npreco: %.2f", codigo, descricao, preco);
    }

    
}
