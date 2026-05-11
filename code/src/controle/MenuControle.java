package controle;

import java.util.Scanner;
import modelo.*;

public class MenuControle {
    private SistemaControle sistema;
    private Listagem sisListagem;
    private Scanner scanner;
    private Consulta sisConsulta;
    private ControleCadastro sisControleCadastro;
    // receber inputs do usuario e manipular o menu
    public MenuControle(SistemaControle sis, Listagem lis, Consulta cos, Scanner scan) {
        this.sistema = sis;
        this.sisListagem = lis;
        this.sisConsulta = cos;
        this.scanner = scan;
        this.sisControleCadastro = new ControleCadastro(sis, scan);
    }

    public String[] inputLogin() {

        System.out.println("Digite o login do usuario: ");
        String login = scanner.nextLine();

        System.out.println("Digite a senha: ");
        String senha = scanner.nextLine();
    
        return new String[] {login, senha};
    }

    public int validarAcao(String menu) {
        int opcao;
        boolean admin = sistema.getUsuarioLogado().getNivelAcesso() == NivelAcesso.ADMIN;

        System.out.println("Digite a acao desejada: ");
        System.out.println("1. Listar");
        System.out.println("2. Consultar");

        if(menu == "pedidos" || admin) {
            System.out.println("3. Cadastrar");
        }

        if(admin) {
            System.out.println("4. Alterar");
            System.out.println("5. Excluir");
        }
        System.out.println("0. Voltar");

        opcao = scanner.nextInt();

        if(admin && (opcao >= 0 && opcao <= 5)) {
            return opcao;
        } 

        if(!admin && (opcao == 0 || opcao == 1 || opcao == 2 || (opcao == 3 && menu == "pedidos"))) {
            return opcao;
        }
        
        System.out.println("Opcao indisponivel para seu nivel de acesso");
        return validarAcao(menu);

        }

    public void gerenciarProdutos() {
        int opcao = validarAcao("produtos");

        switch (opcao) {
            case 1:
                sisListagem.listarProdutos();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    System.out.println("Digite o código:");
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarProdutoPorCodigo(codigo);
                } else if(opcao == 2) {
                    System.out.println("Digite o texto:");
                    scanner.nextLine(); 
                    String texto = scanner.nextLine();
                    sisConsulta.consultarProdutosPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                sisControleCadastro.cadastrarProduto();
                break;
            case 4:
                sisControleCadastro.alterarProduto();
                break;
            case 5:
                sisControleCadastro.excluirProduto();
                break;
            default:
                break;
        }
    }

    public void gerenciarFornecedores() {
        int opcao = validarAcao("fornecedores");

        switch (opcao) {
            case 1:
                sisListagem.listarFornecedores();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    System.out.println("Digite o código:");
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarFornecedorPorCodigo(codigo);
                } else if(opcao == 2) {
                    System.out.println("Digite o texto:");
                    scanner.nextLine(); // limpar buffer
                    String texto = scanner.nextLine();
                    sisConsulta.consultarFornecedoresPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                sisControleCadastro.cadastrarFornecedor();
                break;
            case 4:
                sisControleCadastro.alterarFornecedor();
                break;
            case 5:
                sisControleCadastro.excluirFornecedor();
                break;
            default:
                break;
        }
    }

    public void gerenciarUsuarios() {
        int opcao = validarAcao("usuarios");

        switch (opcao) {
            case 1:
                sisListagem.listarUsuarios();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    System.out.println("Digite o código:");
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarUsuarioPorCodigo(codigo);
                } else if(opcao == 2) {
                    System.out.println("Digite o texto:");
                    scanner.nextLine(); // limpar buffer
                    String texto = scanner.nextLine();
                    sisConsulta.consultarUsuariosPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                sisControleCadastro.cadastrarUsuario();
                break;
            case 4:
                sisControleCadastro.alterarUsuario();
                break;
            case 5:
                sisControleCadastro.excluirUsuario();
                break;
            default:
                break;
        }
    }
    
    public void gerenciarTransportadoras() {
        int opcao = validarAcao("transportadoras");

        switch (opcao) {
            case 1:
                sisListagem.listarTransportadoras();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    System.out.println("Digite o código:");
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarTransportadoraPorCodigo(codigo);
                } else if(opcao == 2) {
                    System.out.println("Digite o texto:");
                    scanner.nextLine(); // limpar buffer
                    String texto = scanner.nextLine();
                    sisConsulta.consultarTransportadorasPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                sisControleCadastro.cadastrarTransportadora();
                break;
            case 4:
                sisControleCadastro.alterarTransportadora();
                break;
            case 5:
                sisControleCadastro.excluirTransportadora();
                break;
            default:
                break;
        }
    }

    public void gerenciarPedidos() {
        int opcao = validarAcao("pedidos");

        switch (opcao) {
            case 1:
                sisListagem.listarRemessas();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarRemessaPorCodigo(codigo);
                } else if(opcao == 2) {
                    String texto = scanner.nextLine();
                    sisConsulta.consultarRemessasPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                sisControleCadastro.cadastrarPedido();
                break;
            case 4:
                sisControleCadastro.alterarPedido();
                break;
            case 5:
                sisControleCadastro.excluirPedido();
                break;
            default:
                break;
        }
    }

    public void gerenciarRemessas() {
        int opcao = validarAcao("remessas");

        switch (opcao) {
            case 1:
                sisListagem.listarRemessas();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    System.out.println("Digite o código:");
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarRemessaPorCodigo(codigo);
                } else if(opcao == 2) {
                    System.out.println("Digite o texto:");
                    scanner.nextLine(); // limpar buffer
                    String texto = scanner.nextLine();
                    sisConsulta.consultarRemessasPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                sisControleCadastro.cadastrarRemessaComPedido();
                break;
            case 4:
                sisControleCadastro.alterarRemessa();
                break;
            case 5:
                sisControleCadastro.excluirRemessa();
                break;
            default:
                break;
        }
    }
    
}
