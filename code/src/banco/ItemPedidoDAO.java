package banco;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    public void salvar(ItemPedido item, int pedidoCodigo) throws SQLException {
        String sql = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            query.setInt(1, pedidoCodigo);
            query.setInt(2, item.getProduto().getCodigo());
            query.setInt(3, item.getQuantidade());
            query.executeUpdate();
            try (ResultSet rs = query.getGeneratedKeys()) {
                if (rs.next()) item.setCodigo(rs.getInt(1));
            }
        }
    }

    public void salvarLista(List<ItemPedido> itens, int pedidoCodigo) throws SQLException {
        String sql = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (ItemPedido item : itens) {
                query.setInt(1, pedidoCodigo);
                query.setInt(2, item.getProduto().getCodigo());
                query.setInt(3, item.getQuantidade());
                query.addBatch();
            }
            query.executeBatch();
            try (ResultSet rs = query.getGeneratedKeys()) {
                int i = 0;
                while (rs.next() && i < itens.size()) {
                    itens.get(i).setCodigo(rs.getInt(1));
                    i++;
                }
            }
        }
    }

    public void excluirPorPedido(int pedidoCodigo) throws SQLException {
        String sql = "DELETE FROM item_pedido WHERE pedido_id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, pedidoCodigo);
            query.executeUpdate();
        }
    }

    public List<ItemPedido> listarPorPedido(int pedidoCodigo) throws SQLException {
        String sql = "SELECT ip.produto_id, ip.quantidade, " +
                     "p.descricao AS p_descricao, p.preco AS p_preco, p.estoque AS p_estoque " +
                     "FROM item_pedido ip JOIN produto p ON p.id = ip.produto_id " +
                     "WHERE ip.pedido_id = ?";
        List<ItemPedido> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, pedidoCodigo);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private ItemPedido mapear(ResultSet rs) throws SQLException {
        Produto produto = new Produto(rs.getInt("produto_id"), rs.getString("p_descricao"),
                rs.getDouble("p_preco"), rs.getInt("p_estoque"));
        return new ItemPedido(produto, rs.getInt("quantidade"));
    }
}