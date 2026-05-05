package controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.*;

// Consolida todas as informações que o sistema precisa
// a partir de novas instancias isso é levado para outras classes
public class SistemaControle {
    private List<Fornecedor> fornecedores = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Remessa> remessas = new ArrayList<>();
    private Usuario usuarioLogado;
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Transportadora> transportadoras  = new ArrayList<>();
    private Dados dados;
    private MenuControle menuControle;

    public SistemaControle(Dados d) {
        this.dados = d;
        usuarios = d.usuarios;
        fornecedores = d.fornecedores;
        produtos = d.produtos;
        transportadoras = d.transportadoras;
        remessas = d.remessas;
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public List<Transportadora> getTransportadora(){
        return transportadoras;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    
    public List<Remessa> getRemessas() {
        return remessas;
    }

    public List<Produto> getProdutos(){
        return produtos;
    }

    public boolean validarLogin() {
        String[] credencial = menuControle.inputLogin();
        String login = credencial[0];
        String senha = credencial[1];

        Login l = new Login(dados);
        usuarioLogado = l.logar(login, senha);

        if(usuarioLogado == null) {
            System.out.println("Senha ou usuario inválido");
            return false;
        }
        return true;
    }

    public void menu(){
        int opcao;
        boolean executar = true;
        while (executar) {
            Scanner s = new Scanner(System.in);

            System.out.println("\n=== MENU ADMIN ===\n1. Produtos\n2.Fornecedores\n3. Usuarios\n4. Transportadoras\n5.Pedidos\n6. Remessas\n0. Sair");
            System.out.println("Digite o modulo que deseja acessar: ");
            opcao = s.nextInt();

            switch (opcao) {
              case 1 -> menuControle.gerenciarProdutos();
              case 2 -> menuControle.gerenciarFornecedores();
              case 3 -> menuControle.gerenciarUsuarios();
              case 4 -> menuControle.gerenciarTransportadoras();
              case 5 -> menuControle.gerenciarPedidos();
              case 0 -> {
                  System.out.println("Saindo do sistema....'");
                  executar = false;
              }
              default -> System.out.println("Opcao inválida!");
            }

        }
    }


    // traz os dados do arquivo para o sistema
    // solicita o login do usuario para permitir as funções 
    
    

}
