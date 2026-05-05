package controle;

import java.util.Scanner;
import modelo.NivelAcesso;

public class MenuControle {
    private SistemaControle sistema;
    private Listagem sisListagem;
    private Scanner scanner;
    private Consulta sisConsulta;
    // receber inputs do usuario e manipular o menu
    public MenuControle(SistemaControle sis, Listagem lis, Consulta cos) {
        this.sistema = sis;
        this.sisListagem = lis;
        this.sisConsulta = cos;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Solicita ao usuário o login e a senha via console.
     *
     * <p>O método lê as credenciais digitadas pelo usuário e as retorna em um
     * vetor de strings, onde:
     * <ul>
     *   <li>índice {@code 0} contém o login</li>
     *   <li>índice {@code 1} contém a senha</li>
     * </ul>
     *
     * @return um array de {@code String} contendo login e senha do usuário
     */
    public String[] inputLogin() {

        System.out.println("Digite o login do usuario: ");
        String login = scanner.nextLine();

        System.out.println("Digite a senha: ");
        String senha = scanner.nextLine();
    
        return new String[] {login, senha};
    }

    public int validarAcao() {
        int opcao;
        System.out.println("Digite a acao desejada: ");
        System.out.println("1. Listar");
        System.out.println("2. Consultar");

        if(sistema.getUsuarioLogado().getNivelAcesso() == NivelAcesso.ADMIN) {
            System.out.println("3. Cadastrar");
            System.out.println("4. Alterar");
            System.out.println("5. Excluir");
        }
        System.out.println("0. Voltar");

        opcao = scanner.nextInt();

        if(sistema.getUsuarioLogado().getNivelAcesso() == NivelAcesso.ADMIN) {
            return opcao;
        } else if(opcao != 1 || opcao != 2) {
            System.out.println("Opcao invalida para seu perfil de acesso!");
            validarAcao();
        }
        return opcao;

        }

    public void gerenciarProdutos() {
        int opcao = validarAcao();

        switch (opcao) {
            case 1:
                sisListagem.listarProdutos();
                break;
            case 2:
                System.out.println("1. Consulta por texto:\n2. Consulta por código");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarProdutoPorCodigo(codigo);
                } else if(opcao == 2) {
                    String texto = scanner.nextLine();
                    sisConsulta.consultarProdutosPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                System.out.println("cadastrar");
                break;
            case 4:
                System.out.println("alterar");
                break;
            case 5:
                System.out.println("excluir");
                break;
            default:
                break;
        }
    }

        public void gerenciarFornecedores() {
        int opcao = validarAcao();

        switch (opcao) {
            case 1:
                sisListagem.listarFornecedores();
                break;
            case 2:
                System.out.println("1. Consulta por texto:\n2. Consulta por código");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarFornecedorPorCodigo(codigo);
                } else if(opcao == 2) {
                    String texto = scanner.nextLine();
                    sisConsulta.consultarFornecedoresPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                System.out.println("cadastrar");
                break;
            case 4:
                System.out.println("alterar");
                break;
            case 5:
                System.out.println("excluir");
                break;
            default:
                break;
        }
    }
    public void gerenciarUsuarios() {
        int opcao = validarAcao();

        switch (opcao) {
            case 1:
                sisListagem.listarUsuarios();
                break;
            case 2:
                System.out.println("1. Consulta por texto:\n2. Consulta por código");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarUsuarioPorCodigo(codigo);
                } else if(opcao == 2) {
                    String texto = scanner.nextLine();
                    sisConsulta.consultarUsuariosPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                System.out.println("cadastrar");
                break;
            case 4:
                System.out.println("alterar");
                break;
            case 5:
                System.out.println("excluir");
                break;
            default:
                break;
        }
    }
    
    public void gerenciarTransportadoras() {
        int opcao = validarAcao();

        switch (opcao) {
            case 1:
                sisListagem.listarTransportadoras();
                break;
            case 2:
                System.out.println("1. Consulta por texto:\n2. Consulta por código");
                opcao = scanner.nextInt();
                if(opcao == 1) {
                    int codigo = scanner.nextInt();
                    sisConsulta.consultarTransportadoraPorCodigo(codigo);
                } else if(opcao == 2) {
                    String texto = scanner.nextLine();
                    sisConsulta.consultarTransportadorasPorTexto(texto);
                } else {
                    System.out.println("Opcao inválida!");
                }
                break;
            case 3:
                System.out.println("cadastrar");
                break;
            case 4:
                System.out.println("alterar");
                break;
            case 5:
                System.out.println("excluir");
                break;
            default:
                break;
        }
    }

    public void gerenciarPedidos() {
        int opcao = validarAcao();

        switch (opcao) {
            case 1:
                sisListagem.listarRemessas();
                break;
            case 2:
                System.out.println("1. Consulta por texto:\n2. Consulta por código");
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
                System.out.println("cadastrar");
                break;
            case 4:
                System.out.println("alterar");
                break;
            case 5:
                System.out.println("excluir");
                break;
            default:
                break;
        }
    }
    
}
