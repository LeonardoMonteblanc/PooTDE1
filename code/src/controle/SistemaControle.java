package controle;

import java.util.List;
import java.util.Scanner;

import modelo.*;

// Consolida todas as informações que o sistema precisa
// a partir de novas instancias isso é levado para outras classes
public class SistemaControle {
    private final List<Fornecedor> fornecedores;
    private final List<Produto> produtos;
    private final List<Remessa> remessas;
    private Usuario usuarioLogado;
    private final List<Usuario> usuarios;
    private final List<Transportadora> transportadoras;
    private final Dados dados = new Dados();
    private final MenuControle menuControle;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Object> lista;
    
    public SistemaControle(Dados d) {
        this.lista = d.getDados();
        usuarios = (List<Usuario>) (Object) lista.get(0);
        fornecedores = (List<Fornecedor>) (Object) lista.get(1);
        produtos = (List<Produto>) (Object) lista.get(2);
        transportadoras = (List<Transportadora>) (Object) lista.get(3);
        remessas = (List<Remessa>) (Object) lista.get(4);

        Listagem listagem = new Listagem(d);
        Consulta consulta = new Consulta(d);
        this.menuControle = new MenuControle(this, listagem, consulta, scanner);
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean validarLogin() {
        String[] credencial = menuControle.inputLogin();
        String login = credencial[0];
        String senha = credencial[1];

        Login l = new Login(usuarios);
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

            String tituloMenu = usuarioLogado.getNivelAcesso() == modelo.NivelAcesso.ADMIN ? "MENU ADMIN" : "MENU CLIENTE";
            System.out.println("\n=== " + tituloMenu + " ===\n1. Produtos\n2.Fornecedores\n3. Usuarios\n4. Transportadoras\n5.Pedidos\n6. Remessas\n0. Sair");
            System.out.println("Digite o modulo que deseja acessar: ");
            opcao = scanner.nextInt();

            switch (opcao) {
              case 1 -> menuControle.gerenciarProdutos();
              case 2 -> menuControle.gerenciarFornecedores();
              case 3 -> menuControle.gerenciarUsuarios();
              case 4 -> menuControle.gerenciarTransportadoras();
              case 5 -> menuControle.gerenciarPedidos();
              case 6 -> menuControle.gerenciarRemessas();
              case 0 -> {
                  System.out.println("Saindo do sistema....");
                  executar = false;
              }
              default -> System.out.println("Opcao inválida!");
            }

        }
    }

    

}
