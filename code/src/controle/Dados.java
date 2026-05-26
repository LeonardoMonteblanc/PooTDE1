package controle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import modelo.*;

public class Dados {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Fornecedor> fornecedores = new ArrayList<>();
    private final List<Produto> produtos = new ArrayList<>();
    private final List<Transportadora> transportadoras = new ArrayList<>();
    private final List<Remessa> remessas = new ArrayList<>();

    public void carregarDados() {
        Usuario admin = new Usuario(1, "Marina Souza", "admin", "123", NivelAcesso.ADMIN);
        Usuario cliente = new Usuario(2, "Carlos Oliveira", "carlos", "123", NivelAcesso.CLIENTE);
        Usuario leo = new Usuario(3, "Leonardo", "leo", "123", NivelAcesso.ADMIN);
        Usuario vitor = new Usuario(4, "Vitor", "vitor", "123", NivelAcesso.ADMIN);
        adicionarTodos(usuarios, leo, vitor, admin, cliente);

        Fornecedor f1 = new Fornecedor(101, "VerdeVivo Hortifruti", "12.345.678/0001-99");
        Fornecedor f2 = new Fornecedor(102, "TecnoArte Eletrônicos", "98.765.432/0001-10");
        Fornecedor f3 = new Fornecedor(103, "Mundo Doce Confeitaria", "11.222.333/0001-44");
        adicionarTodos(fornecedores, f1, f2, f3);

        Produto p1 = new Produto(1001, "Abacate Margarida (kg)", 9.90, f1);
        Produto p2 = new Produto(1002, "Tomate Cereja Orgânico (bandeja 250g)", 7.50, f1);
        Produto p3 = new Produto(1003, "Cesta de Morangos Frescos (500g)", 15.00, f1);
        Produto p4 = new Produto(1004, "Alface Crespa Hidropônica (unidade)", 3.20, f1);
        Produto p5 = new Produto(1005, "Cenoura Baby (pacote 500g)", 6.80, f1);
        Produto p6 = new Produto(1006, "Mix de Folhas Verdes (150g)", 8.90, f1);
        Produto p7 = new Produto(1007, "Manga Palmer (unidade)", 5.50, f1);
        adicionarTodos(produtos, p1, p2, p3, p4, p5, p6, p7);

        Produto p8  = new Produto(2001, "Fone de Ouvido Bluetooth com Cancelamento de Ruído", 299.90, f2);
        Produto p9  = new Produto(2002, "Carregador Portátil Solar 20000mAh", 159.00, f2);
        Produto p10 = new Produto(2003, "Luminária LED Inteligente Wi-Fi RGB", 89.90, f2);
        Produto p11 = new Produto(2004, "Hub USB-C 6 em 1 com HDMI 4K", 179.00, f2);
        Produto p12 = new Produto(2005, "Suporte Articulado para Monitor e Notebook", 129.90, f2);
        Produto p13 = new Produto(2006, "Teclado Mecânico Compacto Switch Azul", 249.00, f2);
        Produto p14 = new Produto(2007, "Mouse Gamer RGB 16000 DPI", 199.90, f2);
        adicionarTodos(produtos, p8, p9, p10, p11, p12, p13, p14);

        Produto p15 = new Produto(3001, "Bolo de Cenoura com Cobertura de Chocolate (1kg)", 45.00, f3);
        Produto p16 = new Produto(3002, "Torta de Limão com Merengue (fatia)", 12.50, f3);
        Produto p17 = new Produto(3003, "Brigadeiro Gourmet de Colher (pote 200g)", 18.00, f3);
        Produto p18 = new Produto(3004, "Brownie de Chocolate com Nozes (unidade)", 7.00, f3);
        Produto p19 = new Produto(3005, "Cookie Red Velvet com Gotas de Chocolate Branco", 8.50, f3);
        Produto p20 = new Produto(3006, "Pão de Mel Artesanal Recheado (caixa c/ 6)", 24.00, f3);
        Produto p21 = new Produto(3007, "Geleia de Morango Caseira (vidro 300g)", 15.90, f3);
        adicionarTodos(produtos, p15, p16, p17, p18, p19, p20, p21);

        // 4. TRANSPORTADORAS
        Transportadora t1 = new Transportadora(501, "Rapidez Logística Express");
        Transportadora t2 = new Transportadora(502, "Carga Segura Transportes");
        Transportadora t3 = new Transportadora(503, "EcoFrete Soluções Verdes");
        adicionarTodos(transportadoras, t1, t2, t3);

        // 5. REMESSAS 
        Remessa r1 = new Remessa(1, t1, cliente);
        Pedido pedido1 = new Pedido(1);
        pedido1.adicionarItem(p3, 2);   // 2 cestas de morango
        pedido1.adicionarItem(p8, 1);   // 1 fone Bluetooth
        pedido1.adicionarItem(p15, 1);  // 1 bolo de cenoura
        r1.adicionarPedido(pedido1);

        Remessa r2 = new Remessa(2, t2, cliente);
        Pedido pedido2 = new Pedido(2);
        pedido2.adicionarItem(p1, 3);   // 3kg abacate
        pedido2.adicionarItem(p5, 2);   // 2 pacotes cenoura baby
        pedido2.adicionarItem(p11, 1);  // 1 hub USB-C
        pedido2.adicionarItem(p19, 5);  // 5 cookies red velvet
        r2.adicionarPedido(pedido2);

        Remessa r3 = new Remessa(3, t3, cliente);
        Pedido pedido3 = new Pedido(3);
        pedido3.adicionarItem(p4, 4);   // 4 pés de alface
        pedido3.adicionarItem(p7, 6);   // 6 mangas
        pedido3.adicionarItem(p9, 2);   // 2 carregadores solares
        pedido3.adicionarItem(p13, 1);  // 1 teclado mecânico
        pedido3.adicionarItem(p17, 3);  // 3 potes brigadeiro
        r3.adicionarPedido(pedido3);

        Remessa r4 = new Remessa(4, t1, cliente);
        Pedido pedido4 = new Pedido(4);
        pedido4.adicionarItem(p2, 3);   // 3 bandejas tomate cereja
        pedido4.adicionarItem(p6, 2);   // 2 mix de folhas
        pedido4.adicionarItem(p10, 1);  // 1 luminária LED
        pedido4.adicionarItem(p14, 1);  // 1 mouse gamer
        pedido4.adicionarItem(p20, 2);  // 2 caixas pão de mel
        pedido4.adicionarItem(p21, 1);  // 1 geleia
        r4.adicionarPedido(pedido4);
        Pedido pedido5 = new Pedido(5);
        pedido5.adicionarItem(p4, 4);   // 4 pés de alface
        pedido5.adicionarItem(p7, 6);   // 6 mangas
        pedido5.adicionarItem(p9, 2);   // 2 carregadores solares
        pedido5.adicionarItem(p13, 1);  // 1 teclado mecânico
        pedido5.adicionarItem(p17, 3);  // 3 potes brigadeiro
        r4.adicionarPedido(pedido5);
        adicionarTodos(remessas, r1, r2, r3, r4);
    }

    private <T> void adicionarTodos(List<T> destino, T... itens) {
        for (T item : itens) {
            destino.add(item);
        }
    }

    public List<Object> getDados() {
        List<Object> d = new ArrayList <>();
        d.add(usuarios);
        d.add(fornecedores);
        d.add(produtos);
        d.add(transportadoras);
        d.add(remessas);


        return d;
    }


    public List<Transportadora> getTransportadora(){
        return transportadoras;
    }

    public Transportadora getTransportadoraByCodigo(int codigo){
        for(Transportadora t : transportadoras){
            if(t.getCodigo() == codigo){
                return t;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    
    public List<Remessa> getRemessas() {
        return remessas;
    }

    public List<Produto> getProdutos(){
        return produtos;
    }

    public Fornecedor getFornecedorByCodigo(int codigo) {
        for (Fornecedor f : fornecedores) {
            if (f.getCodigo() == codigo) {
                return f;
            }
        }
        return null;
    }

    public Usuario getUsuarioByCodigo(int codigo) {
        for (Usuario u : usuarios) {
            if (u.getCodigo() == codigo) {
                return u;
            }
        }
        return null;
    }

    public Remessa getRemessaByCodigo(int codigo) {
        for (Remessa r : remessas) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    public Produto getProdutoByCodigo(int codigo){
        for(Produto p : produtos){
            if(p.getCodigo() == codigo){
                return p;
            }
        }
        return null;
    }

    public int geraCodigoPedido() {
        int maior = 0;
        for (Remessa r : remessas) {
            for (Pedido p : r.getPedidos()) {
                if (p.getCodigo() > maior) {
                    maior = p.getCodigo();
                }
            }
        }
        return maior + 1;
    }

    public int geraCodigoRemessa() {
        int maior = 0;
        for (Remessa r : remessas) {
            if (r.getCodigo() > maior) {
                maior = r.getCodigo();
            }
        }
        return maior + 1;
    }

    public Pedido getPedidoByCodigo(int codigo) {
        for (Remessa r : remessas) {
            for (Pedido p : r.getPedidos()) {
                if (p.getCodigo() == codigo) {
                    return p;
                }
            }
        }
        return null;
    }

    public Remessa getRemessaByPedido(Pedido pedido) {
        for (Remessa r : remessas) {
            if (r.getPedidos().contains(pedido)) {
                return r;
            }
        }
        return null;
    }

    public void removerPedidoDeTodasRemessas(Pedido pedido) {
        for (Remessa r : remessas) {
            r.removerPedido(pedido);
        }
    }
}
