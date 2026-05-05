package controle;

import java.util.Scanner;
import modelo.*;

public class MenuControle {
    private SistemaControle sistema;
    private Listagem sisListagem;
    private Scanner scanner;
    private Consulta sisConsulta;
    // receber inputs do usuario e manipular o menu
    public MenuControle(SistemaControle sis, Listagem lis, Consulta cos, Scanner scan) {
        this.sistema = sis;
        this.sisListagem = lis;
        this.sisConsulta = cos;
        this.scanner = scan;
    }

    public String[] inputLogin() {

        System.out.println("Digite o login do usuario: ");
        String login = scanner.nextLine();

        System.out.println("Digite a senha: ");
        String senha = scanner.nextLine();
    
        return new String[] {login, senha};
    }

    public int validarAcao() {
        int opcao;
        boolean admin = sistema.getUsuarioLogado().getNivelAcesso() == NivelAcesso.ADMIN;

        System.out.println("Digite a acao desejada: ");
        System.out.println("1. Listar");
        System.out.println("2. Consultar");

        if(admin) {
            System.out.println("3. Cadastrar");
            System.out.println("4. Alterar");
            System.out.println("5. Excluir");
        }
        System.out.println("0. Voltar");

        opcao = scanner.nextInt();

        if(admin && (opcao >= 0 && opcao <= 5)) {
            return opcao;
        } 

        if(!admin && (opcao == 0 || opcao == 1 || opcao == 2)) {
            return opcao;
        }
        
        System.out.println("Opcao indisponivel para seu nivel de acesso");
        return validarAcao();

        }

    public void gerenciarProdutos() {
        int opcao = validarAcao();

        switch (opcao) {
            case 1:
                sisListagem.listarProdutos();
                break;
            case 2:
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
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
                cadastrarProduto();
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
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
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
                cadastrarFornecedor();
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
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
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
                cadastrarUsuario();
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
                System.out.println("1. Consulta por código:\n2. Consulta por texto");
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
                cadastrarTransportadora();
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

    private void cadastrarProduto() {
        System.out.println("=== Cadastrar Produto ===");
        System.out.println("Código: ");
        int cod = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Descrição: ");
        String desc = scanner.nextLine();
        System.out.println("Preço: ");
        String precoStr = scanner.nextLine().replace(",", ".");
        double preco = Double.parseDouble(precoStr);
        Produto novoProd = new Produto(cod, desc, preco);
        sistema.getProdutos().add(novoProd);
        System.out.println("✓ Produto cadastrado!");
    }

    private void cadastrarFornecedor() {
        System.out.println("=== Cadastrar Fornecedor ===");
        System.out.println("Código: ");
        int cod = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("CNPJ: ");
        String cnpj = scanner.nextLine();
        Fornecedor novoFor = new Fornecedor(cod, nome, cnpj);
        sistema.getFornecedores().add(novoFor);
        System.out.println("✓ Fornecedor cadastrado!");
    }

    private void cadastrarUsuario() {
        System.out.println("=== Cadastrar Usuário ===");
        System.out.println("Código: ");
        int cod = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Login: ");
        String login = scanner.nextLine();
        System.out.println("Senha: ");
        String senha = scanner.nextLine();
        System.out.println("Nível de acesso (ADMIN/CLIENTE): ");
        String nivel = scanner.nextLine().toUpperCase();
        NivelAcesso nivelAcesso = NivelAcesso.valueOf(nivel);
        Usuario novoUser = new Usuario(cod, nome, login, senha, nivelAcesso);
        sistema.getUsuarios().add(novoUser);
        System.out.println("✓ Usuário cadastrado!");
    }

    private void cadastrarTransportadora() {
        System.out.println("=== Cadastrar Transportadora ===");
        System.out.println("Código: ");
        int cod = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        Transportadora novaTransp = new Transportadora(cod, nome);
        sistema.getTransportadora().add(novaTransp);
        System.out.println("✓ Transportadora cadastrada!");
    }

    public void gerenciarPedidos() {
        int opcao = validarAcao();

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
