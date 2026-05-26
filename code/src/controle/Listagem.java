package controle;

import java.util.List;

import modelo.*;

public class Listagem {
    private Dados d;
    
    public Listagem(Dados d){
        this.d = d;
    }

    public void listarProdutos() {
        int qtdprodutos = d.getProdutos().size();
        List<Produto> produtos = d.getProdutos();

        System.out.println("\n========== PRODUTOS (" + qtdprodutos + ") ==========");
        if (produtos.isEmpty()) {
            System.out.println("(nenhum produto cadastrado)");
            return; }

        int maxCodigo = "CÓDIGO".length();
        int maxDescricao = "DESCRIÇÃO".length();
        int maxPreco = "PREÇO".length();
        int maxFornecedores = "FORNECEDORES".length();

        for (Produto p : produtos) {
            maxCodigo = Math.max(maxCodigo, String.valueOf(p.getCodigo()).length());
            maxDescricao = Math.max(maxDescricao, p.getDescricao().length());
            maxPreco = Math.max(maxPreco, String.format("R$ %.2f", p.getPreco()).length());
            maxFornecedores = Math.max(maxFornecedores, montarFornecedoresTexto(p).length());
        }

        maxCodigo += 2;
        maxDescricao += 2;
        maxPreco += 2;
        maxFornecedores += 2;

        String formato = "%-" + maxCodigo + "s %-" + maxDescricao + "s %-" + maxPreco + "s %-" + maxFornecedores + "s%n";
        System.out.printf(formato, "CÓDIGO", "DESCRIÇÃO", "PREÇO", "FORNECEDORES");

        int totalLargura = maxCodigo + maxDescricao + maxPreco + maxFornecedores + 3;
        System.out.println("-".repeat(totalLargura));

        for (Produto p : produtos) {
            String precoStr = String.format("R$ %.2f", p.getPreco());
            String fornecedoresStr = montarFornecedoresTexto(p);
            System.out.printf(formato, String.valueOf(p.getCodigo()), p.getDescricao(), precoStr, fornecedoresStr);
        }
    }

    public String montarFornecedoresTexto(Produto p) {
        return p.getFornecedoresTexto();
    }

    public void listarUsuarios() {
        List<Usuario> usuarios = d.getUsuarios();
        System.out.println("\n========== USUARIOS (" + usuarios.size() + ") ==========");
        for (Usuario u : usuarios) {
            System.out.printf("%s (%s) - %s%n", u.getNome(), u.getLogin(), u.getNivelAcesso().name());
        }
    }

    public void listarFornecedores() {
        List<Fornecedor> fornecedores = d.getFornecedores();
        System.out.println("\n========== FORNECEDORES (" + fornecedores.size() + ") ==========");
        for (Fornecedor f : fornecedores) {
            System.out.printf("[%03d] %s - CNPJ: %s%n", f.getCodigo(), f.getNome(), f.getCnpj());
        }
    }

    public void listarTransportadoras() {
        List<Transportadora> transportadoras = d.getTransportadora();
        System.out.println("\n========== TRANSPORTADORAS (" + transportadoras.size() + ") ==========");
        for (Transportadora t : transportadoras) {
            System.out.printf("[%03d] %s%n", t.getCodigo(), t.getNome());
        }
    }

    public void listarPedidos() {
        List<Remessa> remessas = d.getRemessas();
        int totalPedidos = 0;
        for (Remessa r : remessas) {
            totalPedidos += r.getPedidos().size();
        }
        System.out.println("\n========== PEDIDOS (" + totalPedidos + ") ==========");
        for (Remessa r : remessas) {
            for (Pedido ped : r.getPedidos()) {
                System.out.printf("%n>> PEDIDO #%d  (Remessa #%d | Cliente: %s) <<%n",
                        ped.getCodigo(), r.getCodigo(), r.getCliente().getNome());
                imprimirItensPedido(ped.getItens());
            }
        }
    }

    public void listarRemessas() {
        List<Remessa> remessas = d.getRemessas();
        System.out.println("\n========== REMESSAS (" + remessas.size() + ") ==========");
        for (Remessa r : remessas) {
            imprimirCabecalhoRemessa(r);
            imprimirPedidosRemessa(r.getPedidos());
        }
    }

    public void imprimirCabecalhoRemessa(Remessa r) {
        System.out.printf("%n>> REMESSA #%d <<%n", r.getCodigo());
        System.out.printf("   Data: %s | Transportadora: %s%n", r.getData(), r.getTransportadora().getNome());
        System.out.printf("   Cliente: %s%n", r.getCliente().getNome());
    }

    public void imprimirPedidosRemessa(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("   (nenhum pedido)");
            return;
        }

        for (Pedido ped : pedidos) {
            System.out.printf("%n   --- PEDIDO #%d ---%n", ped.getCodigo());
            imprimirItensPedido(ped.getItens());
        }
    }

    public void imprimirItensPedido(List<ItemPedido> itens) {
        if (itens.isEmpty()) {
            System.out.println("   (sem itens)");
            return; }

        int maxDesc = "Descrição".length();
        int maxQtd = "Qtd".length();
        int maxPrecoUn = "Preço Un.".length();
        int maxPrecoTot = "Total".length();

        for (ItemPedido item : itens) {
            maxDesc = Math.max(maxDesc, item.getProduto().getDescricao().length());
            maxQtd = Math.max(maxQtd, String.valueOf(item.getQuantidade()).length());
            maxPrecoUn = Math.max(maxPrecoUn, String.format("%.2f", item.getProduto().getPreco()).length());
            maxPrecoTot = Math.max(maxPrecoTot, String.format("%.2f", item.getQuantidade() * item.getProduto().getPreco()).length());
        }

        maxDesc += 2;
        maxQtd += 2;
        maxPrecoUn += 2;
        maxPrecoTot += 2;

        String formato = "   %-" + maxDesc + "s %" + maxQtd + "s  R$ %" + maxPrecoUn + "s  R$ %" + maxPrecoTot + "s%n";
        System.out.printf(formato, "Descrição", "Qtd", "Preço Un.", "Total");

        int larguraTotal = maxDesc + maxQtd + maxPrecoUn + maxPrecoTot + 12;
        System.out.println("   " + "-".repeat(larguraTotal));

        for (ItemPedido item : itens) {
            String desc = item.getProduto().getDescricao();
            String qtd = String.valueOf(item.getQuantidade());
            String precoUn = String.format("%.2f", item.getProduto().getPreco());
            String precoTot = String.format("%.2f", item.getQuantidade() * item.getProduto().getPreco());
            System.out.printf(formato, desc, qtd, precoUn, precoTot);
        }
        System.out.println("   " + "-".repeat(larguraTotal));
    }

    public void listarTodos() {
        listarUsuarios();
        listarFornecedores();
        listarProdutos();
        listarTransportadoras();
        listarRemessas();
    }


}
