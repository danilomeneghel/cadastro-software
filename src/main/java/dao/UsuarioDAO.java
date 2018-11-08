package dao;

import entidade.Usuario;
import util.ConexaoDB;
import alertas.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CrudDAO<Usuario> {

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps;
            if (entidade.getId() == null) {
                ps = conexao.prepareStatement("INSERT INTO usuario (nome, email, senha, ativo) VALUES (?, ?, ?, ?)");
            } else {
                ps = conexao.prepareStatement("UPDATE usuario SET nome=?, email=?, senha=?, ativo=? WHERE id=?");
                ps.setInt(5, entidade.getId());
            }
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getEmail());
            ps.setString(3, entidade.getSenha());
            ps.setInt(4, entidade.getAtivo());
            ps.execute();
            ConexaoDB.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }

    @Override
    public void excluir(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM usuario WHERE id = ?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao excluir!", ex);
        }
    }

    @Override
    public List<Usuario> listar() throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM usuario ORDER BY nome");
            ResultSet resultSet = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setAtivo(resultSet.getInt("ativo"));
                usuarios.add(usuario);
            }
            ConexaoDB.fecharConexao();
            return usuarios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar!", ex);
        }
    }

    public Integer login(String email, String senha) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT email, senha, ativo FROM usuario WHERE email = ? AND senha = ?");
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ativo");
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao localizar usuário!", ex);
        }
        return -1;
    }

    public String getNomeUsuario(String email) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT nome FROM usuario WHERE email = ?");
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("nome");
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao localizar usuário!", ex);
        }
        return null;
    }

}
