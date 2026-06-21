package modelo;
import exceptions.SistemaException;
import modelo.enums.NivelAcesso;

public class Usuario extends Pessoa {

    private String login;
    private String senha;
    private NivelAcesso nivelAcesso;

    public Usuario(Integer codigo, String nome, String cpf, String login, String senha, NivelAcesso nivelAcesso) {
        super(codigo, nome, cpf);
        setLogin(login);
        setSenha(senha);
        setNivelAcesso(nivelAcesso);
    }

    public void fazerLogin(String senha, String login) throws SistemaException {
        if(!this.senha.equals(senha) || !this.login.equals(login)) {
            throw new SistemaException("Login ou senha incorreto, tente novamente");
        }
    }

    // #2 Issue (@Vitor)
    // Criar exception para autenticação? 

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
// SETS ======================================================
    public void setLogin(String login) {
        if(login == null || login.isBlank()) {
            this.login = getNome();
        } else {
            this.login = login;
        }
    }

    
    public void setSenha(String senha) {
        if(senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é inválido");
        }
        this.senha = senha;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        if(nivelAcesso == null) {
            throw new IllegalArgumentException("O nivel de acesso é obrigatório");
        }
        this.nivelAcesso = nivelAcesso;
    }
    
    @Override
    public String toString() {
        return String.format("[Usuario] Login: %s | Nível: %s", login, nivelAcesso);
    }
}
