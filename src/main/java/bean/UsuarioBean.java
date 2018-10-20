package bean;

import alertas.ErroSistema;
import dao.UsuarioDAO;
import entidade.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UsuarioBean extends CrudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;
    private Usuario usuario = new Usuario();

    @Override
    public UsuarioDAO getDao() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario criarNovaEntidade() {
        return new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String login() throws ErroSistema {
        boolean valida = getDao().login(usuario.getEmail(), usuario.getSenha());
        if (valida) {
            return "index";
        } else {
            adicionarMensagem("Usuário ou senha inválido!", FacesMessage.SEVERITY_INFO);
            return null;
        }
    }

}
