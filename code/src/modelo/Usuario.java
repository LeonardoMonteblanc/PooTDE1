package modelo;

public class Usuario {
    private String login;
    private String senha;
    private String nivelAcesso;

    public Usuario(String login, String senha, String acesso) {
        this.login = login;
        this.senha = senha;
        this.nivelAcesso = acesso;
    }
// GETS ======================================================
    public String getLogin() {
        return this.login;
    }

    public String getSenha() {
        return this.senha;
    }

    public String nivelAcesso() {
        return this.nivelAcesso;
    }
// SETS ======================================================
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivelAcesso(String acesso) {
        this.nivelAcesso = acesso;
    }

    @Override
    public String toString() {
        return String.format("[Usuario]: login: %s%nsenha:%s%nnivel acesso: %s", login, senha, nivelAcesso);
    }
}
