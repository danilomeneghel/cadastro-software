package bean;

import dao.CrudDAO;
import alertas.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class CrudBean<E, D extends CrudDAO> {

    private String estadoTela = "listar";

    public E entidade;
    public List<E> entidades;

    public void novo() {
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }

    public void salvar() {
        try {
            getDao().salvar(entidade);
            entidade = criarNovaEntidade();
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaLista();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(E entidade) {
        this.entidade = entidade;
        mudarParaEdita();
    }

    public void excluir(E entidade) {
        try {
            getDao().excluir(entidade);
            entidades.remove(entidade);
            adicionarMensagem("Excluído com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<E> listar() {
        if (isLista() == false) {
            mudarParaLista();
        } else {
            try {
                entidades = getDao().listar();
                if (entidades == null || entidades.size() < 1) {
                    adicionarMensagem("Não temos nada cadastrado!", FacesMessage.SEVERITY_WARN);
                }
                return entidades;
            } catch (ErroSistema ex) {
                Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
                adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
        return null;
    }

    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    //getters e setters
    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }

    //Responsvel por criar os métodos nas classes bean
    public abstract D getDao();

    public abstract E criarNovaEntidade();

    //Metodos para controle da tela
    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isLista() {
        return "listar".equals(estadoTela);
    }

    public void mudarParaInseri() {
        estadoTela = "inserir";
    }

    public void mudarParaEdita() {
        estadoTela = "editar";
    }

    public void mudarParaLista() {
        estadoTela = "listar";
    }

}
