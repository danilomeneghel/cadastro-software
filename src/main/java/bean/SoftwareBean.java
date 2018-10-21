package bean;

import dao.SoftwareDAO;
import entidade.Software;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "softwareBean")
@SessionScoped
public class SoftwareBean extends CrudBean<Software, SoftwareDAO> {

    private SoftwareDAO softwareDAO;

    public SoftwareDAO getDao() {
        if (softwareDAO == null) {
            softwareDAO = new SoftwareDAO();
        }
        return softwareDAO;
    }

    public Software criarNovaEntidade() {
        return new Software();
    }

}
