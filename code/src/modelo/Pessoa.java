package modelo;

public abstract class Pessoa {
    private int codigo;
    private String nome;

    protected Pessoa(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    //GETS
    public int getCodigo() {
        return this.codigo;
    }

    public String getNome() {
        return this.nome;
    }

    //SETS
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("[Pessoa]: codigo: %d%nNome: %s",codigo, nome);
    }


}
