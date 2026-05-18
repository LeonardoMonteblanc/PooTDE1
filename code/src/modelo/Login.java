package modelo;
import java.util.List;

import controle.Dados;

public class Login {
    private List<Usuario> usuarios;

    public Login(Dados d) {
        this.usuarios = d.getUsuarios();
    }
    // recebe o input e verifica se ele existe na lista de usuarios
    public Usuario logar(String login, String senha) {
        for(Usuario u : usuarios) {
            if(u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                System.out.println("Logado como: " + u.getNome() + " - ACESSO: " + u.getNivelAcesso().name());
                return u;
            }
        }
        return null;
    }

}
