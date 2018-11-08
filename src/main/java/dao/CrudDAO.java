package dao;

import alertas.ErroSistema;
import java.util.List;

public interface CrudDAO<E> {

    public void salvar(E entidade) throws ErroSistema;

    public void excluir(E entidade) throws ErroSistema;

    public List<E> listar() throws ErroSistema;

}
