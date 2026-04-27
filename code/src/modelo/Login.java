package modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controle.Dados;

public class Login {
    private String login;
    private String senha;
    private List<Usuario> usuarios = new ArrayList<>();

    public Login(Dados users) {
        this.usuarios = users.usuarios;
        Scanner s = new Scanner(System.in);

        System.out.println("Digite o login do usuario: ");
        this.login = s.nextLine();

        System.out.println("Digite a senha: ");
        this.senha = s.nextLine();
    }

        // recebe o input e verifica se ele existe na lista de usuarios
    public Usuario logar() {
        for(Usuario u : usuarios) {
            if(u.getLogin().equals(this.login) && u.getSenha().equals(this.senha)) {
                System.out.println("Logado como: " + u.getNome() + " - ACESSO: " + u.getNivelAcesso().name());
                return u;
            }
        }
        return null;
    }

}
