package modelo;

public class Usuario extends Pessoa {
    private String login;
    private String senha;
    private NivelAcesso nivelAcesso;

    public Usuario(int codigo, String nome, String login, String senha, NivelAcesso nivelAcesso) {
        super(codigo, nome);
        this.login = login;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }
// GETS ======================================================
    public String getLogin() {
        return this.login;
    }

    public String getSenha() {
        return this.senha;
    }

    public NivelAcesso getNivelAcesso() {
        return this.nivelAcesso;
    }

    public boolean temPermissao(Permissao p) {
        return nivelAcesso.temPermissao(p);
    }
// SETS ======================================================
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    @Override
    public String toString() {
        return String.format("[Usuario]: login: %s%nsenha:%s%nnivel acesso: %s", login, senha, nivelAcesso);
    }
}
