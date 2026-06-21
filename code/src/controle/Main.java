package controle;

import banco.*;
import controle.io.ConsoleOutput;
import controle.io.Leitor;
import exceptions.SistemaException;
import modelo.Usuario;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        Leitor leitor = new Leitor(scan);
        ConsoleOutput out = new ConsoleOutput();

        ProdutoDAO produtoDAO = new ProdutoDAO();
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        TransportadoraDAO transportadoraDAO = new TransportadoraDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        RemessaDAO remessaDAO = new RemessaDAO();
        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();

        ControleListagem listagem = new ControleListagem(produtoDAO, fornecedorDAO, transportadoraDAO, usuarioDAO, pedidoDAO, remessaDAO, itemPedidoDAO, out);

        ControleCadastro cadastro = new ControleCadastro(produtoDAO, fornecedorDAO, transportadoraDAO, usuarioDAO, pedidoDAO, remessaDAO, itemPedidoDAO, out, leitor);

        ControleExclusao exclusao = new ControleExclusao(produtoDAO, fornecedorDAO, transportadoraDAO, usuarioDAO, pedidoDAO, remessaDAO, itemPedidoDAO, out, leitor);

        MenuPrincipal menu = new MenuPrincipal(listagem, cadastro, exclusao, leitor);

        System.out.println("========== LOGIN ==========");
        Usuario usuarioLogado = null;

        while (usuarioLogado == null) {
            String login = leitor.linha("Login: ");
            String senha = leitor.linha("Senha: ");

            try {
                Usuario usuario = usuarioDAO.buscarPorLogin(login);
                if (usuario == null) {
                    System.out.println("Usuário não encontrado.");
                    continue;
                }
                usuario.fazerLogin(senha, login);
                usuarioLogado = usuario;
                System.out.println("Login realizado com sucesso. Bem-vindo, " + usuario.getNome() + "!");
            } catch (SistemaException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
                return;
            }
        }

        menu.setUsuarioLogado(usuarioLogado);
        menu.executar();
    }
}