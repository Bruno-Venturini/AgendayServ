package com.agenday.agendayserv.services;

import java.util.List;

public interface CrudService<T, ID> {
    public List<T> obterTodos();
    public T adicionar(T entidade) throws Exception;
    public T atualizar(T entidade);
    public void deletar(ID id);
}
