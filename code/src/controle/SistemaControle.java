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
    private final Dados dados;
    private final MenuControle menuControle;
    private final Scanner scanner = new Scanner(System.in);

    public SistemaControle(Dados d) {
        this.dados = d;
        usuarios = d.getUsuarios();
        fornecedores = d.getFornecedores();
        produtos = d.getProdutos();
        transportadoras = d.getTransportadoras();
        remessas = d.getRemessas();
        Listagem listagem = new Listagem(this);
        Consulta consulta = new Consulta(this);
        this.menuControle = new MenuControle(this, listagem, consulta, scanner);
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public List<Transportadora> getTransportadora(){
        return transportadoras;
    }

    public Transportadora getTransportadoraByCodigo(int codigo){
        for(Transportadora t : transportadoras){
            if(t.getCodigo() == codigo){
                return t;
            }
        }
        return null;
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

    public Fornecedor getFornecedorByCodigo(int codigo) {
        for (Fornecedor f : fornecedores) {
            if (f.getCodigo() == codigo) {
                return f;
            }
        }
        return null;
    }

    public Usuario getUsuarioByCodigo(int codigo) {
        for (Usuario u : usuarios) {
            if (u.getCodigo() == codigo) {
                return u;
            }
        }
        return null;
    }

    public Remessa getRemessaByCodigo(int codigo) {
        for (Remessa r : remessas) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    public Produto getProdutoByCodigo(int codigo){
        for(Produto p : produtos){
            if(p.getCodigo() == codigo){
                return p;
            }
        }
        return null;
    }

    public int geraCodigoPedido() {
        int maior = 0;
        for (Remessa r : remessas) {
            for (Pedido p : r.getPedidos()) {
                if (p.getCodigo() > maior) {
                    maior = p.getCodigo();
                }
            }
        }
        return maior + 1;
    }

    public int geraCodigoRemessa() {
        int maior = 0;
        for (Remessa r : remessas) {
            if (r.getCodigo() > maior) {
                maior = r.getCodigo();
            }
        }
        return maior + 1;
    }

    public Pedido getPedidoByCodigo(int codigo) {
        for (Remessa r : remessas) {
            for (Pedido p : r.getPedidos()) {
                if (p.getCodigo() == codigo) {
                    return p;
                }
            }
        }
        return null;
    }

    public Remessa getRemessaByPedido(Pedido pedido) {
        for (Remessa r : remessas) {
            if (r.getPedidos().contains(pedido)) {
                return r;
            }
        }
        return null;
    }

    public void removerPedidoDeTodasRemessas(Pedido pedido) {
        for (Remessa r : remessas) {
            r.removerPedido(pedido);
        }
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


    // traz os dados do arquivo para o sistema
    // solicita o login do usuario para permitir as funções 
    
    

}
