package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.*;

public class Consulta {
    private SistemaControle sistema;

    public Consulta(SistemaControle sis) {
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
        System.out.println("Nenhum resultado encontrado com codigo " + codigo + ".");
    }

    public void consultarFornecedorPorCodigo(int codigo) {
        List<Fornecedor> fornecedores = sistema.getFornecedores();
        for (Fornecedor f : fornecedores) {
            if (f.getCodigo() == codigo) {
                System.out.printf("%n[Fornecedor] [%d] %s - CNPJ: %s%n", f.getCodigo(), f.getNome(), f.getCnpj());
                return;
            }
        }
        System.out.println("Nenhum resultado encontrado com codigo " + codigo + ".");
    }

    public void consultarProdutoPorCodigo(int codigo) {
        List<Produto> produtos = sistema.getProdutos();
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                System.out.printf("%n[Produto] [%d] %s - R$ %.2f%n", p.getCodigo(), p.getDescricao(), p.getPreco());
                if (!p.getFornecedores().isEmpty()) {
                    System.out.print("  Fornecedor(es): ");
                    for (int i = 0; i < p.getFornecedores().size(); i++) {
                        System.out.print(p.getFornecedores().get(i).getNome());
                        if (i < p.getFornecedores().size() - 1) System.out.print(", ");
                    }
                    System.out.println();
                }
                return;
            }
        }
        System.out.println("Nenhum resultado encontrado com codigo " + codigo + ".");
    }

    public void consultarTransportadoraPorCodigo(int codigo) {
        List<Transportadora> transportadoras = sistema.getTransportadora();
        for (Transportadora t : transportadoras) {
            if (t.getCodigo() == codigo) {
                System.out.printf("%n[Transportadora] [%d] %s%n", t.getCodigo(), t.getNome());
                return;
            }
        }
        System.out.println("Nenhum resultado encontrado com codigo " + codigo + ".");
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
        System.out.println("Nenhum resultado encontrado com codigo " + codigo + ".");
    }


    public boolean consultarUsuariosPorTexto(String texto) {
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

    public boolean consultarRemessasPorTexto(String texto) {
        List<Remessa>remessas = sistema.getRemessas();
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
}
