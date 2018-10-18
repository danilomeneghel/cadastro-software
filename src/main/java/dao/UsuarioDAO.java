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

public class UsuarioDAO implements CrudDAO<Usuario>{

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps;
            if(entidade.getId() == null){
                ps = conexao.prepareStatement("INSERT INTO usuario (nome, usuario, senha) VALUES (?, ?, ?)");
            } else {
                ps = conexao.prepareStatement("UPDATE usuario SET nome=?, usuario=?, senha=? WHERE id=?");
                ps.setInt(4, entidade.getId());
            }
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getUsuario());
            ps.setString(3, entidade.getSenha());
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
            PreparedStatement ps  = conexao.prepareStatement("delete from usuario where id = ?");
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
            PreparedStatement ps = conexao.prepareStatement("select * from usuario");
            ResultSet resultSet = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while(resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setUsuario(resultSet.getString("usuario"));
                usuario.setSenha(resultSet.getString("senha"));
                usuarios.add(usuario);
            }
            ConexaoDB.fecharConexao();
            return usuarios;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar!",ex);
        }
    }
    
}
