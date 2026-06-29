package controle;

import modelo.*;
import modelo.enums.NivelAcesso;
import modelo.enums.StatusPedido;
import banco.*;
import controle.io.ConsoleOutput;
import controle.io.Leitor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


public class ControleCadastro {

    private final ProdutoDAO produtoDAO;
    private final FornecedorDAO fornecedorDAO;
    private final TransportadoraDAO transportadoraDAO;
    private final UsuarioDAO usuarioDAO;
    private final PedidoDAO pedidoDAO;
    private final RemessaDAO remessaDAO;
    private final ItemPedidoDAO itemPedidoDAO;
    private final ConsoleOutput out;
    private final Leitor leitor;

    // Carrinho da sessão (para o cliente logado)
    private Carrinho carrinho;

    public ControleCadastro(ProdutoDAO prod, FornecedorDAO f, TransportadoraDAO t,UsuarioDAO u, PedidoDAO p, RemessaDAO r, ItemPedidoDAO i,ConsoleOutput out, Leitor leitor) {
        this.produtoDAO = prod;
        this.fornecedorDAO = f;
        this.transportadoraDAO = t;
        this.usuarioDAO = u;
        this.pedidoDAO = p;
        this.remessaDAO = r;
        this.itemPedidoDAO = i;
        this.out = out;
        this.leitor = leitor;
        this.carrinho = new Carrinho(); 
    }

    public void cadastrarProduto(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado. Apenas administradores podem cadastrar produtos.");
            return;
        }

        try {
            String descricao = leitor.linha("Descrição: ");
            double preco = leitor.real("Preço: ");
            int estoque = leitor.inteiro("Estoque inicial: ");

            Produto produto = new Produto(descricao, preco, estoque);

            String associar = leitor.linha("Deseja associar a um fornecedor? (S/N): ");

            if (associar.equalsIgnoreCase("S")) {
                listarFornecedores();
                int idFornecedor = leitor.inteiro("Código do fornecedor: ");

                Fornecedor fornecedor = fornecedorDAO.buscarPorCodigo(idFornecedor);

                if (fornecedor != null) {
                    produto.adicionarFornecedor(fornecedor);
                } else {
                    System.out.println("Fornecedor não encontrado.");
                }
            }

            produtoDAO.salvar(produto);
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Dados inválidos: " + e.getMessage());
        }
    }

    //3# issue
    // @Vitor == exception de cadstro
    // @ exception de alteração

    public void alterarProduto(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado. Apenas administradores podem alterar produtos.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código do produto a alterar: ");
            Produto p = produtoDAO.buscarPorCodigo(codigo);
            if (p == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            out.exibirProduto(p); 

            String descricao = leitor.linha("Nova descrição (deixe em branco para manter): ");
            if (!descricao.isEmpty()) {
                p.setDescricao(descricao);
            }

            String precoStr = leitor.linha("Novo preço (deixe em branco para manter): ");
            if (!precoStr.isEmpty()) {
                double preco = Double.parseDouble(precoStr);
                p.setPreco(preco);
            }

            String estoqueStr = leitor.linha("Novo estoque (deixe em branco para manter): ");
            if (!estoqueStr.isEmpty()) {
                int estoque = Integer.parseInt(estoqueStr);
                p.setEstoque(estoque);
            }

            produtoDAO.atualizar(p);
            System.out.println("Produto alterado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao alterar produto: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Valor numérico inválido.");
        } catch (IllegalArgumentException e) {
            System.err.println("Dados inválidos: " + e.getMessage());
        }
    }

    public void cadastrarFornecedor(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {
            String nome = leitor.linha("Nome: ");
            String cnpj = leitor.linha("CNPJ: ");
            Fornecedor f = new Fornecedor(nome, cnpj);
            fornecedorDAO.salvar(f);

            System.out.println("Fornecedor cadastrado.");

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void alterarFornecedor(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {
            int codigo = leitor.inteiro("Código do fornecedor a alterar: ");
            Fornecedor f = fornecedorDAO.buscarPorCodigo(codigo);

            if (f == null) {
                System.out.println("Fornecedor não encontrado.");
                return;
            }

            out.exibirFornecedor(f);
            String nome = leitor.linha("Novo nome (deixe em branco para manter): ");

            if (!nome.isEmpty()) {
                f.setNome(nome);
            }

            String cnpj = leitor.linha("Novo CNPJ (deixe em branco para manter): ");
            if (!cnpj.isEmpty()) {
                f.setDocumento(cnpj);
            }

            fornecedorDAO.atualizar(f);
            System.out.println("Fornecedor alterado.");

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarTransportadora(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {
            String nome = leitor.linha("Nome: ");
            String cnpj = leitor.linha("CNPJ: ");
            double taxa = leitor.real("Taxa de frete: ");
            Transportadora t = new Transportadora(nome, cnpj, taxa);
            transportadoraDAO.salvar(t);

            System.out.println("Transportadora cadastrada.");

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void alterarTransportadora(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código da transportadora a alterar: ");
            Transportadora t = transportadoraDAO.buscarPorCodigo(codigo);
            if (t == null) {
                System.out.println("Transportadora não encontrada.");
                return;
            }

            out.exibirTransportadora(t);
            String nome = leitor.linha("Novo nome (deixe em branco para manter): ");

            if (!nome.isEmpty()) {
                t.setNome(nome);
            }

            String taxaStr = leitor.linha("Nova taxa (deixe em branco para manter): ");

            if (!taxaStr.isEmpty()) {
                double taxa = Double.parseDouble(taxaStr);
                t.setTaxaFrete(taxa);
            }

            transportadoraDAO.atualizar(t);
            System.out.println("Transportadora alterada.");

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarUsuario(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {

            String nome = leitor.linha("Nome: ");
            String login = leitor.linha("Login: ");

            if (usuarioDAO.buscarPorLogin(login) != null) {
                System.out.println("Login já utilizado.");
                return;
            }

            String senha = leitor.linha("Senha: ");
            String nivelStr = leitor.linha("Nível (ADMIN/CLIENTE): ");
            NivelAcesso nivel = NivelAcesso.valueOf(nivelStr.toUpperCase());
            Usuario u = new Usuario(nome, "", login, senha, nivel);

            usuarioDAO.salvar(u);
            System.out.println("Usuário cadastrado.");

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Nível inválido.");
        }
    }

    public void alterarUsuario(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {

            int codigo = leitor.inteiro("Código do usuário a alterar: ");
            Usuario u = usuarioDAO.buscarPorCodigo(codigo);

            if (u == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }
            out.exibirUsuario(u);
            String nome = leitor.linha("Novo nome (deixe em branco para manter): ");

            if (!nome.isEmpty()){
                u.setNome(nome);
            }

            String login = leitor.linha("Novo login (deixe em branco para manter): ");
            if (!login.isEmpty() && !login.equals(u.getLogin())) {
                if (usuarioDAO.buscarPorLogin(login) != null) {
                    System.out.println("Login já utilizado.");
                } else {
                    u.setLogin(login);
                }
            }

            String senha = leitor.linha("Nova senha (deixe em branco para manter): ");

            if (!senha.isEmpty()) {
                u.setSenha(senha);
            }

            String nivelStr = leitor.linha("Novo nível (deixe em branco para manter): ");

            if (!nivelStr.isEmpty()) {
                u.setNivelAcesso(NivelAcesso.valueOf(nivelStr.toUpperCase()));
            }

            usuarioDAO.atualizar(u);
            System.out.println("Usuário alterado.");
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }


    public void iniciarCarrinho(Usuario cliente) {
        if (!isCliente(cliente)) {
            System.out.println("Apenas clientes podem usar o carrinho.");
            return;
        }
        this.carrinho = new Carrinho();
        System.out.println("Carrinho iniciado.");
    }

    public void adicionarItemAoCarrinho(Usuario cliente) {
        if (!isCliente(cliente) || carrinho == null) {
            System.out.println("Carrinho não iniciado ou usuário inválido.");
            return;
        }
        try {
            int codProduto = leitor.inteiro("Código do produto: ");
            Produto produto = produtoDAO.buscarPorCodigo(codProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            int quantidade = leitor.inteiro("Quantidade: ");
            carrinho.adicionarItemCarrinho(produto, quantidade);
            System.out.println("Item adicionado ao carrinho.");

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void removerItemDoCarrinho(Usuario cliente) {
        if (!isCliente(cliente) || carrinho == null || carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio ou não iniciado.");
            return;
        }
        try {
            int codProduto = leitor.inteiro("Código do produto a remover: ");
            Produto produto = produtoDAO.buscarPorCodigo(codProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            }
            carrinho.removerItem(produto); 
            System.out.println("Item removido.");

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void visualizarCarrinho(Usuario cliente) {
        if (!isCliente(cliente) || carrinho == null || carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        System.out.println("--- CARRINHO ---");
        for (ItemPedido item : carrinho.getItens()) {
            System.out.printf("  %s | Qtd: %d | Subtotal: R$ %.2f%n",
                    item.getProduto().getDescricao(),
                    item.getQuantidade(),
                    item.getSubTotal());
        }
        System.out.printf("Total: R$ %.2f%n", carrinho.calcularTotal());
    }


    public void finalizarCarrinho(Usuario cliente) {
        if (!isCliente(cliente) || carrinho == null || carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio ou não iniciado.");
            return;
        }

        try {
            Pedido pedido = new Pedido(cliente);
            pedido.concluirPedido(carrinho); 

            listarTransportadoras();
            int codTransp = leitor.inteiro("Código da transportadora: ");
            Transportadora transp = transportadoraDAO.buscarPorCodigo(codTransp);

            if (transp == null) {
                System.out.println("Transportadora inválida. Pedido cancelado.");
                
                for (ItemPedido item : pedido.getItens()) {
                    item.getProduto().adicionarEstoque(item.getQuantidade());
                    produtoDAO.atualizar(item.getProduto());
                }
                return;
            }
            
            Remessa remessa = new Remessa(transp);
            remessa.adicionarPedido(pedido);

            pedidoDAO.salvar(pedido);

            for (ItemPedido item : pedido.getItens()) {
                itemPedidoDAO.salvar(item, pedido.getCodigo());
            }

            for (ItemPedido item : pedido.getItens()) {
                produtoDAO.atualizar(item.getProduto());
            }
            
            remessaDAO.salvar(remessa);

            System.out.println("Pedido #" + pedido.getCodigo() + " finalizado com sucesso!");
            out.exibirPedido(pedido);

        } catch (SQLException e) {
            System.err.println("Erro ao finalizar pedido: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }


    public void alterarStatusPedido(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {
            int codigo = leitor.inteiro("Código do pedido: ");
            Pedido pedido = pedidoDAO.buscarPorCodigo(codigo);
            if (pedido == null) {
                System.out.println("Pedido não encontrado.");
                return;
            }
            out.exibirPedido(pedido);
            System.out.println("Novo status (ENVIADO / CANCELADO): ");
            String novoStatus = leitor.linha("").toUpperCase();

            if (novoStatus.equals("ENVIADO")) {
                pedido.enviarPedido();
                pedidoDAO.atualizar(pedido);
                System.out.println("Pedido enviado com sucesso.");
            } else if (novoStatus.equals("CANCELADO")) {
                pedido.cancelarPedido(); 
                pedidoDAO.atualizar(pedido);
                for (ItemPedido item : pedido.getItens()) {
                    produtoDAO.atualizar(item.getProduto());
                }
                System.out.println("Pedido cancelado.");
            } else {
                System.out.println("Status inválido.");
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarRemessa(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {
            List<Pedido> pedidosDisponiveis = pedidoDAO.listarPorStatus(StatusPedido.EM_PROCESSAMENTO);
            if (pedidosDisponiveis.isEmpty()) {
                System.out.println("Nenhum pedido disponível para remessa.");
                return;
            }
            System.out.println("Pedidos disponíveis:");
            for (Pedido p : pedidosDisponiveis) {
                System.out.printf("  #%d - Cliente: %s%n", p.getCodigo(), p.getCliente().getNome());
            }

            String pedidosSelecionados = leitor.linha("Códigos dos pedidos (separados por vírgula): ");
            String[] codigos = pedidosSelecionados.split(",");

            listarTransportadoras();
            int codTransp = leitor.inteiro("Código da transportadora: ");
            Transportadora transp = transportadoraDAO.buscarPorCodigo(codTransp);
            if (transp == null) {
                System.out.println("Transportadora inválida.");
                return;
            }

            Remessa remessa = new Remessa(transp);
            for (String codStr : codigos) {
                int codPed = Integer.parseInt(codStr.trim());
                Pedido ped = pedidoDAO.buscarPorCodigo(codPed);
                if (ped != null && ped.getStatus() == StatusPedido.EM_PROCESSAMENTO) {
                    remessa.adicionarPedido(ped);
                } 
            }
            if (remessa.getPedidos().isEmpty()) {
                System.out.println("Nenhum pedido válido selecionado.");
                return;
            }

            remessaDAO.salvar(remessa);

            
            for (Pedido p : remessa.getPedidos()) {
                pedidoDAO.atualizarRemessaEFrete(p.getCodigo(), remessa.getCodigo(), p.getValorFrete());
            }
            System.out.println("Remessa criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Código inválido.");
        }
    }

    public void alterarRemessa(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }
        try {
            int codRemessa = leitor.inteiro("Código da remessa a alterar: ");
            Remessa remessa = remessaDAO.buscarPorCodigo(codRemessa);

            if (remessa == null) {
                System.out.println("Remessa não encontrada.");
                return;
            }
            out.exibirRemessa(remessa);
            listarTransportadoras();

            int codTransp = leitor.inteiro("Nova transportadora: ");
            Transportadora nova = transportadoraDAO.buscarPorCodigo(codTransp);
            if (nova == null) {
                System.out.println("Transportadora inválida.");
                return;
            }

            for (Pedido p : remessa.getPedidos()) {
                p.setFrete(nova.getTaxaFrete());
                pedidoDAO.atualizar(p); 
            }
            remessa.setTransportadora(nova);
            remessaDAO.atualizar(remessa);
            System.out.println("Remessa alterada.");
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }


    private boolean isAdmin(Usuario usuario) {
        return usuario != null && usuario.getNivelAcesso() == NivelAcesso.ADMIN;
    }

    private boolean isCliente(Usuario usuario) {
        return usuario != null && usuario.getNivelAcesso() == NivelAcesso.CLIENTE;
    }

    private void listarTransportadoras() {
        try {
            List<Transportadora> lista = transportadoraDAO.listarTodos();
            out.exibirListaTransportadoras(lista);
        } catch (SQLException e) {
            System.err.println("Erro ao listar transportadoras.");
        }
    }

    private void listarFornecedores() {
        try {
            List<Fornecedor> lista = fornecedorDAO.listarTodos();
            out.exibirListaFornecedores(lista);
        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores");
        }
    }
}