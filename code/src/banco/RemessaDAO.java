package banco;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemessaDAO {

    public void salvar(Remessa remessa) throws SQLException {
        String sql = "INSERT INTO remessa (transportadora_id) VALUES (?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            query.setInt(1, remessa.getTransportadora().getCodigo());
            query.executeUpdate();
            try (ResultSet rs = query.getGeneratedKeys()) {
                if (rs.next()) remessa.setCodigo(rs.getInt(1));
            }
        }
    }

    public void atualizar(Remessa remessa) throws SQLException {
        String sql = "UPDATE remessa SET transportadora_id = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, remessa.getTransportadora().getCodigo());
            query.setInt(2, remessa.getCodigo());
            query.executeUpdate();
        }
    }

    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM remessa WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            query.executeUpdate();
        }
    }

    public Remessa buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT r.id AS r_id, r.transportadora_id, r.data_criacao, " +
                     "t.nome AS t_nome, t.cnpj AS t_cnpj, t.taxa_fixa AS t_taxa_fixa " +
                     "FROM remessa r JOIN transportadora t ON t.id = r.transportadora_id " +
                     "WHERE r.id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Remessa> listarTodos() throws SQLException {
        String sql = "SELECT r.id AS r_id, r.transportadora_id, r.data_criacao, " +
                     "t.nome AS t_nome, t.cnpj AS t_cnpj, t.taxa_fixa AS t_taxa_fixa " +
                     "FROM remessa r JOIN transportadora t ON t.id = r.transportadora_id";
        List<Remessa> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql);
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Remessa> listarPorCliente(int usuarioCodigo) throws SQLException {
        String sql = "SELECT DISTINCT r.id AS r_id, r.transportadora_id, r.data_criacao, " +
                     "t.nome AS t_nome, t.cnpj AS t_cnpj, t.taxa_fixa AS t_taxa_fixa " +
                     "FROM remessa r " +
                     "JOIN transportadora t ON t.id = r.transportadora_id " +
                     "JOIN pedido p ON p.remessa_id = r.id " +
                     "WHERE p.usuario_id = ?";
        List<Remessa> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, usuarioCodigo);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Remessa mapear(ResultSet rs) throws SQLException {
        Transportadora t = new Transportadora(rs.getInt("transportadora_id"), rs.getString("t_nome"),rs.getString("t_cnpj"), rs.getDouble("t_taxa_fixa"));

        Remessa remessa = new Remessa(t);
        remessa.setCodigo(rs.getInt("r_id"));
        Timestamp dataCriacao = rs.getTimestamp("data_criacao");
        if (dataCriacao != null) remessa.setData(dataCriacao.toLocalDateTime());
        return remessa;
    }
}