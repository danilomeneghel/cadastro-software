package dao;

import entidade.Software;
import util.ConexaoDB;
import alertas.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class SoftwareDAO implements CrudDAO<Software> {

    @Override
    public void salvar(Software software) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps;
            if (software.getId() == null) {
                ps = conexao.prepareStatement("INSERT INTO software (nome, desenvolvedor, tipo, data_cadastro) VALUES (?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("UPDATE software SET nome=?, desenvolvedor=?, tipo=?, data_cadastro=? WHERE id=?");
                ps.setInt(5, software.getId());
            }
            ps.setString(1, software.getNome());
            ps.setString(2, software.getDesenvolvedor());
            ps.setString(3, software.getTipo());
            ps.setDate(4, new Date(software.getData_cadastro().getTime()));
            ps.execute();
            ConexaoDB.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }

    @Override
    public void excluir(Software software) throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM software WHERE id = ?");
            ps.setInt(1, software.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao excluir!", ex);
        }
    }

    @Override
    public List<Software> listar() throws ErroSistema {
        try {
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM software ORDER BY nome");
            ResultSet resultSet = ps.executeQuery();
            List<Software> softwares = new ArrayList<>();
            while (resultSet.next()) {
                Software software = new Software();
                software.setId(resultSet.getInt("id"));
                software.setNome(resultSet.getString("nome"));
                software.setDesenvolvedor(resultSet.getString("desenvolvedor"));
                software.setTipo(resultSet.getString("tipo"));
                software.setData_cadastro(resultSet.getDate("data_cadastro"));
                softwares.add(software);
            }
            ConexaoDB.fecharConexao();
            return softwares;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar!", ex);
        }
    }
}
