package controle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner; 
import modelo.*;

public class SistemaControle {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Fornecedor> fornecedores = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Remessa> remessas = new ArrayList<>();
    private Usuario usuarioLogado;
    private List<Transportadora> transportadoras  = new ArrayList<>();

    public SistemaControle(Dados d) {
        usuarios = d.usuarios;
        fornecedores = d.fornecedores;
        produtos = d.produtos;
        transportadoras = d.transportadoras;
        remessas = d.remessas;
    }


    public boolean logar(String login, String senha) {
        for(Usuario u : usuarios) {
            if(u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                usuarioLogado = u;
                System.out.println("Logado como: " + u.getNome() + " - ACESSO: " + u.getNivelAcesso());
                return true;
            }
        }
        return false;
    }


    public void listar(String tipo) {
        tipo = tipo.toLowerCase();
        switch (tipo) {
            case "usuarios":
                System.out.println("\n========== USUÁRIOS (" + usuarios.size() + ") ==========");
                if (usuarios.isEmpty()) {
                    System.out.println("(nenhum usuário cadastrado)");
                } else {
                    for (Usuario u : usuarios) {
                        System.out.printf("%s (%s) - %s%n", u.getNome(), u.getLogin(), u.getNivelAcesso());
                    }
                }
                break;

            case "fornecedores":
                System.out.println("\n========== FORNECEDORES (" + fornecedores.size() + ") ==========");
                if (fornecedores.isEmpty()) {
                    System.out.println("  (nenhum fornecedor cadastrado)");
                } else {
                    for (Fornecedor f : fornecedores) {
                        System.out.printf("[%03d] %s - CNPJ: %s%n", f.getCodigo(), f.getNome(), f.getCnpj());
                    }
                }
                break;

    case "produtos":
        System.out.println("\n========== PRODUTOS (" + produtos.size() + ") ==========");
        if (produtos.isEmpty()) {
            System.out.println("(nenhum produto cadastrado)");
        } else {
            // Primeira passada: calcular larguras máximas
            int maxCodigo = "CÓDIGO".length();
            int maxDescricao = "DESCRIÇÃO".length();
            int maxPreco = "PREÇO".length();
            int maxFornecedores = "FORNECEDORES".length();
            
            for (Produto p : produtos) {
                maxCodigo = Math.max(maxCodigo, String.valueOf(p.getCodigo()).length());
                maxDescricao = Math.max(maxDescricao, p.getDescricao().length());
                String precoStr = String.format("R$ %.2f", p.getPreco());
                maxPreco = Math.max(maxPreco, precoStr.length());
                
                if (!p.getFornecedores().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < p.getFornecedores().size(); i++) {
                        sb.append(p.getFornecedores().get(i).getNome());
                        if (i < p.getFornecedores().size() - 1) sb.append(", ");
                    }
                    maxFornecedores = Math.max(maxFornecedores, sb.length());
                }
            }
            
            // Adiciona um pequeno espaçamento
            maxCodigo += 2;
            maxDescricao += 2;
            maxPreco += 2;
            maxFornecedores += 2;
            
            // Cabeçalho
            String formato = "%-" + maxCodigo + "s %-" + maxDescricao + "s %-" + maxPreco + "s %-" + maxFornecedores + "s%n";
            System.out.printf(formato, "CÓDIGO", "DESCRIÇÃO", "PREÇO", "FORNECEDORES");
            
            // Linha separadora
            int totalLargura = maxCodigo + maxDescricao + maxPreco + maxFornecedores + 3;
            System.out.println("-".repeat(totalLargura));
            
            // Impressão dos dados
            for (Produto p : produtos) {
                String precoStr = String.format("R$ %.2f", p.getPreco());
                String fornecedoresStr = "";
                if (!p.getFornecedores().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < p.getFornecedores().size(); i++) {
                        sb.append(p.getFornecedores().get(i).getNome());
                        if (i < p.getFornecedores().size() - 1) sb.append(", ");
                    }
                    fornecedoresStr = sb.toString();
                }
                System.out.printf(formato,String.valueOf(p.getCodigo()),p.getDescricao(),precoStr,fornecedoresStr);
            }
        }
        break;

            case "transportadoras":
                System.out.println("\n========== TRANSPORTADORAS (" + transportadoras.size() + ") ==========");
                if (transportadoras.isEmpty()) {
                    System.out.println("  (nenhuma transportadora cadastrada)");
                } else {
                    for (Transportadora t : transportadoras) {
                        System.out.printf("[%03d] %s%n", t.getCodigo(), t.getNome());
                    }
                }
                break;

    case "remessas":
        System.out.println("\n========== REMESSAS (" + remessas.size() + ") ==========");
        if (remessas.isEmpty()) {
            System.out.println("  (nenhuma remessa cadastrada)");
        } else {
            for (Remessa r : remessas) {
                // Cabeçalho da remessa
                System.out.printf("%n>> REMESSA #%d <<%n", r.getCodigo());
                System.out.printf("   Data: %s | Transportadora: %s%n", 
                                r.getData(), r.getTransportadora().getNome());
                System.out.printf("   Cliente: %s%n", r.getCliente().getNome());
                
                List<Pedido> pedidos = r.getPedidos();
                if (pedidos.isEmpty()) {
                    System.out.println("   (nenhum pedido)");
                    continue;
                }
                
                int numPedido = 1;
                for (Pedido ped : pedidos) {
                    System.out.printf("%n   --- PEDIDO %d ---%n", numPedido++);
                    List<ItemPedido> itens = ped.getItens();
                    if (itens.isEmpty()) {
                        System.out.println("   (sem itens)");
                        continue;
                    }
                    
                    // Calcular larguras máximas para as colunas dos itens
                    int maxDesc = "Descrição".length();
                    int maxQtd = "Qtd".length();
                    int maxPrecoUn = "Preço Un.".length();
                    int maxPrecoTot = "Total".length();
                    
                    for (ItemPedido item : itens) {
                        maxDesc = Math.max(maxDesc, item.getProduto().getDescricao().length());
                        maxQtd = Math.max(maxQtd, String.valueOf(item.getQuantidade()).length());
                        String precoUn = String.format("%.2f", item.getProduto().getPreco());
                        maxPrecoUn = Math.max(maxPrecoUn, precoUn.length());
                        String precoTot = String.format("%.2f", item.getQuantidade() * item.getProduto().getPreco());
                        maxPrecoTot = Math.max(maxPrecoTot, precoTot.length());
                    }
                    
                    // Adiciona espaçamento
                    maxDesc += 2;
                    maxQtd += 2;
                    maxPrecoUn += 2;
                    maxPrecoTot += 2;
                    
                    String formato = "   %-" + maxDesc + "s %" + maxQtd + "s  R$ %" + maxPrecoUn + "s  R$ %" + maxPrecoTot + "s%n";
                    
                    // Cabeçalho da tabela de itens
                    System.out.printf(formato, "Descrição", "Qtd", "Preço Un.", "Total");
                    
                    // Linha separadora
                    int larguraTotal = maxDesc + maxQtd + maxPrecoUn + maxPrecoTot + 12; // ajuste para espaços extras
                    System.out.println("   " + "-".repeat(larguraTotal));
                    
                    // Itens
                    for (ItemPedido item : itens) {
                        String desc = item.getProduto().getDescricao();
                        String qtd = String.valueOf(item.getQuantidade());
                        String precoUn = String.format("%.2f", item.getProduto().getPreco());
                        String precoTot = String.format("%.2f", item.getQuantidade() * item.getProduto().getPreco());
                        System.out.printf(formato, desc, qtd, precoUn, precoTot);
                    }
                    System.out.println("   " + "-".repeat(larguraTotal));

                }
            }
        }
        break;

            case "todos":
                listar("usuarios");
                listar("fornecedores");
                listar("produtos");
                listar("transportadoras");
                listar("remessas");
                break;

            default:
                System.out.println("Opção inválida: '" + tipo + "'. Use: usuarios, fornecedores, produtos, transportadoras, remessas, todos.");
        }
    }

    public void mostrarMenu() {
        int opcao;
        Scanner s = new Scanner(System.in);

        System.out.println("Bem vindo ao sistema de ecoomerce ProtoType");
        System.out.println("Para iniciamos: digite seu login e senha.");

        String username = s.nextLine();
        String senha = s.nextLine();

        if(logar(username, senha)) {
            do {
            if(usuarioLogado.getNivelAcesso().equals("ADMIN")) {
                System.out.println("\n1 - Cadastrar\n2 - Alterar\n3 - Excluir\n4 - Consulta por codigo\n5 - Consulta por texto\n6 - Consultar tudo");
                opcao = s.nextInt();
                switch(opcao) {
                    case 1:
                        System.out.println("Cadastrar");
                        break;
                    case 2:
                        System.out.println("Alterar");
                        break;
                    case 3:
                        System.out.println("Excluir");
                        break;
                    case 4: 
                        System.out.println("Consulta por codigo");
                        break;
                    case 5: 
                        System.out.println("Consulta por texto");
                        break;
                    case 6:
                        System.out.println("Mostrando todos os dados: ");
                        listar("todos");
                        break;
                    default:
                        System.out.println("Nao foi identificado");
                } 
                } 
                else {
                    System.out.println("4 - Consulta por codigo\n5 - Consulta por texto\n6 - Consultar tudo");
                    opcao = s.nextInt();

                    switch(opcao) {
                        case 4: 
                            System.out.println("Consulta por codigo");
                            break;
                        case 5: 
                            System.out.println("Consulta por texto");
                            break;
                        case 6:
                            System.out.println("Mostrando todos os dados: ");


                        default:
                            System.out.println("Nao foi identificado essa opcao");
                    } 
                } 
            }while(opcao != 0);
        } 
        
        
    }



}
