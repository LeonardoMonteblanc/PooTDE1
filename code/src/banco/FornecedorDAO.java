package banco;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {


    public void salvar(Fornecedor fornecedor) throws SQLException {
        String sql = "INSERT INTO fornecedor (nome, cnpj) VALUES (?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, fornecedor.getNome());
            query.setString(2, fornecedor.getDocumento());
            query.executeUpdate();
            try (ResultSet rs = query.getGeneratedKeys()) {
                if (rs.next()) fornecedor.setCodigo(rs.getInt(1));
            }
        }
    }

    public void atualizar(Fornecedor fornecedor) throws SQLException {
        String sql = "UPDATE fornecedor SET nome = ?, cnpj = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, fornecedor.getNome());
            query.setString(2, fornecedor.getDocumento());
            query.setInt(3, fornecedor.getCodigo());
            query.executeUpdate();
        }
    }

    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            query.executeUpdate();
        }
    }

    public Fornecedor buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM fornecedor WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Fornecedor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM fornecedor";
        List<Fornecedor> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql);
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }


    public List<Fornecedor> buscarPorNome(String trecho) throws SQLException {
        String sql = "SELECT * FROM fornecedor WHERE nome LIKE ?";
        List<Fornecedor> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, "%" + trecho + "%");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Fornecedor mapear(ResultSet rs) throws SQLException {
        return new Fornecedor(rs.getInt("id"), rs.getString("nome"), rs.getString("cnpj"));
    }
}