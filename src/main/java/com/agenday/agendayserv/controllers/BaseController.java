package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.BaseEntity;
import com.agenday.agendayserv.services.BaseService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

public abstract class BaseController<T extends BaseEntity, ID> {
    protected abstract BaseService<T, ID> getService();

    @GetMapping
    public List<T> getAll() {
        return getService().getAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable ID id) {
        return getService().getById(id);
    }

    @PostMapping
    public T add(@RequestBody T entity) {
        return getService().add(entity);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T entity) {
        return getService().update(id, entity);
    }

    @DeleteMapping
    public void delete(@PathParam("id") ID id) {
        getService().delete(id);
    }
}
