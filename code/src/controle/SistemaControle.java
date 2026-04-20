package controle;

import java.util.ArrayList;
import java.util.List;
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
                System.out.println("Logado como: " + u.getNome() + " - ACESSO: " + u.getNivelAcesso().name());
                return true;
            }
        }
        return false;
    }


    public void listar(String tipo) {
        tipo = tipo.toLowerCase();
        switch (tipo) {
            case "usuarios":
                System.out.println("\n========== USUARIOS (" + usuarios.size() + ") ==========");
                if (usuarios.isEmpty()) {
                    System.out.println("(nenhum usuário cadastrado)");
                } else {
                    for (Usuario u : usuarios) {
                        System.out.printf("%s (%s) - %s%n", u.getNome(), u.getLogin(), u.getNivelAcesso().name());
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
        int opcao = -1;
        Scanner s = new Scanner(System.in);

        System.out.println("Bem vindo ao sistema de ecommerce ProtoType");
        System.out.println("Para iniciarmos: digite seu login e senha.");

        String username = s.nextLine();
        String senha = s.nextLine();

        if (logar(username, senha)) {
            do {
                if (usuarioLogado.temPermissao(Permissao.CADASTRAR)) {
                    System.out.println("\n1 - Cadastrar\n2 - Alterar\n3 - Excluir\n4 - Consulta por codigo\n5 - Consulta por texto\n6 - Consultar tudo\n0 - Sair");
                } else {
                    System.out.println("\n4 - Consulta por codigo\n5 - Consulta por texto\n6 - Consultar tudo\n0 - Sair");
                }

                try {
                    opcao = Integer.parseInt(s.nextLine().trim());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        if (usuarioLogado.temPermissao(Permissao.CADASTRAR))
                            System.out.println("Cadastrar (a implementar)");
                        else
                            System.out.println("Acesso negado.");
                        break;
                    case 2:
                        if (usuarioLogado.temPermissao(Permissao.CADASTRAR))
                            System.out.println("Alterar (a implementar)");
                        else
                            System.out.println("Acesso negado.");
                        break;
                    case 3:
                        if (usuarioLogado.temPermissao(Permissao.EXCLUIR))
                            System.out.println("Excluir (a implementar)");
                        else
                            System.out.println("Acesso negado.");
                        break;
                    case 4:
                        consultarPorCodigo(s);
                        break;
                    case 5:
                        consultarPorTexto(s);
                        break;
                    case 6:
                        listar("todos");
                        break;
                    case 0:
                        System.out.println("Encerrando sessao. Ate logo!");
                        break;
                    default:
                        System.out.println("Opcao nao identificada.");
                }
            } while (opcao != 0);
        } else {
            System.out.println("Login ou senha incorretos.");
        }
    }

    private void consultarPorCodigo(Scanner s) {
        System.out.println("\nBuscar por codigo em:");
        System.out.println("  1 - Usuarios");
        System.out.println("  2 - Fornecedores");
        System.out.println("  3 - Produtos");
        System.out.println("  4 - Transportadoras");
        System.out.println("  5 - Remessas");
        System.out.print("Tipo: ");

        int tipo;
        try {
            tipo = Integer.parseInt(s.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Tipo invalido.");
            return;
        }

        System.out.print("Codigo: ");
        int codigo;
        try {
            codigo = Integer.parseInt(s.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Codigo invalido.");
            return;
        }

        boolean encontrado = false;
        switch (tipo) {
            case 1:
                for (Usuario u : usuarios) {
                    if (u.getCodigo() == codigo) {
                        System.out.printf("%n[Usuario] %s (login: %s) - %s%n",
                                u.getNome(), u.getLogin(), u.getNivelAcesso());
                        encontrado = true;
                        break;
                    }
                }
                break;
            case 2:
                for (Fornecedor f : fornecedores) {
                    if (f.getCodigo() == codigo) {
                        System.out.printf("%n[Fornecedor] [%d] %s - CNPJ: %s%n",
                                f.getCodigo(), f.getNome(), f.getCnpj());
                        encontrado = true;
                        break;
                    }
                }
                break;
            case 3:
                for (Produto p : produtos) {
                    if (p.getCodigo() == codigo) {
                        System.out.printf("%n[Produto] [%d] %s - R$ %.2f%n",
                                p.getCodigo(), p.getDescricao(), p.getPreco());
                        if (!p.getFornecedores().isEmpty()) {
                            System.out.print("  Fornecedor(es): ");
                            for (int i = 0; i < p.getFornecedores().size(); i++) {
                                System.out.print(p.getFornecedores().get(i).getNome());
                                if (i < p.getFornecedores().size() - 1) System.out.print(", ");
                            }
                            System.out.println();
                        }
                        encontrado = true;
                        break;
                    }
                }
                break;
            case 4:
                for (Transportadora t : transportadoras) {
                    if (t.getCodigo() == codigo) {
                        System.out.printf("%n[Transportadora] [%d] %s%n",
                                t.getCodigo(), t.getNome());
                        encontrado = true;
                        break;
                    }
                }
                break;
            case 5:
                for (Remessa r : remessas) {
                    if (r.getCodigo() == codigo) {
                        System.out.printf("%n[Remessa] #%d | Data: %s | Transportadora: %s | Cliente: %s | Pedidos: %d%n",
                                r.getCodigo(), r.getData(),
                                r.getTransportadora().getNome(),
                                r.getCliente().getNome(),
                                r.getPedidos().size());
                        encontrado = true;
                        break;
                    }
                }
                break;
            default:
                System.out.println("Tipo invalido.");
                return;
        }

        if (!encontrado) {
            System.out.println("Nenhum registro encontrado com codigo " + codigo + ".");
        }
    }

    private void consultarPorTexto(Scanner s) {
        System.out.print("\nDigite o texto a buscar: ");
        String texto = s.nextLine().trim().toLowerCase();

        if (texto.isEmpty()) {
            System.out.println("Texto nao pode ser vazio.");
            return;
        }

        boolean algumResultado = false;

        // Usuarios
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
                        u.getCodigo(), u.getNome(), u.getLogin(), u.getNivelAcesso());
            }
            algumResultado = true;
        }

        // Fornecedores
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
            algumResultado = true;
        }

        // Produtos
        List<Produto> produtosEncontrados = new ArrayList<>();
        for (Produto p : produtos) {
            boolean matchDesc = p.getDescricao().toLowerCase().contains(texto);
            boolean matchForn = p.getFornecedores().stream()
                    .anyMatch(f -> f.getNome().toLowerCase().contains(texto));
            if (matchDesc || matchForn) {
                produtosEncontrados.add(p);
            }
        }
        if (!produtosEncontrados.isEmpty()) {
            System.out.println("\n--- Produtos ---");
            for (Produto p : produtosEncontrados) {
                System.out.printf("  [%d] %s - R$ %.2f%n", p.getCodigo(), p.getDescricao(), p.getPreco());
            }
            algumResultado = true;
        }

        // Transportadoras
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
            algumResultado = true;
        }

        // Remessas (busca por nome do cliente ou da transportadora)
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
            algumResultado = true;
        }

        if (!algumResultado) {
            System.out.println("Nenhum resultado encontrado para \"" + texto + "\".");
        }
    }



}
