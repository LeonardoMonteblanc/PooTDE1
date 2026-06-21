package banco;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportadoraDAO {

    
    public void salvar(Transportadora t) throws SQLException {
        String sql = "INSERT INTO transportadora (nome, taxa_fixa) VALUES (?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, t.getNome());
            query.setDouble(2, t.getTaxaFrete());
            query.executeUpdate();
            try (ResultSet rs = query.getGeneratedKeys()) {
                if (rs.next()) t.setCodigo(rs.getInt(1));
            }
        }
    }

    
    public void atualizar(Transportadora t) throws SQLException {
        String sql = "UPDATE transportadora SET nome = ?, taxa_fixa = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, t.getNome());
            query.setDouble(2, t.getTaxaFrete());
            query.setInt(3, t.getCodigo());
            query.executeUpdate();
        }
    }

    
    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM transportadora WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            query.executeUpdate();
        }
    }

    
    public Transportadora buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM transportadora WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    
    public List<Transportadora> listarTodos() throws SQLException {
        String sql = "SELECT * FROM transportadora";
        List<Transportadora> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql);
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    
    public List<Transportadora> buscarPorNome(String trecho) throws SQLException {
        String sql = "SELECT * FROM transportadora WHERE nome LIKE ?";
        List<Transportadora> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, "%" + trecho + "%");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Transportadora mapear(ResultSet rs) throws SQLException {
        return new Transportadora(rs.getInt("id"), rs.getString("nome"), rs.getString("cnpj"), rs.getDouble("taxa_fixa"));
    }
}