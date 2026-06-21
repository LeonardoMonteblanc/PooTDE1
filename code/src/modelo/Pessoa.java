package modelo;

import java.util.Objects;

public abstract class Pessoa {
    private String nome;
    private String doc;
    private Integer codigo;

    protected Pessoa(Integer codigo, String nome, String doc) {
        this.codigo = codigo;
        this.setNome(nome);
        this.setDocumento(doc);

    }

    //GETS
    public int getCodigo() {
        return this.codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDocumento() {
        return this.doc;
    }

    //SETS
    public void setCodigo(Integer cod) {
        this.codigo = cod;
    }
    public void setNome(String nome) {
        if(nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode estar em branco");
        }
        this.nome = nome;
    }

    public void setDocumento(String doc) {
        if(doc.isBlank()) {
            throw new IllegalArgumentException("CNPJ/CPF não pode estar em branco");
        }

        this.doc = doc;

        // #1 Issue (@Vitor)
        // Incluir validação de CNPJ/CPF? 
    }

    @Override 
    public boolean equals(Object ob) {
        if(this == ob) return true;

        if(ob == null || this.getClass() != ob.getClass()) {
            return false;
        }

        Pessoa outPessoa = (Pessoa) ob;

        if(this.codigo != null && outPessoa.codigo !=  null) {
            return this.codigo.equals(outPessoa.codigo);
        }
        return Objects.equals(this.doc,  outPessoa.doc);
    }

    @Override 
    public int hashCode() {
        return Objects.hash(codigo, doc);
    }

    @Override
    public String toString() {
        return String.format("[Pessoa]: codigo: %d%nNome: %s",codigo, nome);
    }


}

