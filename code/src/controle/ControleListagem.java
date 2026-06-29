package controle;

import modelo.*;
import modelo.enums.NivelAcesso;
import banco.*;
import controle.io.ConsoleOutput;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


public class ControleListagem {

    private final ProdutoDAO produtoDAO;
    private final FornecedorDAO fornecedorDAO;
    private final TransportadoraDAO transportadoraDAO;
    private final UsuarioDAO usuarioDAO;
    private final PedidoDAO pedidoDAO;
    private final RemessaDAO remessaDAO;
    private final ItemPedidoDAO itemPedidoDAO;
    private final ConsoleOutput out;

    public ControleListagem(ProdutoDAO prod, FornecedorDAO f,TransportadoraDAO t,UsuarioDAO u,PedidoDAO p,RemessaDAO r,ItemPedidoDAO i,ConsoleOutput out) {

        this.produtoDAO = prod;
        this.fornecedorDAO = f;
        this.transportadoraDAO = t;
        this.usuarioDAO = u;
        this.pedidoDAO = p;
        this.remessaDAO = r;
        this.itemPedidoDAO = i;
        this.out = out;
    }


    public void listarProdutos() {
        try {
            List<Produto> produtos = produtoDAO.listarTodos();
            out.exibirListaProdutos(produtos);

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public void listarFornecedores() {
        try {
            List<Fornecedor> fornecedores = fornecedorDAO.listarTodos();
            out.exibirListaFornecedores(fornecedores);

        } catch (SQLException e) {
            System.err.println("Erro ao listar fornecedores: " + e.getMessage());
        }
    }

    public void listarTransportadoras() {
        try {
            List<Transportadora> transportadoras = transportadoraDAO.listarTodos();
            out.exibirListaTransportadoras(transportadoras);

        } catch (SQLException e) {
            System.err.println("Erro ao listar transportadoras: " + e.getMessage());
        }
    }

    public void listarUsuarios(Usuario usuarioLogado) {
        if (!isAdmin(usuarioLogado)) {
            System.out.println("Acesso negado. Apenas administradores podem listar usuários.");
            return;
        }
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            out.exibirListaUsuarios(usuarios);

        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    public void listarPedidos(Usuario usuarioLogado) {
        try {
            List<Pedido> pedidos;
            if (isAdmin(usuarioLogado)) {
                pedidos = pedidoDAO.listarTodos();

            } else {
                pedidos = pedidoDAO.listarPorCliente(usuarioLogado.getCodigo());
            }

            out.exibirListaPedidos(pedidos);
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    public void listarRemessas(Usuario usuarioLogado) {
        try {
            List<Remessa> remessas;
            if (isAdmin(usuarioLogado)) {
                remessas = remessaDAO.listarTodos();

            } else {
                remessas = remessaDAO.listarPorCliente(usuarioLogado.getCodigo());
            }

            out.exibirListaRemessas(remessas);
        } catch (SQLException e) {
            System.err.println("Erro ao listar remessas: " + e.getMessage());
        }
    }


    public void consultarProdutoPorCodigo(int codigo) {
        try {
            Produto p = produtoDAO.buscarPorCodigo(codigo);

            if (p == null) {
                System.out.println("Produto com código " + codigo + " não encontrado.");
                return;
            }

            out.exibirProduto(p);
        } catch (SQLException e) {
            System.err.println("Erro ao consultar produto: " + e.getMessage());
        }
    }

    public void consultarFornecedorPorCodigo(int codigo) {
        try {
            Fornecedor f = fornecedorDAO.buscarPorCodigo(codigo);
            if (f == null) {
                System.out.println("Fornecedor com código " + codigo + " não encontrado.");
                return;
            }
            out.exibirFornecedor(f);

        } catch (SQLException e) {
            System.err.println("Erro ao consultar fornecedor: " + e.getMessage());
        }
    }

    public void consultarTransportadoraPorCodigo(int codigo) {
        try {
            Transportadora t = transportadoraDAO.buscarPorCodigo(codigo);
            if (t == null) {
                System.out.println("Transportadora com código " + codigo + " não encontrada.");
                return;
            }
            out.exibirTransportadora(t);

        } catch (SQLException e) {
            System.err.println("Erro ao consultar transportadora: " + e.getMessage());
        }
    }

    public void consultarUsuarioPorCodigo(int codigo, Usuario usuarioLogado) {
        if (!isAdmin(usuarioLogado) && usuarioLogado.getCodigo() != codigo) {
            System.out.println("Acesso negado. Apenas administradores podem consultar outros usuários.");
            return;
        }

        try {
            Usuario u = usuarioDAO.buscarPorCodigo(codigo);
            if (u == null) {
                System.out.println("Usuário com código " + codigo + " não encontrado.");
                return;
            }
            out.exibirUsuario(u);
        } catch (SQLException e) {
            System.err.println("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public void consultarRemessaPorCodigo(int codigo, Usuario usuarioLogado) {
        if (!isAdmin(usuarioLogado) && usuarioLogado.getCodigo() != codigo) {
            System.out.println("Acesso negado. Apenas administradores podem consultar outros usuários.");
            return;
        }

        try {
            Remessa r = remessaDAO.buscarPorCodigo(codigo);
            if (r == null) {
                System.out.println("Remessa com código " + codigo + " não encontrado.");
                return;
            }
            out.exibirRemessa(r);
        } catch (SQLException e) {
            System.err.println("Erro ao consultar remessa: " + e.getMessage());
        }
    }

    public void consultarPedidoPorCodigo(int codigo, Usuario usuarioLogado) {
        try {
            Pedido p = pedidoDAO.buscarPorCodigo(codigo);
            if (p == null) {
                System.out.println("Pedido com código " + codigo + " não encontrado.");
                return;
            }
            if (!isAdmin(usuarioLogado) && p.getCliente().getCodigo() != usuarioLogado.getCodigo()) {
                System.out.println("Acesso negado. Você não tem permissão para ver este pedido.");
                return;
            }
            out.exibirPedido(p); 
        } catch (SQLException e) {
            System.err.println("Erro ao consultar pedido: " + e.getMessage());
        }
    }


    public void consultarProdutosPorTexto(String texto) {
        try {
            List<Produto> produtos = produtoDAO.buscarPorDescricao(texto);
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado com o termo \"" + texto + "\".");
                return;
            }
            out.exibirListaProdutos(produtos);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    public void consultarFornecedoresPorTexto(String texto) {
        try {
            List<Fornecedor> fornecedores = fornecedorDAO.buscarPorNome(texto);
            if (fornecedores.isEmpty()) {
                System.out.println("Nenhum fornecedor encontrado com o termo \"" + texto + "\".");
                return;
            }
            out.exibirListaFornecedores(fornecedores);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedores: " + e.getMessage());
        }
    }

    public void consultarTransportadorasPorTexto(String texto) {
        try {
            List<Transportadora> transportadoras = transportadoraDAO.buscarPorNome(texto);
            if (transportadoras.isEmpty()) {
                System.out.println("Nenhuma transportadora encontrada com o termo \"" + texto + "\".");
                return;
            }
            out.exibirListaTransportadoras(transportadoras);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar transportadoras: " + e.getMessage());
        }
    }

    public void consultarUsuariosPorTexto(String texto, Usuario usuarioLogado) {
        if (!isAdmin(usuarioLogado)) {
            System.out.println("Acesso negado. Apenas administradores podem buscar usuários.");
            return;
        }
        try {
            List<Usuario> usuarios = usuarioDAO.buscarPorNome(texto);
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário encontrado com o termo \"" + texto + "\".");
                return;
            }
            out.exibirListaUsuarios(usuarios);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
        }
    }


    public void consultarPedidosPorPeriodo(LocalDateTime inicio, LocalDateTime fim, Usuario usuarioLogado) {
        try {
            List<Pedido> pedidos;
            if (isAdmin(usuarioLogado)) {
                pedidos = pedidoDAO.listarPorPeriodo(inicio, fim);
            } else {
                pedidos = pedidoDAO.listarPorPeriodoCliente(inicio, fim, usuarioLogado.getCodigo());
            }
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado no período informado.");
                return;
            }
            out.exibirListaPedidos(pedidos);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedidos por período: " + e.getMessage());
        }
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario != null && usuario.getNivelAcesso() == NivelAcesso.ADMIN;
    }
}