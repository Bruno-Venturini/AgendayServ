package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<T extends BaseEntity, ID> implements Service<T, ID> {
    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    public List<T> obterTodos() {
        return getRepository().findAll();
    }

    @Override
    public T obterPorId(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public T adicionar(T entity) throws Exception {
        return getRepository().save(entity);
    }

    @Override
    public T atualizar(ID id, T entity) {
        return getRepository().findById(id)
            .map(e -> saveAndReturnSavedEntity(entity, e))
            .orElse(null);
    }

    @Override
    public void deletar(ID id) {
        getRepository().deleteById(id);
    }

    private T saveAndReturnSavedEntity(T entity, T entityFromDB) {
        entity.setId(entityFromDB.getId());
        return getRepository().save(entity);
    }
}
