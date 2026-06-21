package banco;

import modelo.*;
import modelo.enums.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private final ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void salvar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (usuario_id, status_pedido) VALUES (?, ?)";
        Connection con = null;
        try {
            con = ConexaoBD.getConnection();
            con.setAutoCommit(false);
            try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, pedido.getCliente().getCodigo());
                stmt.setString(2, pedido.getStatus().name());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) pedido.setCodigo(rs.getInt(1));
                }
            }
            if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
                itemPedidoDAO.salvarLista(pedido.getItens(), pedido.getCodigo());
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET usuario_id = ?, status_pedido = ?, " +
                     "data_envio = ?, data_cancelamento = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getCliente().getCodigo());
            stmt.setString(2, pedido.getStatus().name());
            stmt.setTimestamp(3, pedido.getDataEnvio() != null ? Timestamp.valueOf(pedido.getDataEnvio()) : null);
            stmt.setTimestamp(4, pedido.getDataCancelamento() != null ? Timestamp.valueOf(pedido.getDataCancelamento()) : null);
            stmt.setInt(5, pedido.getCodigo());
            stmt.executeUpdate();
        }
    }

    public void excluir(int codigo) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBD.getConnection();
            con.setAutoCommit(false);
            itemPedidoDAO.excluirPorPedido(codigo);
            try (PreparedStatement stmt = con.prepareStatement("DELETE FROM pedido WHERE id = ?")) {
                stmt.setInt(1, codigo);
                stmt.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public Pedido buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                Pedido pedido = mapear(rs);
                pedido.setItens(itemPedidoDAO.listarPorPedido(pedido.getCodigo()));
                return pedido;
            }
        }
    }

    public List<Pedido> listarTodos() throws SQLException {
        String sql = "SELECT * FROM pedido";
        return listarComSql(sql, stmt -> {});
    }

    public List<Pedido> listarPorCliente(int usuarioCodigo) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE usuario_id = ?";
        return listarComSql(sql, stmt -> stmt.setInt(1, usuarioCodigo));
    }

    public List<Pedido> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE data_criacao BETWEEN ? AND ?";
        return listarComSql(sql, stmt -> {
            stmt.setTimestamp(1, Timestamp.valueOf(inicio));
            stmt.setTimestamp(2, Timestamp.valueOf(fim));
        });
    }

    public List<Pedido> listarPorPeriodoCliente(LocalDateTime inicio, LocalDateTime fim, int usuarioCodigo) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE data_criacao BETWEEN ? AND ? AND usuario_id = ?";
        return listarComSql(sql, stmt -> {
            stmt.setTimestamp(1, Timestamp.valueOf(inicio));
            stmt.setTimestamp(2, Timestamp.valueOf(fim));
            stmt.setInt(3, usuarioCodigo);
        });
    }

    public List<Pedido> listarPorStatus(StatusPedido status) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE status_pedido = ?";
        return listarComSql(sql, stmt -> stmt.setString(1, status.name()));
    }

    public boolean existeProdutoEmPedidoPendente(int produtoCodigo) throws SQLException {
        String sql = "SELECT 1 FROM item_pedido ip " +
                     "JOIN pedido p ON p.id = ip.pedido_id " +
                     "WHERE ip.produto_id = ? AND p.status_pedido = 'PENDENTE' LIMIT 1";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, produtoCodigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void atualizarRemessaEFrete(int pedidoCodigo, Integer remessaCodigo, double frete) throws SQLException {
        // OBS: a tabela "pedido" não possui coluna "frete" no schema atual.
        // Apenas remessa_id é persistido.
        String sql = "UPDATE pedido SET remessa_id = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            if (remessaCodigo != null) {
                stmt.setInt(1, remessaCodigo);
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setInt(2, pedidoCodigo);
            stmt.executeUpdate();
        }
    }

    @FunctionalInterface
    private interface ParamBinder {
        void bind(PreparedStatement stmt) throws SQLException;
    }

    private List<Pedido> listarComSql(String sql, ParamBinder binder) throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            binder.bind(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        for (Pedido pedido : lista) {
            pedido.setItens(itemPedidoDAO.listarPorPedido(pedido.getCodigo()));
        }
        return lista;
    }

    /** Monta o Pedido a partir da linha atual; busca o Usuario completo via UsuarioDAO. */
    private Pedido mapear(ResultSet rs) throws SQLException {
        Usuario cliente = usuarioDAO.buscarPorCodigo(rs.getInt("usuario_id"));

        Pedido pedido = new Pedido(cliente);
        pedido.setCodigo(rs.getInt("id"));
        pedido.setStatus(StatusPedido.valueOf(rs.getString("status_pedido")));

        Timestamp dataCriacao = rs.getTimestamp("data_criacao");
        if (dataCriacao != null) pedido.setDataCriado(dataCriacao.toLocalDateTime());

        Timestamp dataEnvio = rs.getTimestamp("data_envio");
        if (dataEnvio != null) pedido.setDataEnvio(dataEnvio.toLocalDateTime());

        Timestamp dataCancelamento = rs.getTimestamp("data_cancelamento");
        if (dataCancelamento != null) pedido.setDataCancelamento(dataCancelamento.toLocalDateTime());

        return pedido;
    }
}