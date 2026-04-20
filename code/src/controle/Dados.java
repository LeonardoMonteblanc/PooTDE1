package controle;
import java.util.ArrayList;
import java.util.List;

import modelo.*;

import modelo.Fornecedor;
import modelo.Pedido;
import modelo.NivelAcesso;
import modelo.Produto;
import modelo.Remessa;
import modelo.Transportadora;
import modelo.Usuario;

public class Dados {

    public List<Usuario> usuarios = new ArrayList<>();
    public List<Fornecedor> fornecedores = new ArrayList<>();
    public List<Produto> produtos = new ArrayList<>();
    public List<Transportadora> transportadoras = new ArrayList<>();
    public List<Remessa> remessas = new ArrayList<>();

    public void carregarDados() {
        Usuario admin = new Usuario(1, "Marina Souza", "admin", "123", NivelAcesso.ADMIN);
        Usuario cliente = new Usuario(2, "Carlos Oliveira", "carlos", "123", NivelAcesso.CLIENTE);
        Usuario leo = new Usuario(3, "Leonardo", "leo", "123", NivelAcesso.ADMIN);
        Usuario vitor = new Usuario(4, "Vitor", "vitor", "123", NivelAcesso.ADMIN);
        usuarios.add(leo);
        usuarios.add(vitor);
        usuarios.add(admin);
        usuarios.add(cliente);

        Fornecedor f1 = new Fornecedor(101, "VerdeVivo Hortifruti", "12.345.678/0001-99");
        Fornecedor f2 = new Fornecedor(102, "TecnoArte Eletrônicos", "98.765.432/0001-10");
        Fornecedor f3 = new Fornecedor(103, "Mundo Doce Confeitaria", "11.222.333/0001-44");
        fornecedores.add(f1);
        fornecedores.add(f2);
        fornecedores.add(f3);

        Produto p1 = new Produto(1001, "Abacate Margarida (kg)", 9.90, f1);
        Produto p2 = new Produto(1002, "Tomate Cereja Orgânico (bandeja 250g)", 7.50, f1);
        Produto p3 = new Produto(1003, "Cesta de Morangos Frescos (500g)", 15.00, f1);
        Produto p4 = new Produto(1004, "Alface Crespa Hidropônica (unidade)", 3.20, f1);
        Produto p5 = new Produto(1005, "Cenoura Baby (pacote 500g)", 6.80, f1);
        Produto p6 = new Produto(1006, "Mix de Folhas Verdes (150g)", 8.90, f1);
        Produto p7 = new Produto(1007, "Manga Palmer (unidade)", 5.50, f1);
        produtos.add(p1);
        produtos.add(p2);
        produtos.add(p3);
        produtos.add(p4);
        produtos.add(p5);
        produtos.add(p6);
        produtos.add(p7);

        Produto p8  = new Produto(2001, "Fone de Ouvido Bluetooth com Cancelamento de Ruído", 299.90, f2);
        Produto p9  = new Produto(2002, "Carregador Portátil Solar 20000mAh", 159.00, f2);
        Produto p10 = new Produto(2003, "Luminária LED Inteligente Wi-Fi RGB", 89.90, f2);
        Produto p11 = new Produto(2004, "Hub USB-C 6 em 1 com HDMI 4K", 179.00, f2);
        Produto p12 = new Produto(2005, "Suporte Articulado para Monitor e Notebook", 129.90, f2);
        Produto p13 = new Produto(2006, "Teclado Mecânico Compacto Switch Azul", 249.00, f2);
        Produto p14 = new Produto(2007, "Mouse Gamer RGB 16000 DPI", 199.90, f2);
        produtos.add(p8);
        produtos.add(p9);
        produtos.add(p10);
        produtos.add(p11);
        produtos.add(p12);
        produtos.add(p13);
        produtos.add(p14);

        Produto p15 = new Produto(3001, "Bolo de Cenoura com Cobertura de Chocolate (1kg)", 45.00, f3);
        Produto p16 = new Produto(3002, "Torta de Limão com Merengue (fatia)", 12.50, f3);
        Produto p17 = new Produto(3003, "Brigadeiro Gourmet de Colher (pote 200g)", 18.00, f3);
        Produto p18 = new Produto(3004, "Brownie de Chocolate com Nozes (unidade)", 7.00, f3);
        Produto p19 = new Produto(3005, "Cookie Red Velvet com Gotas de Chocolate Branco", 8.50, f3);
        Produto p20 = new Produto(3006, "Pão de Mel Artesanal Recheado (caixa c/ 6)", 24.00, f3);
        Produto p21 = new Produto(3007, "Geleia de Morango Caseira (vidro 300g)", 15.90, f3);
        produtos.add(p15);
        produtos.add(p16);
        produtos.add(p17);
        produtos.add(p18);
        produtos.add(p19);
        produtos.add(p20);
        produtos.add(p21);

        // 4. TRANSPORTADORAS
        Transportadora t1 = new Transportadora(501, "Rapidez Logística Express");
        Transportadora t2 = new Transportadora(502, "Carga Segura Transportes");
        Transportadora t3 = new Transportadora(503, "EcoFrete Soluções Verdes");
        transportadoras.add(t1);
        transportadoras.add(t2);
        transportadoras.add(t3);

        // 5. REMESSAS 
        Remessa r1 = new Remessa(1, t1, cliente);
        Pedido pedido1 = new Pedido();
        pedido1.adicionarItem(p3, 2);   // 2 cestas de morango
        pedido1.adicionarItem(p8, 1);   // 1 fone Bluetooth
        pedido1.adicionarItem(p15, 1);  // 1 bolo de cenoura
        r1.adicionarPedido(pedido1);
        remessas.add(r1);

        Remessa r2 = new Remessa(2, t2, cliente);
        Pedido pedido2 = new Pedido();
        pedido2.adicionarItem(p1, 3);   // 3kg abacate
        pedido2.adicionarItem(p5, 2);   // 2 pacotes cenoura baby
        pedido2.adicionarItem(p11, 1);  // 1 hub USB-C
        pedido2.adicionarItem(p19, 5);  // 5 cookies red velvet
        r2.adicionarPedido(pedido2);
        remessas.add(r2);

        Remessa r3 = new Remessa(3, t3, cliente);
        Pedido pedido3 = new Pedido();
        pedido3.adicionarItem(p4, 4);   // 4 pés de alface
        pedido3.adicionarItem(p7, 6);   // 6 mangas
        pedido3.adicionarItem(p9, 2);   // 2 carregadores solares
        pedido3.adicionarItem(p13, 1);  // 1 teclado mecânico
        pedido3.adicionarItem(p17, 3);  // 3 potes brigadeiro
        r3.adicionarPedido(pedido3);
        remessas.add(r3);

        Remessa r4 = new Remessa(4, t1, cliente);
        Pedido pedido4 = new Pedido();
        pedido4.adicionarItem(p2, 3);   // 3 bandejas tomate cereja
        pedido4.adicionarItem(p6, 2);   // 2 mix de folhas
        pedido4.adicionarItem(p10, 1);  // 1 luminária LED
        pedido4.adicionarItem(p14, 1);  // 1 mouse gamer
        pedido4.adicionarItem(p20, 2);  // 2 caixas pão de mel
        pedido4.adicionarItem(p21, 1);  // 1 geleia
        r4.adicionarPedido(pedido4);
        r4.adicionarPedido(pedido3);
        remessas.add(r4);
    }

}
