package controle;

import modelo.*;
import modelo.enums.NivelAcesso;
import modelo.enums.StatusPedido;
import banco.*;
import controle.io.ConsoleOutput;
import controle.io.Leitor;

import java.sql.SQLException;

public class ControleExclusao {

    private final ProdutoDAO produtoDAO;
    private final FornecedorDAO fornecedorDAO;
    private final TransportadoraDAO transportadoraDAO;
    private final UsuarioDAO usuarioDAO;
    private final PedidoDAO pedidoDAO;
    private final RemessaDAO remessaDAO;
    private final ItemPedidoDAO itemPedidoDAO;
    private final ConsoleOutput out;
    private final Leitor leitor;

    public ControleExclusao(ProdutoDAO prod, FornecedorDAO f, TransportadoraDAO t,UsuarioDAO u, PedidoDAO p, RemessaDAO r, ItemPedidoDAO i, ConsoleOutput out, Leitor leitor) {
        this.produtoDAO = prod;
        this.fornecedorDAO = f;
        this.transportadoraDAO = t;
        this.usuarioDAO = u;
        this.pedidoDAO = p;
        this.remessaDAO = r;
        this.itemPedidoDAO = i;
        this.out = out;
        this.leitor = leitor;
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario != null && usuario.getNivelAcesso() == NivelAcesso.ADMIN;
    }

    public void excluirProduto(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado. Apenas administradores podem excluir produtos.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código do produto a excluir: ");
            Produto p = produtoDAO.buscarPorCodigo(codigo);
            if (p == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            out.exibirProduto(p);

            if (pedidoDAO.existeProdutoEmPedidoPendente(codigo)) {
                System.out.println("Não é possível excluir: produto está em pedidos pendentes.");
                return;
            }

            String confirm = leitor.linha("Confirma exclusão? (S/N): ");
            if (confirm.equalsIgnoreCase("S")) {
                produtoDAO.excluir(codigo);
                System.out.println("Produto excluído com sucesso.");
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
        }
    }

    public void excluirFornecedor(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código do fornecedor a excluir: ");
            Fornecedor f = fornecedorDAO.buscarPorCodigo(codigo);
            if (f == null) {
                System.out.println("Fornecedor não encontrado.");
                return;
            }

            out.exibirFornecedor(f);

            String confirm = leitor.linha("Confirma exclusão? (S/N): ");
            if (confirm.equalsIgnoreCase("S")) {
                fornecedorDAO.excluir(codigo);
                System.out.println("Fornecedor excluído.");
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }


    public void excluirTransportadora(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código da transportadora a excluir: ");
            Transportadora t = transportadoraDAO.buscarPorCodigo(codigo);
            if (t == null) {
                System.out.println("Transportadora não encontrada.");
                return;
            }

            out.exibirTransportadora(t);


            String confirm = leitor.linha("Confirma exclusão? (S/N): ");
            if (confirm.equalsIgnoreCase("S")) {
                transportadoraDAO.excluir(codigo);
                System.out.println("Transportadora excluída.");
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void excluirUsuario(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código do usuário a excluir: ");
            if (codigo == adminLogado.getCodigo()) {
                System.out.println("Não é possível excluir o próprio usuário logado.");
                return;
            }

            Usuario u = usuarioDAO.buscarPorCodigo(codigo);
            if (u == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }

            out.exibirUsuario(u);

            String confirm = leitor.linha("Confirma exclusão? (S/N): ");
            if (confirm.equalsIgnoreCase("S")) {
                usuarioDAO.excluir(codigo);
                System.out.println("Usuário excluído.");
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void excluirPedido(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código do pedido a excluir: ");
            Pedido p = pedidoDAO.buscarPorCodigo(codigo);
            if (p == null) {
                System.out.println("Pedido não encontrado.");
                return;
            }

            if (p.getStatus() == StatusPedido.ENVIADO) {
                System.out.println("Não é possível excluir um pedido já enviado.");
                return;
            }

            out.exibirPedido(p);

            String confirm = leitor.linha("Confirma exclusão? (S/N): ");
            if (confirm.equalsIgnoreCase("S")) {

                if (p.getStatus() != StatusPedido.CANCELADO) {
                    // Restaurar estoque
                    for (ItemPedido item : p.getItens()) {
                        item.getProduto().adicionarEstoque(item.getQuantidade());
                        produtoDAO.atualizar(item.getProduto());
                    }
                }
                itemPedidoDAO.excluirPorPedido(codigo);
                pedidoDAO.excluir(codigo);
                System.out.println("Pedido excluído.");
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void excluirRemessa(Usuario adminLogado) {
        if (!isAdmin(adminLogado)) {
            System.out.println("Acesso negado.");
            return;
        }

        try {
            int codigo = leitor.inteiro("Código da remessa a excluir: ");
            Remessa r = remessaDAO.buscarPorCodigo(codigo);
            if (r == null) {
                System.out.println("Remessa não encontrada.");
                return;
            }

            boolean temEnviado = false;
            for (Pedido p : r.getPedidos()) {
                if (p.getStatus() == StatusPedido.ENVIADO) {
                    temEnviado = true;
                    break;
                }
            }
            if (temEnviado) {
                System.out.println("Não é possível excluir remessa com pedidos já enviados.");
                return;
            }

            out.exibirRemessa(r);

            String confirm = leitor.linha("Confirma exclusão? (S/N): ");
            if (confirm.equalsIgnoreCase("S")) {
                for (Pedido p : r.getPedidos()) {
                    pedidoDAO.atualizarRemessaEFrete(p.getCodigo(), null, p.getValorFrete());
                }
                remessaDAO.excluir(codigo);
                System.out.println("Remessa excluída.");
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}