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
        Integer valida = getDao().login(usuario.getEmail(), usuario.getSenha());
        switch (valida) {
            case 1:
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("usuario", usuario);

                System.out.println("Usuario " + session.getAttribute("usuario"));

                return "/app/index?faces-redirect=true";
            case 0:
                adicionarMensagem("Usuário inativo!", FacesMessage.SEVERITY_INFO);
                return null;
            default:
                adicionarMensagem("Usuário ou senha inválido!", FacesMessage.SEVERITY_INFO);
                return null;
        }
    }

    public Usuario getUsuarioLogado() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        System.out.println("Return usuario Logado " + session.getAttribute("usuario"));
        
        return (Usuario) session.getAttribute("usuario");        
    }

    public String doLogout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "/login/login.jsf";
    }
}
