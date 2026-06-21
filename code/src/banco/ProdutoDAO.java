package banco;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    
    public void salvar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (descricao, preco, estoque) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, produto.getDescricao());
            query.setDouble(2, produto.getPreco());
            query.setInt(3, produto.getEstoque());
            query.executeUpdate();
            try (ResultSet rs = query.getGeneratedKeys()) {
                if (rs.next()) produto.setCodigo(rs.getInt(1));
            }
        }
    }
    
    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET descricao = ?, preco = ?, estoque = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, produto.getDescricao());
            query.setDouble(2, produto.getPreco());
            query.setInt(3, produto.getEstoque());
            query.setInt(4, produto.getCodigo());
            query.executeUpdate();
        }
    }
    
    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            query.executeUpdate();
        }
    }
    
    public Produto buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }
    
    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM produto";
        List<Produto> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql);
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }


    public List<Produto> buscarPorDescricao(String trecho) throws SQLException {
        String sql = "SELECT * FROM produto WHERE descricao LIKE ?";
        List<Produto> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, "%" + trecho + "%");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
    
    public void adicionarFornecedor(int produtoCodigo, int fornecedorCodigo) throws SQLException {
        String sql = "INSERT INTO produto_fornecedor (produto_id, fornecedor_id) VALUES (?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, produtoCodigo);
            query.setInt(2, fornecedorCodigo);
            query.executeUpdate();
        }
    }
    
    public void removerFornecedor(int produtoCodigo, int fornecedorCodigo) throws SQLException {
        String sql = "DELETE FROM produto_fornecedor WHERE produto_id = ? AND fornecedor_id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, produtoCodigo);
            query.setInt(2, fornecedorCodigo);
            query.executeUpdate();
        }
    }
    
    public List<Fornecedor> listarFornecedoresDoProduto(int produtoCodigo) throws SQLException {
        String sql = "SELECT f.* FROM fornecedor f " +
                     "JOIN produto_fornecedor pf ON pf.fornecedor_id = f.id " +
                     "WHERE pf.produto_id = ?";
        List<Fornecedor> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, produtoCodigo);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    Fornecedor f = new Fornecedor(rs.getInt("id"), rs.getString("nome"), rs.getString("cnpj"));
                    lista.add(f);
                }
            }
        }
        return lista;
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        return new Produto(rs.getInt("id"), rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque"));
    }
}