package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.*;

public class Consulta {
    private Dados sistema;

    public Consulta(Dados sis) {
        this.sistema = sis;
    }

    public void consultarUsuarioPorCodigo(int codigo) {
        List<Usuario> usuarios = sistema.getUsuarios();
        for (Usuario u : usuarios) {
            if (u.getCodigo() == codigo) {
                System.out.printf("%n[Usuario] %s (login: %s) - %s%n", u.getNome(), u.getLogin(), u.getNivelAcesso().name());
                return;
            }
        }
        imprimirNaoEncontrado(codigo);
    }

    public void consultarFornecedorPorCodigo(int codigo) {
        List<Fornecedor> fornecedores = sistema.getFornecedores();
        for (Fornecedor f : fornecedores) {
            if (f.getCodigo() == codigo) {
                System.out.printf("%n[Fornecedor] [%d] %s - CNPJ: %s%n", f.getCodigo(), f.getNome(), f.getCnpj());
                return;
            }
        }
        imprimirNaoEncontrado(codigo);
    }

    public void consultarProdutoPorCodigo(int codigo) {
        List<Produto> produtos = sistema.getProdutos();
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                System.out.printf("%n[Produto] [%d] %s - R$ %.2f%n", p.getCodigo(), p.getDescricao(), p.getPreco());
                String fornecedoresTexto = p.getFornecedoresTexto();
                if (!fornecedoresTexto.isEmpty()) {
                    System.out.println("  Fornecedor(es): " + fornecedoresTexto);
                }
                return;
            }
        }
        imprimirNaoEncontrado(codigo);
    }

    public void consultarTransportadoraPorCodigo(int codigo) {
        List<Transportadora> transportadoras = sistema.getTransportadora();
        for (Transportadora t : transportadoras) {
            if (t.getCodigo() == codigo) {
                System.out.printf("%n[Transportadora] [%d] %s%n", t.getCodigo(), t.getNome());
                return;
            }
        }
        imprimirNaoEncontrado(codigo);
    }

    public void consultarRemessaPorCodigo(int codigo) {
        List<Remessa> remessas = sistema.getRemessas();
        for (Remessa r : remessas) {
            if (r.getCodigo() == codigo) {
                System.out.printf("%n[Remessa] #%d | Data: %s | Transportadora: %s | Cliente: %s | Pedidos: %d%n",
                        r.getCodigo(), r.getData(),
                        r.getTransportadora().getNome(),
                        r.getCliente().getNome(),
                        r.getPedidos().size());
                return;
            }
        }
        imprimirNaoEncontrado(codigo);
    }

    public void consultarPedidoPorCodigo(int codigo) {
        List<Remessa> remessas = sistema.getRemessas();
        for (Remessa r : remessas) {
            for (Pedido p : r.getPedidos()) {
                if (p.getCodigo() == codigo) {
                    System.out.printf("%n>> PEDIDO #%d  (Remessa #%d | Cliente: %s) <<%n",
                            p.getCodigo(), r.getCodigo(), r.getCliente().getNome());
                    new Listagem(sistema).imprimirItensPedido(p.getItens());
                    return;
                }
            }
        }
        imprimirNaoEncontrado(codigo);
    }


    public boolean consultarUsuariosPorTexto(String texto) {
        texto = texto.toLowerCase();
        List<Usuario> usuarios = sistema.getUsuarios();
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(texto) || u.getLogin().toLowerCase().contains(texto)) {
                usuariosEncontrados.add(u);
            }
        }
        if (!usuariosEncontrados.isEmpty()) {
            System.out.println("\n--- Usuarios ---");
            for (Usuario u : usuariosEncontrados) {
                System.out.printf("  [%d] %s (login: %s) - %s%n",
                        u.getCodigo(), u.getNome(), u.getLogin(), u.getNivelAcesso().name());
            }
            return true;
        }
        return false;
    }

    public boolean consultarFornecedoresPorTexto(String texto) {
        texto = texto.toLowerCase();
        List<Fornecedor> fornecedores = sistema.getFornecedores();
        List<Fornecedor> fornecedoresEncontrados = new ArrayList<>();
        for (Fornecedor f : fornecedores) {
            if (f.getNome().toLowerCase().contains(texto) || f.getCnpj().toLowerCase().contains(texto)) {
                fornecedoresEncontrados.add(f);
            }
        }
        if (!fornecedoresEncontrados.isEmpty()) {
            System.out.println("\n--- Fornecedores ---");
            for (Fornecedor f : fornecedoresEncontrados) {
                System.out.printf("  [%d] %s - CNPJ: %s%n", f.getCodigo(), f.getNome(), f.getCnpj());
            }
            return true;
        }
        return false;
    }

    public boolean consultarProdutosPorTexto(String texto) {
        texto = texto.toLowerCase();
        List<Produto> produtos = sistema.getProdutos();
        List<Produto> produtosEncontrados = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getDescricao().toLowerCase().contains(texto)) {
                produtosEncontrados.add(p);
            }
        }
        if (!produtosEncontrados.isEmpty()) {
            System.out.println("\n--- Produtos ---");
            for (Produto p : produtosEncontrados) {
                System.out.printf("  [%d] %s - R$ %.2f%n", p.getCodigo(), p.getDescricao(), p.getPreco());
            }
            return true;
        }
        return false;
    }

    public boolean consultarTransportadorasPorTexto(String texto) {
        texto = texto.toLowerCase();
        List<Transportadora> transportadoras = sistema.getTransportadora();
        List<Transportadora> transportadorasEncontradas = new ArrayList<>();
        for (Transportadora t : transportadoras) {
            if (t.getNome().toLowerCase().contains(texto)) {
                transportadorasEncontradas.add(t);
            }
        }
        if (!transportadorasEncontradas.isEmpty()) {
            System.out.println("\n--- Transportadoras ---");
            for (Transportadora t : transportadorasEncontradas) {
                System.out.printf("  [%d] %s%n", t.getCodigo(), t.getNome());
            }
            return true;
        }
        return false;
    }

    public boolean consultarPedidosPorTexto(String texto) {
        String termo = texto.toLowerCase();
        boolean encontrou = false;
        for (Remessa r : sistema.getRemessas()) {
            for (Pedido p : r.getPedidos()) {
                if (pedidoMatchTexto(p, r, termo)) {
                    if (!encontrou) {
                        System.out.println("\n--- Pedidos ---");
                        encontrou = true;
                    }
                    System.out.printf("  Pedido #%d | Remessa #%d | Cliente: %s | Transportadora: %s%n",
                            p.getCodigo(), r.getCodigo(), r.getCliente().getNome(), r.getTransportadora().getNome());
                }
            }
        }
        return encontrou;
    }

    public boolean consultarRemessasPorTexto(String texto) {
        texto = texto.toLowerCase();
        List<Remessa> remessas = sistema.getRemessas();
        List<Remessa> remessasEncontradas = new ArrayList<>();
        for (Remessa r : remessas) {
            if (r.getCliente().getNome().toLowerCase().contains(texto) ||
                r.getTransportadora().getNome().toLowerCase().contains(texto)) {
                remessasEncontradas.add(r);
            }
        }
        if (!remessasEncontradas.isEmpty()) {
            System.out.println("\n--- Remessas ---");
            for (Remessa r : remessasEncontradas) {
                System.out.printf("  Remessa #%d | %s | Transportadora: %s | Cliente: %s%n",
                        r.getCodigo(), r.getData(),
                        r.getTransportadora().getNome(),
                        r.getCliente().getNome());
            }
            return true;
        }
        return false;
    }

    private boolean pedidoMatchTexto(Pedido pedido, Remessa remessa, String termo) {
        if (String.valueOf(pedido.getCodigo()).contains(termo)) {
            return true;
        }
        if (remessa.getCliente().getNome().toLowerCase().contains(termo)) {
            return true;
        }
        if (remessa.getTransportadora().getNome().toLowerCase().contains(termo)) {
            return true;
        }
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto().getDescricao().toLowerCase().contains(termo)) {
                return true;
            }
        }
        return false;
    }

    private void imprimirNaoEncontrado(int codigo) {
        System.out.println("Nenhum resultado encontrado com codigo " + codigo + ".");
    }
}
