package bean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getEmail() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("email").toString();
    }
    
    public static String getNome() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("nome").toString();
    }

    public static String getId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("id");
        } else {
            return null;
        }
    }
}
