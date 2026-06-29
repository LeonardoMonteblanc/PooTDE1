package controle;

import modelo.*;
import modelo.enums.*;
import controle.io.Leitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class MenuPrincipal {

    private final ControleListagem listagem;
    private final ControleCadastro cadastro;
    private final ControleExclusao exclusao;
    private final Leitor leitor;
    private Usuario usuarioLogado;

    public MenuPrincipal(ControleListagem listagem, ControleCadastro cadastro, ControleExclusao exclusao, Leitor leitor) {
        this.listagem = listagem;
        this.cadastro = cadastro;
        this.exclusao = exclusao;
        this.leitor = leitor;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;

        if (usuario != null && usuarioLogado.getNivelAcesso() == NivelAcesso.CLIENTE) {
            cadastro.iniciarCarrinho(usuario); //carrega as informações do carrinho do usuario anterior 
        }
    }

    public void executar() {
        if (usuarioLogado != null && usuarioLogado.getNivelAcesso() == NivelAcesso.ADMIN) {
            executarAdmin(); 
        } else {
            executarCliente();
        }
    }


    private void executarAdmin() {
        boolean sair = true;
        while (sair) {
            System.out.println("\n========== MENU ADMIN ==========\n");
            System.out.println("1. Cadastrar\n2. Alterar\n3. Excluir\n4. Consultar\n5. Pedidos\n6. Remessas\n0. Sair");
            
            switch (leitor.inteiro("Escolha uma opção: ")) {
                case 1: 
                    menuCadastrar(); 
                    break;
                case 2: 
                    menuAlterar(); 
                    break;
                case 3: 
                    menuExcluir(); 
                    break;
                case 4: 
                    menuConsultar(); 
                    break;
                case 5: 
                    menuPedidos(); 
                    break;
                case 6: 
                    menuRemessasAdmin(); 
                    break;

                case 0: 
                    System.out.println("Saindo..."); 
                    sair = false; 
                    break;
                default: 
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void executarCliente() {
        boolean sair = true;
        while (sair) {
            System.out.println("\n========== MENU CLIENTE ==========\n");
            System.out.println("1. Consultar produtos\n2. Carrinho\n3. Meus pedidos\n0. Sair");

            switch (leitor.inteiro("Escolha uma opção: ")) {
                case 1: 
                    menuConsultarCliente(); 
                    break;
                case 2: 
                    menuCarrinho(); 
                    break;
                case 3: 
                    menuPedidos(); 
                    break;
                case 0: 
                    System.out.println("Saindo..."); 
                    sair = false; 
                    break;
                default: 
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void menuCadastrar() {
        System.out.println("\n--- CADASTRAR ---\n");
        System.out.println("1. Produto\n2. Fornecedor\n3. Transportadora\n4. Usuário\n5. Remessa\n0. Voltar");

        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                cadastro.cadastrarProduto(usuarioLogado); 
                break;
            case 2: 
                cadastro.cadastrarFornecedor(usuarioLogado); 
                break;
            case 3: 
                cadastro.cadastrarTransportadora(usuarioLogado); 
                break;
            case 4: 
                cadastro.cadastrarUsuario(usuarioLogado); 
                break;
            case 5: 
                cadastro.cadastrarRemessa(usuarioLogado); 
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void menuAlterar() {
        System.out.println("\n--- ALTERAR ---\n");
        System.out.println("1. Produto\n2. Fornecedor\n3. Transportadora\n4. Usuário\n5. Status do Pedido\n6. Remessa\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                cadastro.alterarProduto(usuarioLogado); 
                break;
            case 2: 
                cadastro.alterarFornecedor(usuarioLogado); 
                break;
            case 3: 
                cadastro.alterarTransportadora(usuarioLogado); 
                break;
            case 4: 
                cadastro.alterarUsuario(usuarioLogado); 
                break;
            case 5: 
                cadastro.alterarStatusPedido(usuarioLogado); 
                break;
            case 6: 
                cadastro.alterarRemessa(usuarioLogado); 
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void menuExcluir() {
        System.out.println("\n--- EXCLUIR ---\n");
        System.out.println("1. Produto\n2. Fornecedor\n3. Transportadora\n4. Usuário\n5. Pedido\n6. Remessa\n0. Voltar");
        
        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                exclusao.excluirProduto(usuarioLogado); 
                break;
            case 2: 
                exclusao.excluirFornecedor(usuarioLogado); 
                break;
            case 3: 
                exclusao.excluirTransportadora(usuarioLogado); 
                break;
            case 4: 
                exclusao.excluirUsuario(usuarioLogado); 
                break;
            case 5: 
                exclusao.excluirPedido(usuarioLogado); 
                break;
            case 6: 
                exclusao.excluirRemessa(usuarioLogado); 
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void menuConsultar() {
        System.out.println("\n--- CONSULTAR / LISTAR ---\n");
        System.out.println("1. Produtos\n2. Fornecedores\n3. Transportadoras\n4. Usuários\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por texto");

                switch (leitor.inteiro("Opção: ")) {
                    case 1: 
                        listagem.listarProdutos();
                        break;
                    case 2: 
                        int cod = leitor.inteiro("Código: "); 
                        listagem.consultarProdutoPorCodigo(cod);
                        break;
                    case 3: 
                        String busca = leitor.linha("Texto: "); 
                        listagem.consultarProdutosPorTexto(busca);
                        break;
                    default: 
                        System.out.println("Opção inválida.");
                }
                break;
            case 2: 
                System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por texto");
                switch (leitor.inteiro("Opção: ")) {
                    case 1: 
                        listagem.listarFornecedores();
                        break;
                    case 2: 
                        int cod = leitor.inteiro("Código: "); 
                        listagem.consultarFornecedorPorCodigo(cod);
                        break;
                    case 3: 
                        String busca = leitor.linha("Texto: "); 
                        listagem.consultarFornecedoresPorTexto(busca);
                        break;
                    default: 
                        System.out.println("Opção inválida.");
                }
                break;            
            case 3: 
                System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por texto");

                switch (leitor.inteiro("Opção: ")) {
                    case 1: 
                        listagem.listarTransportadoras();
                        break;
                    case 2: 
                        int cod = leitor.inteiro("Código: "); 
                        listagem.consultarTransportadoraPorCodigo(cod);
                        break;
                    case 3: 
                        String busca = leitor.linha("Texto: "); 
                        listagem.consultarTransportadorasPorTexto(busca);
                        break;
                    default: 
                        System.out.println("Opção inválida.");
                }
                break;            
            case 4: 
                System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por texto");
                switch (leitor.inteiro("Opção: ")) {
                    case 1: 
                        listagem.listarUsuarios(usuarioLogado);
                        break;
                    case 2: 
                        int cod = leitor.inteiro("Código: "); 
                        listagem.consultarUsuarioPorCodigo(cod,usuarioLogado);
                        break;
                    case 3: 
                        String busca = leitor.linha("Texto: "); 
                        listagem.consultarUsuariosPorTexto(busca, usuarioLogado);
                        break;
                    default: 
                        System.out.println("Opção inválida.");
                }
                break; 
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }


    private void menuPedidos() {
        System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por período\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                listagem.listarPedidos(usuarioLogado); 
                break;
            case 2: 
                listagem.consultarPedidoPorCodigo(leitor.inteiro("Código: "), usuarioLogado); 
                break;
            case 3: 
                consultarPedidosPorPeriodo(); 
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void menuRemessasAdmin() {
        System.out.println("\n--- REMESSAS ---\n1. Listar todos\n2. Consultar por código\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                listagem.listarRemessas(usuarioLogado); 
                break;
            case 2:
                listagem.consultarRemessaPorCodigo(leitor.inteiro("Código: "), usuarioLogado);
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void menuConsultarCliente() {
        System.out.println("\n--- PRODUTOS ---\n");
        System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por texto");

        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                listagem.listarProdutos();
                break;
            case 2: 
                int cod = leitor.inteiro("Código: "); 
                listagem.consultarProdutoPorCodigo(cod);
                break;
            case 3: 
                String busca = leitor.linha("Texto: "); 
                listagem.consultarProdutosPorTexto(busca);
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void menuCarrinho() {
        System.out.println("\n--- CARRINHO ---\n1. Adicionar item\n2. Remover item\n3. Visualizar\n4. Finalizar compra\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: 
                cadastro.adicionarItemAoCarrinho(usuarioLogado); 
                break;
            case 2: 
                cadastro.removerItemDoCarrinho(usuarioLogado); 
                break;
            case 3: 
                cadastro.visualizarCarrinho(usuarioLogado); 
                break;
            case 4: 
                cadastro.finalizarCarrinho(usuarioLogado); 
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void consultarPedidosPorPeriodo() {
        String inicioStr = leitor.linha("Data inicial (dd/MM/yyyy): ");
        String fimStr = leitor.linha("Data final (dd/MM/yyyy): ");
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime inicio = LocalDate.parse(inicioStr, formatter).atStartOfDay();
            LocalDateTime fim = LocalDate.parse(fimStr, formatter).atTime(23, 59, 59);

            listagem.consultarPedidosPorPeriodo(inicio, fim, usuarioLogado);
        } catch (Exception e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
        }
    }
}
