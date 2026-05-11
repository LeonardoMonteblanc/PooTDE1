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
    public void alterarProduto() {
        System.out.println("=== Alterar Produto ===");
        System.out.println("Código do produto a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Produto produto = sistema.getProdutos().stream()
                .filter(p -> p.getCodigo() == cod)
                .findFirst()
                .orElse(null);

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

        Produto produto = sistema.getProdutos().stream()
                .filter(p -> p.getCodigo() == cod)
                .findFirst()
                .orElse(null);

        if (produto == null) {
            System.out.println("✗ Produto não encontrado!");
            return;
        }

        sistema.getProdutos().remove(produto);
        System.out.println("✓ Produto excluído!");
    }

    // ========== FORNECEDORES ==========
    public void alterarFornecedor() {
        System.out.println("=== Alterar Fornecedor ===");
        System.out.println("Código do fornecedor a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Fornecedor fornecedor = sistema.getFornecedores().stream()
                .filter(f -> f.getCodigo() == cod)
                .findFirst()
                .orElse(null);

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

        Fornecedor fornecedor = sistema.getFornecedores().stream()
                .filter(f -> f.getCodigo() == cod)
                .findFirst()
                .orElse(null);

        if (fornecedor == null) {
            System.out.println("✗ Fornecedor não encontrado!");
            return;
        }

        sistema.getFornecedores().remove(fornecedor);
        System.out.println("✓ Fornecedor excluído!");
    }

    // ========== USUÁRIOS ==========
    public void alterarUsuario() {
        System.out.println("=== Alterar Usuário ===");
        System.out.println("Código do usuário a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = sistema.getUsuarios().stream()
                .filter(u -> u.getCodigo() == cod)
                .findFirst()
                .orElse(null);

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

        Usuario usuario = sistema.getUsuarios().stream()
                .filter(u -> u.getCodigo() == cod)
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            System.out.println("✗ Usuário não encontrado!");
            return;
        }

        sistema.getUsuarios().remove(usuario);
        System.out.println("✓ Usuário excluído!");
    }

    // ========== TRANSPORTADORAS ==========
    public void alterarTransportadora() {
        System.out.println("=== Alterar Transportadora ===");
        System.out.println("Código da transportadora a alterar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        Transportadora transportadora = sistema.getTransportadora().stream()
                .filter(t -> t.getCodigo() == cod)
                .findFirst()
                .orElse(null);

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

        Transportadora transportadora = sistema.getTransportadora().stream()
                .filter(t -> t.getCodigo() == cod)
                .findFirst()
                .orElse(null);

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

        Remessa remessa = sistema.getRemessas().stream()
                .filter(r -> r.getCodigo() == cod)
                .findFirst()
                .orElse(null);

        if (remessa == null) {
            System.out.println("✗ Remessa não encontrada!");
            return;
        }

        System.out.println("Código da transportadora (atual: " + remessa.getTransportadora().getCodigo() + "): ");
        String transpStr = scanner.nextLine();
        if (!transpStr.isEmpty()) {
            int codTransp = Integer.parseInt(transpStr);
            Transportadora transp = sistema.getTransportadora().stream()
                    .filter(t -> t.getCodigo() == codTransp)
                    .findFirst()
                    .orElse(null);
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

        Remessa remessa = sistema.getRemessas().stream()
                .filter(r -> r.getCodigo() == cod)
                .findFirst()
                .orElse(null);

        if (remessa == null) {
            System.out.println("✗ Remessa não encontrada!");
            return;
        }

        sistema.getRemessas().remove(remessa);
        System.out.println("✓ Remessa excluída!");
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

        Transportadora transportadora = sistema.getTransportadora().stream()
                .filter(t -> t.getCodigo() == codTransp)
                .findFirst()
                .orElse(null);

        if (transportadora == null) {
            System.out.println("✗ Transportadora não encontrada!");
            return;
        }

        System.out.println("Código do cliente (usuário): ");
        int codCliente = scanner.nextInt();
        scanner.nextLine();

        Usuario cliente = sistema.getUsuarios().stream()
                .filter(u -> u.getCodigo() == codCliente)
                .findFirst()
                .orElse(null);

        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }

        // Criar remessa
        Remessa novaRemessa = new Remessa(codRemessa, transportadora, cliente);
        Pedido novoPedido = new Pedido();

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

            Produto produto = sistema.getProdutos().stream()
                    .filter(p -> p.getCodigo() == codProduto)
                    .findFirst()
                    .orElse(null);

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
