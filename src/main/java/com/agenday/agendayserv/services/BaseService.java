package com.agenday.agendayserv.services;

import com.agenday.agendayserv.exceptions.NotFoundException;
import com.agenday.agendayserv.models.BaseEntity;
import com.agenday.agendayserv.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<T extends BaseEntity, ID> {
    @Autowired
    private ModelMapper autoMapper;

    protected abstract JpaRepository<T, ID> getRepository();

    public List<T> getAll() {
        return getRepository().findAll();
    }

    public T getById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    public T add(T entity) {
        return getRepository().save(entity);
    }

    public T update(ID id, T entity) {
        var exists = getRepository().findById(id);

        if (exists.isEmpty()) {
            var object = getRepository().getClass().getName();

            throw new NotFoundException(StringUtils.remove(object, "Repository"));
        }

        var dbEntity = exists.get();

        autoMapper.map(entity, dbEntity);

        return getRepository().save(dbEntity);
    }

    public void delete(ID id) {
        getRepository().deleteById(id);
    }
}
