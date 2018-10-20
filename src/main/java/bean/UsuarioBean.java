package bean;

import alertas.ErroSistema;
import dao.UsuarioDAO;
import entidade.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
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
        Integer valida = getDao().login(usuario.getEmail(), usuario.getSenha());
        switch (valida) {
            case 1:
                HttpSession session = SessionBean.getSession();
                session.setAttribute("email", usuario.getEmail());
                session.setAttribute("nome", usuario.getNome());
                return "index";
            case 0:
                adicionarMensagem("Usuário inativo!", FacesMessage.SEVERITY_INFO);
                return null;
            default:
                adicionarMensagem("Usuário ou senha inválido!", FacesMessage.SEVERITY_INFO);
                return null;
        }
    }

    public String getNomeUsuario() {
        HttpSession session = SessionBean.getSession();
        String nome = (String) session.getAttribute("nome");
        return nome;
    }

    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        return "/";
    }
}
