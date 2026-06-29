package modelo;

public class Transportadora extends Pessoa {
    private double taxaFrete; 
    
    public Transportadora(Integer codigo, String nome, String cnpj, double taxa) {
        super(codigo, nome, cnpj);
        this.taxaFrete = taxa;
    }

    public Transportadora(String nome, String cnpj, double taxa) {
        super(null, nome, cnpj);
        this.taxaFrete = taxa;
    }


// GETS ======================================================
    public double getTaxaFrete() {
        return this.taxaFrete;
    }

// SETS ======================================================
    public void setTaxaFrete(double taxaFrete) {
        if(taxaFrete < 0 || Double.isNaN(taxaFrete)) {
            throw new IllegalArgumentException("Valor de frete inválido");
        }
        this.taxaFrete = taxaFrete;
    }

    @Override 
    public String toString() {
        return String.format("Transportadora: %s | CNPJ: %s | Frete: R$ %.2f", this.getNome(), this.getDocumento(), this.taxaFrete);
    }
}
