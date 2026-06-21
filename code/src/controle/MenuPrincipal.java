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
        if (usuario != null && isCliente()) {
            cadastro.iniciarCarrinho(usuario);
        }
    }

    public void executar() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuário logado. Encerrando.");
            return;
        }
        if (isAdmin()) executarAdmin(); else executarCliente();
    }

    // ==================== MENU ADMIN ====================

    private void executarAdmin() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n========== MENU ADMIN ==========\n");
            System.out.println("1. Cadastrar\n2. Alterar\n3. Excluir\n4. Consultar / Listar\n5. Pedidos\n6. Remessas\n0. Sair");
            switch (leitor.inteiro("Escolha uma opção: ")) {
                case 1: menuCadastrar(); break;
                case 2: menuAlterar(); break;
                case 3: menuExcluir(); break;
                case 4: menuConsultar(); break;
                case 5: menuPedidosAdmin(); break;
                case 6: menuRemessasAdmin(); break;
                case 0: System.out.println("Saindo..."); sair = true; break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void executarCliente() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n========== MENU CLIENTE ==========\n");
            System.out.println("1. Consultar / Listar produtos\n2. Carrinho\n3. Meus pedidos\n0. Sair");
            switch (leitor.inteiro("Escolha uma opção: ")) {
                case 1: menuConsultarCliente(); break;
                case 2: menuCarrinho(); break;
                case 3: menuPedidosCliente(); break;
                case 0: System.out.println("Saindo..."); sair = true; break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void menuCadastrar() {
        System.out.println("\n--- CADASTRAR ---\n");
        System.out.println("1. Produto\n2. Fornecedor\n3. Transportadora\n4. Usuário\n5. Remessa\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: cadastro.cadastrarProduto(usuarioLogado); break;
            case 2: cadastro.cadastrarFornecedor(usuarioLogado); break;
            case 3: cadastro.cadastrarTransportadora(usuarioLogado); break;
            case 4: cadastro.cadastrarUsuario(usuarioLogado); break;
            case 5: cadastro.cadastrarRemessa(usuarioLogado); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void menuAlterar() {
        System.out.println("\n--- ALTERAR ---\n");
        System.out.println("1. Produto\n2. Fornecedor\n3. Transportadora\n4. Usuário\n5. Status do Pedido\n6. Remessa\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: cadastro.alterarProduto(usuarioLogado); break;
            case 2: cadastro.alterarFornecedor(usuarioLogado); break;
            case 3: cadastro.alterarTransportadora(usuarioLogado); break;
            case 4: cadastro.alterarUsuario(usuarioLogado); break;
            case 5: cadastro.alterarStatusPedido(usuarioLogado); break;
            case 6: cadastro.alterarRemessa(usuarioLogado); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void menuExcluir() {
        System.out.println("\n--- EXCLUIR ---\n");
        System.out.println("1. Produto\n2. Fornecedor\n3. Transportadora\n4. Usuário\n5. Pedido\n6. Remessa\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: exclusao.excluirProduto(usuarioLogado); break;
            case 2: exclusao.excluirFornecedor(usuarioLogado); break;
            case 3: exclusao.excluirTransportadora(usuarioLogado); break;
            case 4: exclusao.excluirUsuario(usuarioLogado); break;
            case 5: exclusao.excluirPedido(usuarioLogado); break;
            case 6: exclusao.excluirRemessa(usuarioLogado); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void menuConsultar() {
        System.out.println("\n--- CONSULTAR / LISTAR ---\n");
        System.out.println("1. Produtos\n2. Fornecedores\n3. Transportadoras\n4. Usuários\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: consultarGenerico(listagem::listarProdutos, listagem::consultarProdutoPorCodigo, listagem::consultarProdutosPorTexto); break;
            case 2: consultarGenerico(listagem::listarFornecedores, listagem::consultarFornecedorPorCodigo, listagem::consultarFornecedoresPorTexto); break;
            case 3: consultarGenerico(listagem::listarTransportadoras, listagem::consultarTransportadoraPorCodigo, listagem::consultarTransportadorasPorTexto); break;
            case 4: consultarGenerico(() -> listagem.listarUsuarios(usuarioLogado),
                    cod -> listagem.consultarUsuarioPorCodigo(cod, usuarioLogado),
                    texto -> listagem.consultarUsuariosPorTexto(texto, usuarioLogado)); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void consultarGenerico(Runnable listarTodos, IntConsumer porCodigo, Consumer<String> porTexto) {
        System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por texto");
        switch (leitor.inteiro("Opção: ")) {
            case 1: listarTodos.run(); break;
            case 2: porCodigo.accept(leitor.inteiro("Código: ")); break;
            case 3: porTexto.accept(leitor.linha("Texto: ")); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void menuPedidosAdmin() {
        menuPedidos();
    }

    private void menuPedidosCliente() {
        System.out.println("\n--- MEUS PEDIDOS ---\n");
        menuPedidos();
    }

    private void menuPedidos() {
        System.out.println("1. Listar todos\n2. Consultar por código\n3. Consultar por período\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: listagem.listarPedidos(usuarioLogado); break;
            case 2: listagem.consultarPedidoPorCodigo(leitor.inteiro("Código: "), usuarioLogado); break;
            case 3: consultarPedidosPorPeriodo(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void menuRemessasAdmin() {
        System.out.println("\n--- REMESSAS ---\n1. Listar todos\n2. Consultar por código\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: listagem.listarRemessas(usuarioLogado); break;
            case 2:
                leitor.inteiro("Código: ");
                System.out.println("Funcionalidade em desenvolvimento. Use 'Listar remessas'.");
                break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }


    private void menuConsultarCliente() {
        System.out.println("\n--- PRODUTOS ---\n");
        consultarGenerico(listagem::listarProdutos, listagem::consultarProdutoPorCodigo, listagem::consultarProdutosPorTexto);
    }

    private void menuCarrinho() {
        System.out.println("\n--- CARRINHO ---\n1. Adicionar item\n2. Remover item\n3. Visualizar\n4. Finalizar compra\n0. Voltar");
        switch (leitor.inteiro("Opção: ")) {
            case 1: cadastro.adicionarItemAoCarrinho(usuarioLogado); break;
            case 2: cadastro.removerItemDoCarrinho(usuarioLogado); break;
            case 3: cadastro.visualizarCarrinho(usuarioLogado); break;
            case 4: cadastro.finalizarCarrinho(usuarioLogado); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
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

    private boolean isAdmin() {
        return usuarioLogado != null && usuarioLogado.getNivelAcesso() == NivelAcesso.ADMIN;
    }

    private boolean isCliente() {
        return usuarioLogado != null && usuarioLogado.getNivelAcesso() == NivelAcesso.CLIENTE;
    }
}
