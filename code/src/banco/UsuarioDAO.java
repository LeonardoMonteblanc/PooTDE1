package banco;

import modelo.*;
import modelo.enums.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (login, senha, nivel_acesso, nome) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, usuario.getLogin());
            query.setString(2, usuario.getSenha());
            query.setString(3, usuario.getNivelAcesso().name());
            query.setString(4, usuario.getNome());
            query.executeUpdate();
            try (ResultSet rs = query.getGeneratedKeys()) {
                if (rs.next()) usuario.setCodigo(rs.getInt(1));
            }
        }
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET login = ?, senha = ?, nivel_acesso = ?, nome = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, usuario.getLogin());
            query.setString(2, usuario.getSenha());
            query.setString(3, usuario.getNivelAcesso().name());
            query.setString(4, usuario.getNome());
            query.setInt(5, usuario.getCodigo());
            query.executeUpdate();
        }
    }

    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            query.executeUpdate();
        }
    }

    public Usuario buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, codigo);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public Usuario buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE login = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, login);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuario";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql);
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Usuario> buscarPorNome(String trecho) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nome LIKE ?";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, "%" + trecho + "%");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(rs.getInt("id"), rs.getString("nome"), "00000000000", rs.getString("login"), rs.getString("senha"),NivelAcesso.valueOf(rs.getString("nivel_acesso")));
    }
}