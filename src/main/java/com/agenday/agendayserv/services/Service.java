package com.agenday.agendayserv.services;

public interface Service<T, ID> {
    Iterable<T> obterTodos();
    T obterPorId(ID id);
    T adicionar(T entity) throws Exception;
    T atualizar(ID id, T entity);
    void deletar(ID id);
}
