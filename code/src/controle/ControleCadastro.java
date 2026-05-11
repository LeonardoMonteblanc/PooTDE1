package controle;

import java.util.Scanner;
import modelo.*;

public class ControleCadastro {
    private SistemaControle sistema;
    private Scanner scanner;

    public ControleCadastro(SistemaControle sistema, Scanner scanner) {
        this.sistema = sistema;
        this.scanner = scanner;
    }

    // ========== PRODUTOS ==========
    public void cadastrarProduto() {
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

    public void alterarProduto() {
        System.out.println("=== Alterar Produto ===");
        System.out.println("Código do produto a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Produto produto = sistema.getProdutoByCodigo(cod);

        if (produto == null) {
            System.out.println("✗ Produto não encontrado!");
            return;
        }

        System.out.println("Descrição (atual: " + produto.getDescricao() + "): ");
        String desc = scanner.nextLine();
        if (!desc.isEmpty()) {
            produto.setDescricao(desc);
        }

        System.out.println("Preço (atual: " + produto.getPreco() + "): ");
        String precoStr = scanner.nextLine().replace(",", ".");
        if (!precoStr.isEmpty()) {
            produto.setPreco(Double.parseDouble(precoStr));
        }

        System.out.println("✓ Produto alterado!");
    }

    public void excluirProduto() {
        System.out.println("=== Excluir Produto ===");
        System.out.println("Código do produto a excluir: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Produto produto = sistema.getProdutoByCodigo(cod);

        if (produto == null) {
            System.out.println("✗ Produto não encontrado!");
            return;
        }

        sistema.getProdutos().remove(produto);
        System.out.println("✓ Produto excluído!");
    }

    // ========== FORNECEDORES ==========
    public void cadastrarFornecedor() {
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

    public void alterarFornecedor() {
        System.out.println("=== Alterar Fornecedor ===");
        System.out.println("Código do fornecedor a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Fornecedor fornecedor = sistema.getFornecedorByCodigo(cod);

        if (fornecedor == null) {
            System.out.println("✗ Fornecedor não encontrado!");
            return;
        }

        System.out.println("Nome (atual: " + fornecedor.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            fornecedor.setNome(nome);
        }

        System.out.println("CNPJ (atual: " + fornecedor.getCnpj() + "): ");
        String cnpj = scanner.nextLine();
        if (!cnpj.isEmpty()) {
            fornecedor.setCnpj(cnpj);
        }

        System.out.println("✓ Fornecedor alterado!");
    }

    public void excluirFornecedor() {
        System.out.println("=== Excluir Fornecedor ===");
        System.out.println("Código do fornecedor a excluir: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Fornecedor fornecedor = sistema.getFornecedorByCodigo(cod);

        if (fornecedor == null) {
            System.out.println("✗ Fornecedor não encontrado!");
            return;
        }

        sistema.getFornecedores().remove(fornecedor);
        System.out.println("✓ Fornecedor excluído!");
    }

    // ========== USUÁRIOS ==========
    public void cadastrarUsuario() {
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

    public void alterarUsuario() {
        System.out.println("=== Alterar Usuário ===");
        System.out.println("Código do usuário a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = sistema.getUsuarioByCodigo(cod);

        if (usuario == null) {
            System.out.println("✗ Usuário não encontrado!");
            return;
        }

        System.out.println("Nome (atual: " + usuario.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            usuario.setNome(nome);
        }

        System.out.println("Login (atual: " + usuario.getLogin() + "): ");
        String login = scanner.nextLine();
        if (!login.isEmpty()) {
            usuario.setLogin(login);
        }

        System.out.println("Senha (atual: " + usuario.getSenha() + "): ");
        String senha = scanner.nextLine();
        if (!senha.isEmpty()) {
            usuario.setSenha(senha);
        }

        System.out.println("Nível de acesso (atual: " + usuario.getNivelAcesso() + ") - (ADMIN/CLIENTE): ");
        String nivel = scanner.nextLine().toUpperCase();
        if (!nivel.isEmpty()) {
            usuario.setNivelAcesso(NivelAcesso.valueOf(nivel));
        }

        System.out.println("✓ Usuário alterado!");
    }

    public void excluirUsuario() {
        System.out.println("=== Excluir Usuário ===");
        System.out.println("Código do usuário a excluir: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = sistema.getUsuarioByCodigo(cod);

        if (usuario == null) {
            System.out.println("✗ Usuário não encontrado!");
            return;
        }

        sistema.getUsuarios().remove(usuario);
        System.out.println("✓ Usuário excluído!");
    }

    // ========== TRANSPORTADORAS ==========
    public void cadastrarTransportadora() {
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

    public void alterarTransportadora() {
        System.out.println("=== Alterar Transportadora ===");
        System.out.println("Código da transportadora a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Transportadora transportadora = sistema.getTransportadoraByCodigo(cod);

        if (transportadora == null) {
            System.out.println("✗ Transportadora não encontrada!");
            return;
        }

        System.out.println("Nome (atual: " + transportadora.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            transportadora.setNome(nome);
        }

        System.out.println("✓ Transportadora alterada!");
    }

    public void excluirTransportadora() {
        System.out.println("=== Excluir Transportadora ===");
        System.out.println("Código da transportadora a excluir: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Transportadora transportadora = sistema.getTransportadoraByCodigo(cod);

        if (transportadora == null) {
            System.out.println("✗ Transportadora não encontrada!");
            return;
        }

        sistema.getTransportadora().remove(transportadora);
        System.out.println("✓ Transportadora excluída!");
    }

    // ========== REMESSAS ==========
    public void alterarRemessa() {
        System.out.println("=== Alterar Remessa ===");
        System.out.println("Código da remessa a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Remessa remessa = sistema.getRemessaByCodigo(cod);

        if (remessa == null) {
            System.out.println("✗ Remessa não encontrada!");
            return;
        }

        System.out.println("Código da transportadora (atual: " + remessa.getTransportadora().getCodigo() + "): ");
        String transpStr = scanner.nextLine();
        if (!transpStr.isEmpty()) {
            int codTransp = Integer.parseInt(transpStr);
            Transportadora transp = sistema.getTransportadoraByCodigo(codTransp);
            if (transp != null) {
                remessa.setTransportadora(transp);
            }
        }

        System.out.println("✓ Remessa alterada!");
    }

    public void excluirRemessa() {
        System.out.println("=== Excluir Remessa ===");
        System.out.println("Código da remessa a excluir: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Remessa remessa = sistema.getRemessaByCodigo(cod);

        if (remessa == null) {
            System.out.println("✗ Remessa não encontrada!");
            return;
        }

        sistema.getRemessas().remove(remessa);
        System.out.println("✓ Remessa excluída!");
    }

    // ========== PEDIDOS ==========
    public void alterarPedido() {
        System.out.println("=== Alterar Pedido ===");
        Listagem listagem = new Listagem(sistema);
        listagem.listarPedidos();

        System.out.println("Código do pedido a alterar: ");
        int codPedido = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = sistema.getPedidoByCodigo(codPedido);
        if (pedido == null) {
            System.out.println("✗ Pedido não encontrado!");
            return;
        }

        boolean editando = true;
        while (editando) {
            System.out.println("\n--- Itens atuais ---");
            listagem.imprimirItensPedido(pedido.getItens());
            System.out.println("\n1. Adicionar item");
            System.out.println("2. Remover item");
            System.out.println("3. Alterar quantidade de um item");
            System.out.println("0. Finalizar alteração");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listagem.listarProdutos();
                    System.out.println("Código do produto: ");
                    int codProd = scanner.nextInt();
                    scanner.nextLine();
                    Produto produto = sistema.getProdutoByCodigo(codProd);
                    if (produto == null) {
                        System.out.println("✗ Produto não encontrado!");
                        break;
                    }
                    System.out.println("Quantidade: ");
                    int qtd = scanner.nextInt();
                    scanner.nextLine();
                    pedido.adicionarItem(produto, qtd);
                    System.out.println("✓ Item adicionado!");
                    break;
                case 2:
                    System.out.println("Código do produto a remover: ");
                    int codRemover = scanner.nextInt();
                    scanner.nextLine();
                    Produto prodRemover = sistema.getProdutoByCodigo(codRemover);
                    if (prodRemover == null) {
                        System.out.println("✗ Produto não encontrado!");
                        break;
                    }
                    pedido.removerItem(prodRemover);
                    System.out.println("✓ Item removido!");
                    break;
                case 3:
                    System.out.println("Código do produto a alterar quantidade: ");
                    int codAlterar = scanner.nextInt();
                    scanner.nextLine();
                    ItemPedido itemAlterar = pedido.getItemByCodigoProduto(codAlterar);
                    if (itemAlterar == null) {
                        System.out.println("✗ Item não encontrado no pedido!");
                        break;
                    }
                    System.out.println("Nova quantidade (atual: " + itemAlterar.getQuantidade() + "): ");
                    int novaQtd = scanner.nextInt();
                    scanner.nextLine();
                    itemAlterar.setQuantidade(novaQtd);
                    System.out.println("✓ Quantidade alterada!");
                    break;
                case 0:
                    editando = false;
                    break;
                default:
                    System.out.println("Opcao inválida!");
            }
        }

        System.out.println("✓ Pedido alterado!");
    }

    public void excluirPedido() {
        System.out.println("=== Excluir Pedido ===");
        Listagem listagem = new Listagem(sistema);
        listagem.listarPedidos();

        System.out.println("Código do pedido a excluir: ");
        int codPedido = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = sistema.getPedidoByCodigo(codPedido);
        if (pedido == null) {
            System.out.println("✗ Pedido não encontrado!");
            return;
        }

        Remessa remessa = sistema.getRemessaByPedido(pedido);
        if (remessa != null) {
            remessa.removerPedido(pedido);
        }
        System.out.println("✓ Pedido excluído!");
    }

    public void cadastrarPedido() {
        System.out.println("=== Fazer Pedido ===");

        Listagem listagem = new Listagem(sistema);
        Pedido pedido = new Pedido(sistema.geraCodigoPedido());
        boolean addProduto = true;

        while (addProduto) {
            listagem.listarProdutos();
            System.out.println("Digite o código do produto (ou 0 para finalizar): ");
            int codigoProduto = scanner.nextInt();

            if (codigoProduto == 0) {
                addProduto = false;
                continue;
            }

            Produto produtoSelecionado = sistema.getProdutoByCodigo(codigoProduto);
            if (produtoSelecionado == null) {
                System.out.println("Produto não encontrado");
                continue;
            }

            System.out.println("Produto: " + produtoSelecionado.getDescricao() + "= R$ " + String.format("%.2f", produtoSelecionado.getPreco()));
            System.out.println("Digite a quantidade: ");
            int quantidade = scanner.nextInt();

            pedido.adicionarItem(produtoSelecionado, quantidade);
            System.out.println("Item adicionado");
        }

        System.out.println("=== Resumo do Pedido ===");
        listagem.imprimirItensPedido(pedido.getItens());

        double total = 0;
        for (ItemPedido item : pedido.getItens()) {
            total += item.getQuantidade() * item.getProduto().getPreco();
        }
        System.out.printf("Total: R$ %.2f%n", total);

        listagem.listarTransportadoras();
        System.out.println("Digite o código da transportadora (codigo): ");
        int codigoTransp = scanner.nextInt();

        Transportadora transportadora = sistema.getTransportadoraByCodigo(codigoTransp);
        if (transportadora == null) {
            System.out.println("transportadora nao encontrada");
            return;
        }

        Remessa remessa = new Remessa(sistema.getRemessas().size() + 1, transportadora, sistema.getUsuarioLogado());
        remessa.adicionarPedido(pedido);
        sistema.getRemessas().add(remessa);

        System.out.println("Pedido #" + pedido.getCodigo() + " incluido com sucesso!");
    }

    // ========== CADASTRO DE REMESSAS COM PEDIDOS ==========
    public void cadastrarRemessaComPedido() {
        System.out.println("=== Cadastrar Remessa com Pedido ===");
        System.out.println("Código da remessa: ");
        int codRemessa = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Código da transportadora: ");
        int codTransp = scanner.nextInt();
        scanner.nextLine();

        Transportadora transportadora = sistema.getTransportadoraByCodigo(codTransp);

        if (transportadora == null) {
            System.out.println("✗ Transportadora não encontrada!");
            return;
        }

        System.out.println("Código do cliente (usuário): ");
        int codCliente = scanner.nextInt();
        scanner.nextLine();

        Usuario cliente = sistema.getUsuarioByCodigo(codCliente);

        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }

        // Criar remessa
        Remessa novaRemessa = new Remessa(codRemessa, transportadora, cliente);
        Pedido novoPedido = new Pedido(sistema.geraCodigoPedido());

        // Adicionar itens ao pedido
        boolean adicionarMais = true;
        new Listagem(sistema).listarProdutos();
        while (adicionarMais) {
            System.out.println("\n--- Adicionar Item ao Pedido ---");
            System.out.println("Código do produto (ou 0 para finalizar): ");
            int codProduto = scanner.nextInt();
            scanner.nextLine();

            if (codProduto == 0) {
                adicionarMais = false;
                break;
            }

            Produto produto = sistema.getProdutoByCodigo(codProduto);

            if (produto == null) {
                System.out.println("✗ Produto não encontrado!");
                continue;
            }

            System.out.println("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            novoPedido.adicionarItem(produto, quantidade);
            System.out.println("✓ Item adicionado: " + produto.getDescricao() + " (qtd: " + quantidade + ")");
        }

        if (novoPedido.getItens().isEmpty()) {
            System.out.println("✗ Pedido sem itens! Remessa não criada.");
            return;
        }

        // Adicionar pedido à remessa e remessa ao sistema
        novaRemessa.adicionarPedido(novoPedido);
        sistema.getRemessas().add(novaRemessa);
        System.out.println("✓ Remessa cadastrada com sucesso! Código: " + codRemessa);
    }
}
