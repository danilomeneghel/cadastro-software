package bean;

import alertas.ErroSistema;
import dao.UsuarioDAO;
import entidade.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean extends CrudBean<Usuario, UsuarioDAO> implements Serializable {

    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    public UsuarioBean() {
        usuario = new Usuario();
    }

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

    public String doLogin() throws ErroSistema {
        if (usuario.getEmail() != null) {
            Integer valida = getDao().login(usuario.getEmail(), usuario.getSenha());
            switch (valida) {
                case 1:
                    session.setAttribute("email", (String) usuario.getEmail());
                    session.setAttribute("nome", (String) getDao().getNomeUsuario(usuario.getEmail()));
                    return "/app/index?faces-redirect=true";
                case 0:
                    adicionarMensagem("Usuário inativo!", FacesMessage.SEVERITY_INFO);
                    return null;
                default:
                    adicionarMensagem("Usuário ou senha inválido!", FacesMessage.SEVERITY_INFO);
                    return null;
            }
        }
        return null;
    }

    public String getUsuarioLogado() {
        return (String) session.getAttribute("nome");
    }

    public String doLogout() {
        session.invalidate();
        return "/login/index?faces-redirect=true";
    }
}
