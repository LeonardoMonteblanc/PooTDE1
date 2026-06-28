package controle.io;

import modelo.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleOutput {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private void exibirLinhaSeparadora(int tamanho) {
       System.err.println("=".repeat(tamanho)); 
    }

    public void exibirSemRegistros(String entidade) {
        System.out.println("(nenhum " + entidade + " cadastrado)");
    }

    public void exibirProduto(Produto p) {
        System.out.printf("%-6s | %-40s | %-10s | %-6s | %-30s","CÓDIGO", "DESCRIÇÃO", "PREÇO", "ESTOQUE", "FORNECEDOR");

        exibirLinhaSeparadora(90);
        
        System.out.printf("%-6d | %-40s | %-10s | %-6d n",p.getCodigo(),p.getDescricao(),String.format("R$ %.2f", p.getPreco()),p.getEstoque());

    }

    public void exibirFornecedor(Fornecedor f) {
        System.out.printf("%-6s | %-30s | %-20s%n", "CÓDIGO", "NOME", "CNPJ");
        exibirLinhaSeparadora(60);

        System.out.printf("%-6d | %-30s | %-20s%n",f.getCodigo(),f.getNome(),f.getDocumento());
    }

    public void exibirTransportadora(Transportadora t) {
        System.out.printf("%-6s | %-30s | %-10s%n", "CÓDIGO", "NOME", "TAXA FRETE");
        exibirLinhaSeparadora(50);

        System.out.printf("%-6d | %-30s | %-10s%n",t.getCodigo(),t.getNome(),String.format("R$ %.2f", t.getTaxaFrete()));
    }

    public void exibirUsuario(Usuario u) {
        System.out.printf("%-6s | %-30s | %-15s | %-15s | %-10s%n",
                "CÓDIGO", "NOME", "LOGIN", "DOCUMENTO", "NÍVEL");
        exibirLinhaSeparadora(80);

        System.out.printf("%-6d | %-30s | %-15s | %-15s | %-10s%n",u.getCodigo(),u.getNome(),u.getLogin(),u.getDocumento(),u.getNivelAcesso().name());
    }


    public void exibirItemPedido(ItemPedido item) {
        System.out.printf("%-40s | %6d | %10s | %10s%n", item.getProduto().getDescricao(), item.getQuantidade(), String.format("R$ %.2f", item.getProduto().getPreco()),String.format("R$ %.2f", item.getSubTotal()));
}


    public void exibirPedido(Pedido p) {
        System.out.printf("%-8s | %-30s | %-15s | %-20s%n","NÚMERO", "CLIENTE", "STATUS", "DATA");
        exibirLinhaSeparadora(70); 

        System.out.printf("%-8d | %-30s | %-15s | %-20s%n",p.getCodigo(),p.getCliente().getNome(),p.getStatus().name(),p.getDataCriado().format(DATE_FORMATTER));
        

        System.out.println("   --- Itens ---");
        System.out.printf("   %-40s | %6s | %10s | %10s%n", "DESCRIÇÃO", "QTD", "PREÇO UN.", "SUBTOTAL");
        exibirLinhaSeparadora(70); 

        for (ItemPedido item : p.getItens()) {
            exibirItemPedido(item);
        }

        System.out.printf("   Total do pedido: R$ %.2f%n", p.calcularTotalPedido());

    }


    public void exibirRemessa(Remessa r) {
        System.out.printf("%-6s | %-30s | %-20s | %-6s%n","CÓDIGO", "TRANSPORTADORA", "DATA", "PEDIDOS");
        exibirLinhaSeparadora(70);

        System.out.printf("%-6d | %-30s | %-20s | %-6d%n", r.getCodigo(),r.getTransportadora().getNome(),r.getData().format(DATE_FORMATTER),r.getPedidos().size());
    }

    public void exibirListaProdutos(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            exibirSemRegistros("produtos");
            return;
        }
        System.out.printf("%-6s | %-40s | %-10s | %-6s | %-30s%n","CÓDIGO", "DESCRIÇÃO", "PREÇO", "ESTOQUE", "FORNECEDOR");

        exibirLinhaSeparadora(90);
        for (Produto p : produtos) {
            System.out.printf("%-6d | %-40s | %-10s | %-6d",p.getCodigo(),p.getDescricao(),String.format("R$ %.2f", p.getPreco()),p.getEstoque());
            System.out.println();
        }
    }

    public void exibirListaFornecedores(List<Fornecedor> fornecedores) {
        if (fornecedores.isEmpty()) {
            exibirSemRegistros("fornecedores");
            return;
        }
        System.out.printf("%-6s | %-30s | %-20s%n", "CÓDIGO", "NOME", "CNPJ");
        exibirLinhaSeparadora(60);

        for (Fornecedor f : fornecedores) {
            System.out.printf("%-6d | %-30s | %-20s%n",f.getCodigo(),f.getNome(),f.getDocumento());            
        }
        System.out.println();
    }

    public void exibirListaTransportadoras(List<Transportadora> transportadoras) {
        if (transportadoras.isEmpty()) {
            exibirSemRegistros("transportadoras");
            return;
        }
        System.out.printf("%-6s | %-30s | %-10s%n", "CÓDIGO", "NOME", "TAXA FRETE");
        exibirLinhaSeparadora(50);

        
        for (Transportadora t : transportadoras) {
            System.out.printf("%-6d | %-30s | %-10s%n",t.getCodigo(),t.getNome(),String.format("R$ %.2f", t.getTaxaFrete()));
        }
        System.out.println();
    }

    public void exibirListaUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            exibirSemRegistros("usuários");
            return;
        }

        System.out.printf("%-6s | %-30s | %-15s | %-15s | %-10s%n",
                "CÓDIGO", "NOME", "LOGIN", "DOCUMENTO", "NÍVEL");
        exibirLinhaSeparadora(80);

        for (Usuario u : usuarios) {
            System.out.printf("%-6d | %-30s | %-15s | %-15s | %-10s%n",u.getCodigo(),u.getNome(),u.getLogin(),u.getDocumento(),u.getNivelAcesso().name());
        }
        System.out.println();
    }

    public void exibirListaPedidos(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            exibirSemRegistros("pedidos");
            return;
        }
        
        
        
        for (Pedido p : pedidos) {
            System.out.printf("%-8s | %-30s | %-15s | %-20s%n","NÚMERO", "CLIENTE", "STATUS", "DATA");
            exibirLinhaSeparadora(70); 
            System.out.printf("%-8d | %-30s | %-15s | %-20s%n",p.getCodigo(),p.getCliente().getNome(),p.getStatus().name(),p.getDataCriado().format(DATE_FORMATTER));
            
            System.out.printf("%-40s | %6s | %10s | %10s%n",    "DESCRIÇÃO", "QTD", "PREÇO UN.", "SUBTOTAL");
            
            for (ItemPedido item : p.getItens()) {
                exibirItemPedido(item);
            }
            exibirLinhaSeparadora(70); 
            System.out.printf("Total do pedido: R$ %.2f%n", p.calcularTotalPedido());

        }
        System.out.println();
    }

    public void exibirListaRemessas(List<Remessa> remessas) {
        if (remessas.isEmpty()) {
            exibirSemRegistros("remessas");
            return;
        }
        System.out.printf("%-6s | %-30s | %-20s | %-6s%n", "CÓDIGO", "TRANSPORTADORA", "DATA", "PEDIDOS");
        exibirLinhaSeparadora(70);

        for (Remessa r : remessas) {
            System.out.printf("%-6d | %-30s | %-20s | %-6d%n", r.getCodigo(),r.getTransportadora().getNome(),r.getData().format(DATE_FORMATTER),r.getPedidos().size());
        }
        System.out.println();
    }
}