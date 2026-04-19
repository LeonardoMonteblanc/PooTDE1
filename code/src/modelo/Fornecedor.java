package modelo;

public class Fornecedor extends Pessoa {
    private String cnpj;
    
    public Fornecedor(int codigo, String nome, String cnpj) {
        super(codigo, nome);
        this.cnpj = cnpj;
    }

// GETS ======================================================
    public String getCnpj() {
        return this.cnpj;
    }

// SETS ======================================================
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return String.format("[Fornecedor]: cnpj: %s",cnpj);
    }

}
